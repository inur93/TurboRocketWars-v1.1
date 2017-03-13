package com.vormadal.turborocket.screens;

import static com.vormadal.turborocket.utils.PropKeys.getMapConfig;
import static com.vormadal.turborocket.utils.PropKeys.getMapKeys;
import static com.vormadal.turborocket.utils.PropKeys.getStatsBarHeight;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.vormadal.turborocket.CollisionController;
import com.vormadal.turborocket.InputManager;
import com.vormadal.turborocket.InputManager.INPUT_MODE;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.Map;
import com.vormadal.turborocket.models.Player;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.actors.ActorPlayer;
import com.vormadal.turborocket.models.ammo.Bullet;
import com.vormadal.turborocket.models.ammo.Cannon;
import com.vormadal.turborocket.models.ammo.Fragment;
import com.vormadal.turborocket.models.ammo.SeekerMissile;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.PropKeys;
import com.vormadal.turborocket.utils.InputConfiguration.InputType;
import com.vormadal.turborocket.utils.InputListener;

public class GameScreen implements Screen {
	//configs
		public int statsBarHeight = getStatsBarHeight();
		
		
		
		private WorldEntitiesController entitiesController;
		private CollisionController collisionController;
		private Stage stage;
		private Stage statsStage;
//		private SpriteBatch batch;
		private World world;
		Fragment frag;
		
		final float PIXELS_TO_METERS = 100f;
//		private Timer timer;
		private InputManager inputManager;
		
		private ArrayList<Player> players;
		
		//debug
		Body bodyEdgeScreen;
	    Box2DDebugRenderer debugRenderer;
	    Matrix4 debugMatrix;
	    OrthographicCamera camera;
	    BitmapFont font;
	    BitmapFont fontTest;
	    
	    public GameScreen(Game game, MapConfig map, int numPlayers){
	    	create();
	    }
	    
		public void create() {
			
			PropKeys.setDefault();
			//viewport width should be determined by number of players playing on same screen.
			//the width is the size of the world that should be visible, thus the viewport is smaller on splitscreen.
			stage = new Stage(new FitViewport(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-statsBarHeight));//new ExtendViewport(640, 480));//new ScreenViewport());
			statsStage = new Stage(new FillViewport(Gdx.graphics.getWidth(), statsBarHeight));
			world = new World(new Vector2(0f, -1f), true);
			entitiesController = new WorldEntitiesController(world, stage);
			collisionController = new CollisionController();
			world.setContactListener(collisionController);
//			stage.addActor(new ActorFragment(frag));
			
			String[] mapPaths = getMapKeys();
			MapConfig mapConfig = getMapConfig(mapPaths[0]);
			Map map = new Map(entitiesController, mapConfig);
//			Cannon<Bomb> bomb = new Cannon<Bomb>(entitiesController, new BombFactory(), 1, true,100);
			Ship<?,?> ship1 = createDefaultShip(map.getSpawnPoints()[0]);
			Ship<?,?> ship2 = createDefaultShip(map.getSpawnPoints()[1]);

			players = new ArrayList<>();
			
			//used for extendViewport
			players.add(new Player(ship2, 
					new InputConfiguration(InputType.WASD), 
					0, 0, //x,y
					0.5f, 1, // width, height
					0));
			players.add(new Player(ship1, 
					new InputConfiguration(InputType.ARROWS), 
					0.5f, 0,
					0.5f, 1,
					0));
			
			List<InputListener> listeners = new ArrayList<>();
			
			for(Player p : players){
				ActorPlayer ap = new ActorPlayer(p);
				statsStage.addActor(ap);
				listeners.add(p);
			}
			
			fontTest = new BitmapFont();
			
			
			
			inputManager = new InputManager(listeners, INPUT_MODE.GAME);
			Gdx.input.setInputProcessor(inputManager);
			
		
			
		}
		
		private Ship<?,?> createDefaultShip(Vector2 spawnPoint){
			Cannon<Bullet> bullets = new Cannon<Bullet>(entitiesController, new Bullet.NormalShotFactory(), 1, true, 100);
			Cannon<SeekerMissile> seeker = new Cannon<SeekerMissile>(entitiesController, new SeekerMissile.SeekerFactory(), 3, true, 100);
			
			
			return new Ship<>(entitiesController, spawnPoint, bullets, seeker);
		}

		@Override
		public void render(float delta) {

//			camera.update();
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			entitiesController.lockQueue();
			// Step the physics simulation forward at a rate of 60hz
	        world.step(1f/60f, 6, 2);
			
	        Camera camera = stage.getCamera();
			entitiesController.unlockQueue();
			
			for(Player p : players){
				
				Vector3 pos0 = camera.position;		
				Vector2 pos1 = p.getCameraPos();
				Vector3 posOffset = pos0.cpy().sub(pos1.x+Gdx.graphics.getWidth()*p.getPosOffset(),pos1.y, 0);
				
				stage.getCamera().translate(posOffset.scl(-1));
				
		        stage.getViewport().setScreenX((int) (Gdx.graphics.getWidth()*p.getScreenX()));
		        stage.getViewport().setScreenWidth((int) (Gdx.graphics.getWidth()*p.getWidth()));
		        stage.getViewport().setScreenHeight(Gdx.graphics.getHeight()-statsBarHeight);
		        stage.getViewport().apply();
				stage.draw();
				
				
				statsStage.getViewport().setScreenX(0);
				statsStage.getViewport().setScreenY(Gdx.graphics.getHeight()-statsBarHeight);
				statsStage.getViewport().setScreenWidth(Gdx.graphics.getWidth());
				statsStage.getViewport().setScreenHeight(statsBarHeight);
				statsStage.getViewport().apply();
				statsStage.draw();
				
			}
		}
		
		@Override
		public void resize(int width, int height) {
			// TODO Auto-generated method stub
//			super.resize(width, height);
			
		}
		
		@Override
		public void dispose () {
//			batch.dispose();
			stage.dispose();
			world.dispose();
			inputManager.cancel();
//			timer.clear();
//			img.dispose();
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void hide() {
			// TODO Auto-generated method stub
			
		}
}
