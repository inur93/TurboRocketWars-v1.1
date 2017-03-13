package com.vormadal.turborocket.models;

import com.badlogic.gdx.math.Vector2;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputListener;

public class Player implements InputListener{

	private Ship<?,?> ship;
	private InputConfiguration config;
	private float screenX, screenY, width, height;
	private float posOffset;
	
	public Player(Ship<?,?> ship, 
			InputConfiguration config, 
			float screenXPct, float screenYPct, 
			float widthPct, float heightPct,
			float posOffsetPct){
		this.setScreenX(screenXPct);
		this.setScreenY(screenYPct);
		this.setWidth(widthPct);
		this.setHeight(heightPct);
		this.ship = ship;
		this.config = config;
		this.setPosOffset(posOffsetPct);
	}
	
	public Vector2 getCameraPos(){
		return ship.getBody().getPosition();
	}
	
	public InputConfiguration getInputConfig(){
		return this.config;
	}
	
	public Ship<?,?> getShip(){
		return this.ship;
	}

	public float getScreenY() {
		return screenY;
	}

	public void setScreenY(float screenY) {
		this.screenY = screenY;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getScreenX() {
		return screenX;
	}

	public void setScreenX(float screenX) {
		this.screenX = screenX;
	}

	public float getPosOffset() {
		return posOffset;
	}

	public void setPosOffset(float posOffset) {
		this.posOffset = posOffset;
	}

	@Override
	public InputConfiguration getInputConfiguration() {
		return this.config;
	}

	@Override
	public void setInputConfiguration(InputConfiguration config) {
		this.config = config;
	}

	@Override
	public void left() {
		this.ship.left();
	}

	@Override
	public void right() {
		this.ship.right();
	}

	@Override
	public void boost() {
		this.ship.boost();
	}

	@Override
	public void shootNormal() {
		this.ship.shootNormal();
	}

	@Override
	public void shootSpecial() {
		this.ship.shootSpecial();
	}

	@Override
	public void leftRightReleased() {
		this.ship.stopRotation();
	}

	@Override
	public void esc() {
		//TODO open menu maybe - at the moment intended for pausing game
	}

	@Override
	public void enter() {}
}
