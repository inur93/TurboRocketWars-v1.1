package com.vormadal.turborocket.models;

import com.badlogic.gdx.math.Vector2;
import com.vormadal.turborocket.utils.InputConfiguration;

public class Player {

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
}
