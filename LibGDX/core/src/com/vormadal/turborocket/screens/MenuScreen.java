package com.vormadal.turborocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.vormadal.turborocket.TurboRocketWarsGame;
import com.vormadal.turborocket.models.actors.ActorBackground;
import com.vormadal.turborocket.utils.styles.ButtonStyles;
import com.vormadal.turborocket.utils.styles.Styles;

public class MenuScreen implements Screen {
	Skin skin;
	Stage stage;
	
	SpriteBatch batch;
 
	private Game g;
	public MenuScreen(Game g){
		create();
		this.g=g;
	}
 
	public MenuScreen(){
		create();
		
	}
	
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		
		Actor background = new ActorBackground("menu/frozen-olaf-background.png");
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
//				g.setScreen(new GameScreen());
				g.setScreen(new MapSelectScreen(g, 1));
			}
		});
		singlePlayerButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, pos);
		pos += nrmOffset;
		
		final TextButton multiPlayerButton = new TextButton("Multi player",textButtonStyle);
		multiPlayerButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, pos);
		pos += nrmOffset;
		
		final TextButton settingsButton = new TextButton("Settings",textButtonStyle);
		settingsButton.addListener(new ChangeListener() {	
			public void changed (ChangeEvent event, Actor actor) {
				g.setScreen( new SettingsScreen());
			}
		});
		settingsButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, pos);
		pos += nrmOffset;
		
		final TextButton exitButton = new TextButton("Exit",textButtonStyle);
		exitButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, pos);
		pos += nrmOffset;
		
		
		stage.addActor(background);
		stage.addActor(singlePlayerButton);
		stage.addActor(multiPlayerButton);
		stage.addActor(settingsButton);
		stage.addActor(exitButton);
		
//		stage.addActor(textButton);
//		stage.addActor(textButton);
 
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
		// TODO Auto-generated method stub
 
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
