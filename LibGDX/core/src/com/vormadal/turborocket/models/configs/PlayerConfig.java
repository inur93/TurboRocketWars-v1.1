package com.vormadal.turborocket.models.configs;

import com.vormadal.turborocket.utils.InputConfiguration;

public class PlayerConfig {

	public ShipConfig ship;
	public ScreenConfig screen;
	public InputConfiguration inputConfig;
	
	public float posOffset;
	
	public PlayerConfig(){
		//on purpose
	}
	
	public PlayerConfig(ShipConfig ship, InputConfiguration inputConfig, ScreenConfig screenConfig, float posOffset){
		this.ship = ship;
		this.inputConfig = inputConfig;
		this.screen = screenConfig;
		this.posOffset = posOffset;
	}
}
