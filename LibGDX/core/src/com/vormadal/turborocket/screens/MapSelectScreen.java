package com.vormadal.turborocket.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.vormadal.turborocket.configurations.ButtonStyles;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.configurations.LabelStyles;
import com.vormadal.turborocket.configurations.PropKeys;
import com.vormadal.turborocket.configurations.Styles;
import com.vormadal.turborocket.controllers.InputManager;
import com.vormadal.turborocket.controllers.InputManager.INPUT_MODE;
import com.vormadal.turborocket.controllers.TurboRocketWarsGame;
import com.vormadal.turborocket.models.actors.ActorBackground;
import com.vormadal.turborocket.models.actors.ActorMap;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputConfiguration.InputType;
import com.vormadal.turborocket.utils.InputListener;

public class MapSelectScreen implements Screen, InputListener{			

	
	private Stage backgroundStage;
	private Stage mapsStage;
	private Stage overlayStage; //for buttons and other info
	final float PIXELS_TO_METERS = 100f;

	private float buttonWidth = 50;
	private float buttonHeight = 300;
	private InputManager inputManager;
	private String title = "SELECT MAP";
	
	private Label nameLbl, descLbl, sizeLbl, numPlayerLbl;
	
	private TextButton leftArrow, rightArrow;
	
	private InputConfiguration inputConfiguration = new InputConfiguration(InputType.ARROWS);
	private ArrayList<InputListener> listeners = new ArrayList<>();
	private List<ActorMap> maps = new ArrayList<>();
	private List<MapConfig> mapConfigs;
	private int currentMap = 0;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	
	private TurboRocketWarsGame game;
	
	/**
	 * 
	 * 
	 */
	public MapSelectScreen(TurboRocketWarsGame game) {
		this.game = game;
		listeners.add(this);
		create();
	}

