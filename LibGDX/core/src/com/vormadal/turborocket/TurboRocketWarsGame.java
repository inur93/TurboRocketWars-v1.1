package com.vormadal.turborocket;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.actors.ActorFragment;
import com.vormadal.turborocket.models.actors.ActorShip;
import com.vormadal.turborocket.models.ammo.Bomb;
import com.vormadal.turborocket.models.ammo.Bomb.BombFactory;
import com.vormadal.turborocket.utils.B2Separator;
import com.vormadal.turborocket.utils.PropKeys;
import com.vormadal.turborocket.models.ammo.Cannon;
import com.vormadal.turborocket.models.ammo.Fragment;

public class TurboRocketWarsGame extends ApplicationAdapter  {
	
	private Stage stage;
	private SpriteBatch batch;
	private World world;
	Fragment frag;
	Ship ship;
	Ship ship2;
	final float PIXELS_TO_METERS = 100f;
	private Timer timer;
	private InputManager inputManager;
	
	
	
	//debug
	Body bodyEdgeScreen;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;
    BitmapFont font;
    
	@Override
	public void create () {
		PropKeys.setDefault();
		stage = new Stage(new ScreenViewport());
		world = new World(new Vector2(0f, -1f), true);
		
		frag = new Fragment(new Vector2(0f,0f), new Vector2(30, 30), new Vector2(0,0), world);
		frag.create();
//		stage.addActor(new ActorFragment(frag));
		
		Bomb bomb =  new Bomb.BombFactory().factory(
				new Vector2(), 
				new Vector2(50f,50f), 
				new Vector2(0f,1f), 
				world);
		Cannon<Bomb> stdCannon = new Cannon<Bomb>(new BombFactory(), 1, true,100, world);
		ship = new Ship<>(world, new Vector2(50f,50f), stdCannon, stdCannon);
		ship2 = new Ship<>(world, new Vector2(100f,50f), stdCannon, stdCannon);
		stage.addActor(new ActorShip(ship));
		stage.addActor(new ActorShip(ship2));
		
		createFloor();
		
		inputManager = new InputManager(ship, ship2);
		Gdx.input.setInputProcessor(inputManager);
		
		timer = new Timer();
		timer.scheduleTask(inputManager, 0f, 1.0f/60f);
	}
	
	private void createFloor(){
		
		// Create our body definition
		BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        float w = (float)Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer
        float h = (float)Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50f/PIXELS_TO_METERS;
        //bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
        bodyDef2.position.set(0,0);
        FixtureDef fixtureDef2 = new FixtureDef();

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef2.shape = edgeShape;

        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();

//        Gdx.input.setInputProcessor(this);

//        debugRenderer = new Box2DDebugRenderer();
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
//                getHeight());	
	}
	
	

	@Override
	public void render () {
//		camera.update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);
        Gdx.gl.glLineWidth(2);
        
        
		stage.draw();
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
		stage.dispose();
		world.dispose();
		timer.clear();
//		img.dispose();
	}

	
}