package com.vormadal.turborocket.screens;

import static com.vormadal.turborocket.utils.PropKeys.getMapConfig;
import static com.vormadal.turborocket.utils.PropKeys.getMapKeys;
import static com.vormadal.turborocket.utils.PropKeys.getStatsBarHeight;

import java.util.ArrayList;

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

public class PlayerOptionsMenu implements Screen{			

			private Stage[] shipSelectionStage;
			private Stage[] overlayStage;
			
			Fragment frag;
			
			final float PIXELS_TO_METERS = 100f;
			private Timer timer;
			private InputManager inputManager;
			
			private ArrayList<Player> players;
			
			//debug
		    Matrix4 debugMatrix;
		    OrthographicCamera camera;
		    BitmapFont font;
		    
		    /**
		     * 
		     * @param numPlayers - should be 1 or 2
		     */
		    public PlayerOptionsMenu(int numPlayers) {
		    	create(numPlayers);
		    }
		    
			public void create(int numPlayers) {
				//make sure that the number is 1 or 2
				numPlayers = (numPlayers > 2 ? 2 : numPlayers < 1 ? 1 : numPlayers);
				PropKeys.setDefault();
				//viewport width should be determined by number of players playing on same screen.
				//the width is the size of the world that should be visible, thus the viewport is smaller on splitscreen.
				stage = new Stage(new FitViewport(Gdx.graphics.getWidth()/numPlayers, Gdx.graphics.getHeight()));
					
				String[] mapPaths = getMapKeys();

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
				
				for(Player p : players){
					ActorPlayer ap = new ActorPlayer(p);
					statsStage.addActor(ap);
				}
				
				fontTest = new BitmapFont();
				
				inputManager = new InputManager(players);
				Gdx.input.setInputProcessor(inputManager);
				
				timer = new Timer();
				timer.scheduleTask(inputManager, 0f, 1.0f/60f);
				
			}
			
			private Ship<?,?> createDefaultShip(Vector2 spawnPoint){
				Cannon<Bullet> bullets = new Cannon<Bullet>(entitiesController, new Bullet.NormalShotFactory(), 1, true, 100);
				Cannon<SeekerMissile> seeker = new Cannon<SeekerMissile>(entitiesController, new SeekerMissile.SeekerFactory(), 3, true, 100);
				
				
				return new Ship<>(entitiesController, spawnPoint, bullets, seeker);
			}

			@Override
			public void render(float delta) {

//				camera.update();
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
				

//				Vector2 pos2s = ship2.getBody().getPosition();
//				Vector3 pos2c = camera.position;		
//				Vector3 pos2diff = pos2c.cpy().sub(pos2s.x+Gdx.graphics.getWidth()/4, pos2s.y, 0);
		//
//				stage.getCamera().translate(pos2diff.scl(-1));
////				Gdx.gl.glViewport(Gdx.graphics.getWidth()/2, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());
////				stage.getViewport().setScreenBounds(Gdx.graphics.getWidth()/2, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());
//				stage.getViewport().setWorldWidth(Gdx.graphics.getWidth());
//				stage.getViewport().setScreenWidth(Gdx.graphics.getWidth());
//				stage.getViewport().setScreenX(Gdx.graphics.getWidth()/2);
//				stage.getViewport().apply();
//				stage.draw();
				
				
				//		batch.begin();
//				batch.draw(img, 0, 0);
//				batch.end();
			}
			
			@Override
			public void resize(int width, int height) {
				// TODO Auto-generated method stub
//				super.resize(width, height);
				
			}
			
			@Override
			public void dispose () {
//				batch.dispose();
				stage.dispose();
				world.dispose();
				timer.clear();
//				img.dispose();
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


