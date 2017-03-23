package com.vormadal.turborocket.controllers;

import com.badlogic.gdx.Game;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.models.configs.PlayerConfig;
import com.vormadal.turborocket.models.configs.ScreenConfig;
import com.vormadal.turborocket.screens.GameScreen;
import com.vormadal.turborocket.screens.MapSelectScreen;
import com.vormadal.turborocket.screens.MenuScreen;
import com.vormadal.turborocket.screens.PlayerOptionsScreen;
import com.vormadal.turborocket.screens.SettingsScreen;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputConfiguration.InputType;

public class TurboRocketWarsGame extends Game  {

	private PlayerConfig[] players;
	private MapConfig selectedMap;
	public enum GameMode {SINGLE_PLAYER, MULTI_PLAYER}; //ONLINE ?
	private GameMode mode = GameMode.SINGLE_PLAYER;
	
	//screens
	private MenuScreen menuScreen;
	private MapSelectScreen mapSelectScreen;
	private PlayerOptionsScreen playerOptionsScreen;
	private SettingsScreen settingsScreen;
	private GameScreen gameScreen;
	private ConfigManager configManager;
	
	public TurboRocketWarsGame(ConfigManager configManager) {
		this.configManager = configManager;
	}
	
	@Override
	public void create() {
		this.configManager.initialize();
		this.menuScreen = new MenuScreen(this);
		this.mapSelectScreen = new MapSelectScreen(this);
		this.playerOptionsScreen = new PlayerOptionsScreen(this);
		this.settingsScreen = new SettingsScreen(this);
//		this.gameScreen = new GameScreen(this, map, numPlayers)
		setScreen(menuScreen);
	}
	
	public void selectGameMode(GameMode mode){
		this.mode = mode;
		if(mode.equals(GameMode.SINGLE_PLAYER)){
			PlayerConfig p1 = new PlayerConfig();
			p1.inputConfig = new InputConfiguration(InputType.ARROWS);
			ScreenConfig s1 = new ScreenConfig(0, 0, 1, 1);
			p1.screen = s1;
			players = new PlayerConfig[]{p1};
		}else if(mode.equals(GameMode.MULTI_PLAYER)){
			PlayerConfig p1 = new PlayerConfig();
			PlayerConfig p2 = new PlayerConfig();
			
			p1.inputConfig = new InputConfiguration(InputType.ARROWS);
			ScreenConfig s1 = new ScreenConfig(0, 0, 0.5f, 1);
			p1.screen = s1;
			
			p2.inputConfig = new InputConfiguration(InputType.WASD);	
			ScreenConfig s2 = new ScreenConfig(0.5f, 0, 0.5f, 1);
			p2.screen = s2;
			players = new PlayerConfig[]{p1, p2};
		}
				
		gotoMapSelect();
	}
	public void selectMap(MapConfig config){
		selectedMap = config;
		this.setScreen(playerOptionsScreen);
	}
	
	public MapConfig getSelectedMap(){
		return this.selectedMap;
	}
	
	public void gotoMapSelect(){
		this.setScreen(mapSelectScreen);
	}
	public void gotoSettings(){
		this.setScreen(settingsScreen);
	}
	public void gotoMainMenu(){
		this.setScreen(menuScreen);
	}
	public void gotoGame(){
		this.setScreen(mapSelectScreen);
	}
	
	public PlayerConfig[] getPlayerConfigs(){
		return this.players;
	}
	public int getNumberOfPlayers(){
		return this.players.length;
	}
			
	public void pauseGame(){
		
	}	
	
	public void exitGame(){
		System.exit(0); //TODO maybe some cleanup?
	}
}
