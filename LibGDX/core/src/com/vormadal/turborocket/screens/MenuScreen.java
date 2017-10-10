package com.vormadal.turborocket.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.vormadal.turborocket.configurations.ButtonStyles;
import com.vormadal.turborocket.configurations.Styles;
import com.vormadal.turborocket.controllers.TurboRocketWarsGame;
import com.vormadal.turborocket.controllers.TurboRocketWarsGame.GameMode;

public class MenuScreen extends BasicScreen {
	Skin skin;
	
	SpriteBatch batch;
 
	private TurboRocketWarsGame game;
	public MenuScreen(TurboRocketWarsGame g){
		super("Menu", Styles.Const.mainMenuBackground, null);
		create();
		this.game=g;
	}
	
	public void create(){
		batch = new SpriteBatch();
		
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		
		TextButtonStyle textButtonStyle = ButtonStyles.defaultStyle();
 
		int initOffset = -300;
		int buttonWidth = 200;
		int nrmOffset = -150;
		int pos = Gdx.graphics.getHeight()+initOffset;
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		final TextButton singlePlayerButton = new TextButton("Single player",textButtonStyle);
		singlePlayerButton.addListener(new ChangeListener() {	
			public void changed (ChangeEvent event, Actor actor) {
				game.selectGameMode(GameMode.SINGLE_PLAYER);
			}
		});
		placeWidget(singlePlayerButton, Position.BOT_MID, 0, pos);
		pos += nrmOffset;
		
		final TextButton multiPlayerButton = new TextButton("Multi player",textButtonStyle);
		placeWidget(multiPlayerButton, Position.BOT_MID, 0, pos);
		pos += nrmOffset;
		multiPlayerButton.addListener(new ChangeListener() {	
			public void changed (ChangeEvent event, Actor actor) {
				game.selectGameMode(GameMode.MULTI_PLAYER);
			}
		});
		
		final TextButton settingsButton = new TextButton("Settings",textButtonStyle);
		placeWidget(settingsButton, Position.BOT_MID, 0, pos);
		pos += nrmOffset;
		settingsButton.addListener(new ChangeListener() {	
			public void changed (ChangeEvent event, Actor actor) {
				game.gotoSettings();
			}
		});
		
		
		final TextButton exitButton = new TextButton("Exit",textButtonStyle);
		placeWidget(exitButton, Position.BOT_MID, 0, pos);
		exitButton.addListener(new ChangeListener() {	
			public void changed (ChangeEvent event, Actor actor) {
				game.exitGame();
			}
		});
	}
 
	public void render (float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
//		Table.drawDebug(stage);
//		Table.
	}
 
	@Override
	public void resize (int width, int height) {
//		stage.setViewport(width, height, false);
		stage.getViewport().setScreenSize(width, height);
	}
 
	@Override
	public void dispose () {
		stage.dispose();
		skin.dispose();
	}
 
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.stage);
	}
 
	@Override
	public void hide() {
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
	

}
