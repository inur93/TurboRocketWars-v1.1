package com.vormadal.turborocket.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.vormadal.turborocket.configurations.Styles;
import com.vormadal.turborocket.controllers.InputManager;
import com.vormadal.turborocket.controllers.InputManager.INPUT_MODE;
import com.vormadal.turborocket.controllers.PlayerOptionsController;
import com.vormadal.turborocket.controllers.TurboRocketWarsGame;
import com.vormadal.turborocket.models.configs.PlayerConfig;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputConfiguration.InputType;
import com.vormadal.turborocket.utils.InputListener;
import com.vormadal.turborocket.utils.InputListenerAdapter;

public class PlayerOptionsScreen extends BasicScreen{			

	private boolean isInitialized = false;
	private TurboRocketWarsGame game;
	private List<PlayerOptionsController> views = new ArrayList<>();
	private InputManager inputManager;
	private Stage backgroundStage;
	/**
	 * 
	 * @param numPlayers - should be 1 or 2
	 */
	public PlayerOptionsScreen(TurboRocketWarsGame game) {
		super("", Styles.Const.playerOptionsMenuBackground, new InputConfiguration(InputType.ARROWS));
		this.game = game;
	}

	public void create() {
		if(isInitialized){
			dispose(); // release all current resources before creating new ones.
		}
		PlayerConfig[] players = game.getPlayerConfigs();
		List<InputListener> listeners = new ArrayList<>();
		int numPlayers = players.length;
		for(int i = 0; i < numPlayers; i++){
			PlayerConfig player = players[i];
			
			//viewport width should be determined by number of players playing on same screen.
			//the width is the size of the world that should be visible, thus the viewport is smaller on splitscreen.
			
			
			PlayerOptionsController poc = new PlayerOptionsController(player, this);
			views.add(poc);
			listeners.add(poc);
			
		}
		listeners.add(this);
		inputManager = new InputManager(listeners, INPUT_MODE.SIMPLE);
		
	}
	


	@Override
	public void render(float delta) {

		//				camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		backgroundStage.draw();
		for(PlayerOptionsController v : this.views){
			v.render(delta);
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//				super.resize(width, height);

	}

	@Override
	public void dispose () {
		//				batch.dispose();
//		stage.dispose();
//		world.dispose();
//		timer.clear();
		//				img.dispose();
	}

	@Override
	public void show() {
		if(!isInitialized || game.getNumberOfPlayers() != this.views.size()){
			create();
		}

		
		Gdx.input.setInputProcessor(inputManager);
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
	public void esc() {
		this.game.gotoMapSelect();
	}
	
	
}