	public void create() {
		ConfigManager.instance().loadDefaultSettings();
		//viewport width should be determined by number of players playing on same screen.
		//the width is the size of the world that should be visible, thus the viewport is smaller on splitscreen.
		backgroundStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		mapsStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		overlayStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		backgroundStage.addActor(new ActorBackground(ConfigManager.instance().getSettingValue(Styles.Const.mapSelectMenuBackground)));
		
		Label titleLabel = new Label(title, LabelStyles.getTitleStyle());
		
		titleLabel.setPosition(Gdx.graphics.getWidth()/2-titleLabel.getWidth()/2, Gdx.graphics.getHeight()-100);
		overlayStage.addActor(titleLabel);
		
		float mapWidth = Gdx.graphics.getWidth()*0.75f;
		float mapHeight = Gdx.graphics.getHeight()*0.60f;

		float labelX = Gdx.graphics.getWidth()/2-mapWidth/2 + 10;
		float labelY = Gdx.graphics.getHeight()/2+mapHeight/2;
		float labelYOffset = -25;
//		nameLbl, descLbl, sizeLbl, numPlayerLbl;
		nameLbl = new Label("", LabelStyles.getDefaultStyle());
		nameLbl.setPosition(labelX, labelY += labelYOffset);
		descLbl = new Label("", LabelStyles.getDefaultStyle());
		descLbl.setPosition(labelX, labelY += labelYOffset);
		sizeLbl = new Label("", LabelStyles.getDefaultStyle());
		sizeLbl.setPosition(labelX, labelY += labelYOffset);
		numPlayerLbl = new Label("", LabelStyles.getDefaultStyle());
		numPlayerLbl.setPosition(labelX, labelY += labelYOffset);
		
		overlayStage.addActor(nameLbl);
		overlayStage.addActor(descLbl);
		overlayStage.addActor(sizeLbl);
		overlayStage.addActor(numPlayerLbl);
		
		float mapX = Gdx.graphics.getWidth()/2-mapWidth/2;
		float mapY = Gdx.graphics.getHeight()/2-mapHeight/2;
		float xOffset = Gdx.graphics.getWidth();

		this.mapConfigs = ConfigManager.instance().getMaps();
		for(MapConfig config : mapConfigs){

			ActorMap actor = new ActorMap(config);
			
			//					actor.scaleBy(1.375f, 1.75f);
			actor.setSize(mapWidth, mapHeight);
			actor.setOrigin(1); // center
			actor.setPosition(mapX, mapY);
			this.maps.add(actor);
			this.mapsStage.addActor(actor);
			mapX += xOffset;

		}

		leftArrow = new TextButton("", ButtonStyles.leftArrowStyle());
		leftArrow.setSize(buttonWidth, buttonHeight);
		leftArrow.setPosition(0, Gdx.graphics.getHeight()/2-leftArrow.getHeight()/2);
		leftArrow.setDisabled(true);
		leftArrow.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				left();
			}
		});
		this.overlayStage.addActor(leftArrow);
		
		rightArrow = new TextButton("", ButtonStyles.rightArrowStyle());
		rightArrow.setSize(buttonWidth, buttonHeight);
		rightArrow.setPosition(Gdx.graphics.getWidth()-buttonWidth, Gdx.graphics.getHeight()/2-rightArrow.getHeight()/2);
		rightArrow.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				right();
			}
		});
		this.overlayStage.addActor(rightArrow);
		
		TextButton selectBtn = new TextButton("SELECT", ButtonStyles.defaultStyle());
		selectBtn.setPosition(Gdx.graphics.getWidth()/2-selectBtn.getWidth()/2, 10);
		selectBtn.addListener(new ChangeListener() {	
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				next();
			}
		});
		this.overlayStage.addActor(selectBtn);
		
		inputManager = new InputManager(listeners, INPUT_MODE.SIMPLE);
		inputManager.addInputProcessor(overlayStage);
		updateMapInfo();

	}
	
	private void back(){
		game.gotoMainMenu();
	}
	
	private void next(){
		game.selectMap(mapConfigs.get(currentMap));
	}

	@Override
	public void render(float delta) {

		//				camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		Camera cam = this.mapsStage.getCamera();
//		float camX = cam.position.x;
		
		ActorMap map = this.maps.get(currentMap);
		float mapX = map.getX()+map.getWidth()/2;
		
		if(moveLeft){
			cam.translate(-Gdx.graphics.getWidth()/30, 0, 0);
			if(cam.position.x <= mapX){
				cam.translate(mapX-cam.position.x, 0, 0);
				moveLeft = false;
			}
		}
		if(moveRight){
			cam.translate(Gdx.graphics.getWidth()/30, 0, 0);
			if(cam.position.x >= mapX){
				cam.translate(mapX-cam.position.x, 0, 0);
				moveRight = false;
			}
		}
		
		backgroundStage.act(delta);
		mapsStage.act(delta);
		overlayStage.act(delta);
		
		backgroundStage.draw();
		mapsStage.draw();
		overlayStage.draw();

	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void dispose () {
		mapsStage.dispose();
	}

	@Override
	public void show() {
		mapsStage.getViewport().setScreenHeight(Gdx.graphics.getHeight());
		mapsStage.getViewport().setScreenWidth(Gdx.graphics.getWidth());
		mapsStage.getViewport().setScreenX(0);
		mapsStage.getViewport().setScreenY(0);
		mapsStage.getViewport().apply();
		Gdx.input.setInputProcessor(this.inputManager);
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

	@Override
	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	@Override
	public void setInputConfiguration(InputConfiguration config) {
		// TODO Auto-generated method stub

	}
	
	private void updateMapInfo(){
		MapConfig map = mapConfigs.get(currentMap);
		nameLbl.setText("name: " + map.name);
		sizeLbl.setText("size: " + map.width + "x" + map.height);
		descLbl.setText("description: " + map.description);
		numPlayerLbl.setText("number of players: " + map.platforms.size());
	}

	@Override
	public void left() {
		currentMap--;
		if(currentMap <= 0) {
			currentMap = 0;
			leftArrow.setDisabled(true);
		}
		rightArrow.setDisabled(false);
		moveLeft = true;
		moveRight = false;
		updateMapInfo();
	}

	@Override
	public void right() {
		currentMap++;
		if(currentMap >= this.maps.size()-1){
			currentMap = this.maps.size()-1;
			rightArrow.setDisabled(true);
		}
		leftArrow.setDisabled(false);
		moveRight = true;
		moveLeft = false;
		updateMapInfo();
		
	}

	@Override
	public void boost() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shootNormal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shootSpecial() {
		// TODO Auto-generated method stub

	}

	@Override
	public void leftRightReleased() {
		// TODO Auto-generated method stub

	}

	@Override
	public void esc() {
		back();
	}

	@Override
	public void enter() {
		next();
	}
}


