package com.vormadal.turborocket.controllers;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vormadal.turborocket.configurations.ButtonStyles;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.models.actors.ActorShip;
import com.vormadal.turborocket.models.configs.PlayerConfig;
import com.vormadal.turborocket.models.configs.ScreenConfig;
import com.vormadal.turborocket.models.configs.ShipConfig;
import com.vormadal.turborocket.screens.PlayerOptionsScreen;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputListener;

public class PlayerOptionsController implements InputListener{

	private Stage shipStage, infoStage;
	private int currentShip;
	private TextButton leftArrow;
	private TextButton rightArrow;
	private boolean moveRight, moveLeft;
	private List<ActorShip> ships;
	private List<ShipConfig> shipConfigs;
	private PlayerConfig player;
	private ScreenConfig screenConfig;
	//	private static int test = 0;
	private PlayerOptionsScreen controller;
	public PlayerOptionsController(PlayerConfig player, PlayerOptionsScreen controller){
		this.controller = controller;
		this.player = player;
		this.screenConfig = player.screen;
		create();
	}

	private void create(){
		shipStage = new Stage(new FitViewport(Gdx.graphics.getWidth()*screenConfig.width, Gdx.graphics.getHeight()));
		infoStage = new Stage(new FitViewport(Gdx.graphics.getWidth()*screenConfig.width, Gdx.graphics.getHeight()));

		float width = 20;
		float height = 100;
		float y = Gdx.graphics.getHeight()*screenConfig.height/2;
		float xLeft = 0;
		float xRight = Gdx.graphics.getWidth()*screenConfig.width-width;
		System.out.println("buttons: left " + xLeft + ";right " +  xRight + ";y " + y);
		leftArrow = createButton(width, height, xLeft, y, ButtonStyles.leftArrowStyle(), true);
		rightArrow = createButton(width, height, xRight, y, ButtonStyles.rightArrowStyle(), false);
		infoStage.addActor(leftArrow);
		infoStage.addActor(rightArrow);
		
		
		float mapX = Gdx.graphics.getWidth()/2-width/2;
		float mapY = Gdx.graphics.getHeight()/2-height/2;
		float xOffset = Gdx.graphics.getWidth();

		this.shipConfigs = ConfigManager.instance().getShips();
		for(ShipConfig config : shipConfigs){

			ActorShip actor = new ActorShip(null, config);
			
			//					actor.scaleBy(1.375f, 1.75f);
			actor.setSize(width, height);
			actor.setOrigin(1); // center
			actor.setPosition(mapX, mapY);
			this.ships.add(actor);
			this.shipStage.addActor(actor);
			mapX += xOffset;

		}
//		new XMLReader().load
		
	}

	private TextButton createButton(float width, float height, float posX, float posY, TextButtonStyle style, boolean disabled){
		TextButton button = new TextButton("", style);
		button.setSize(width, height);
		button.setPosition(posX, posY);
		button.setDisabled(disabled);
		//		button.addListener(listener);
		return button;
	}

	private void nextShip(){
		currentShip++;
		if(currentShip >= shipConfigs.size()) {
			currentShip = shipConfigs.size()-1;
			rightArrow.setDisabled(true);
		}
		leftArrow.setDisabled(false);
		moveLeft = false;
		moveRight = true;
		updateShipInfo();
	}

	private void setupStage(Stage stage){
		float width = Gdx.graphics.getWidth()*screenConfig.width;
		float height = Gdx.graphics.getHeight()*screenConfig.height;
		float x = Gdx.graphics.getWidth()*screenConfig.screenX;
		float y = Gdx.graphics.getHeight()*screenConfig.screenY;
		stage.getViewport().setScreenX((int) x);
		stage.getViewport().setScreenY((int) y);

		stage.getViewport().setScreenWidth((int) width);
		stage.getViewport().setScreenHeight((int) height);
		stage.getViewport().apply(true);
	}

	private void previousShip(){
		currentShip--;
		if(currentShip <= 0) {
			currentShip = 0;
			leftArrow.setDisabled(true);
		}
		rightArrow.setDisabled(false);
		moveLeft = true;
		moveRight = false;
		updateShipInfo();
	}
	public void render(float delta){

//		Camera cam = shipStage.getCamera();
//		if(moveLeft){
//			cam.translate(-Gdx.graphics.getWidth()/30, 0, 0);
//			if(cam.position.x <= mapX){
//				cam.translate(mapX-cam.position.x, 0, 0);
//				moveLeft = false;
//			}
//		}
//		if(moveRight){
//			cam.translate(Gdx.graphics.getWidth()/30, 0, 0);
//			if(cam.position.x >= mapX){
//				cam.translate(mapX-cam.position.x, 0, 0);
//				moveRight = false;
//			}
//		}
		setupStage(shipStage);
		shipStage.act();
		shipStage.draw();

		setupStage(infoStage);
		infoStage.act();
		infoStage.draw();


	}

	public void updateShipInfo(){

	}

	@Override
	public InputConfiguration getInputConfiguration() {
		return this.player.inputConfig;
	}

	@Override
	public void setInputConfiguration(InputConfiguration config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void left() {
		previousShip();
	}

	@Override
	public void right() {
		nextShip();
	}

	@Override
	public void boost() {
	}

	@Override
	public void shootNormal() {
		Viewport v = infoStage.getViewport();
		System.out.println("viewport: " + v.getScreenWidth() + ";" + v.getScreenHeight() + "--" + v.getScreenX() + ";" + v.getScreenY());
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

	}

	@Override
	public void enter() {
		// TODO Auto-generated method stub

	}
}
