package test;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.vormadal.turborocket.models.actors.ActorSlider;
import com.vormadal.turborocket.utils.Setting;
import com.vormadal.turborocket.utils.Setting.SettingType;
import com.vormadal.turborocket.utils.Setting.ValueType;

import test.MenuScreen.Background;

public class SettingsScreen implements Screen {

	Skin skin;
	Stage stage;
	
	SpriteBatch batch;
 
	private List<Setting> settings;
	Game g;
	public SettingsScreen(){
		createTestSettings();
		create();
	}
	
	private void createTestSettings(){
		this.settings = new ArrayList<>();
		settings.add(createSetting("setting1", "0", "0", "10", "", SettingType.SLIDER, ValueType.INT));
	}
	
	private Setting createSetting(
			String name, String value, 
			String minValue,String maxValue, 
			String regEx, 
			SettingType settingType, ValueType valueType){
		Setting s = new Setting();
		s.name = name;
		s.settingType = settingType;
		s.value = value;
		s.valueType = valueType;
		s.validation.maxValue = maxValue;
		s.validation.minValue = minValue;
		s.validation.regEx = regEx;
		return s;
	}
//	public void create(){
//		
//		Skin skin = new Skin();
//		Slider slider=new Slider(0,100,1,false,skin);
//
//	    Container<Slider> container=new Container<Slider>(slider);
//	    container.setTransform(true);   // for enabling scaling and rotation
//	    container.size(100, 60);
//	    container.setOrigin(container.getWidth() / 2, container.getHeight() / 2);
//	    container.setPosition(100,200);
//	    container.setScale(3);  //scale according to your requirement
//	}


	
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		
//		Actor background = new Background();
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
//		skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
//		Pixmap pixmap = new Pixmap(200, 100, Format.RGBA8888);
//		pixmap.setColor(Color.BLUE);
//		pixmap.fill();
// 
//		skin.add("unselected", new Texture(Gdx.files.internal("menu/buttons/olaf-1.jpg")));
//		skin.add("selected", new Texture(Gdx.files.internal("menu/buttons/olaf-2.jpg")));
// 
//		// Store the default libgdx font under the name "default".
//		BitmapFont bfont=new BitmapFont();
////		bfont.scale(1);
//		skin.add("default",bfont);
// 
//		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
//		TextButtonStyle textButtonStyle = new TextButtonStyle();
//		textButtonStyle.up = skin.newDrawable("unselected", Color.LIGHT_GRAY);
//		textButtonStyle.down = skin.newDrawable("unselected", Color.LIGHT_GRAY);
//
//		textButtonStyle.over = skin.newDrawable("selected", Color.WHITE);
//		textButtonStyle.font = skin.getFont("default");
// 
//		skin.add("default", textButtonStyle);
//		
//		SliderStyle sliderStyle = new SliderStyle();
//		sliderStyle.background = skin.newDrawable("slider-background", Color.WHITE);
//		sliderStyle.knob = skin.newDrawable("slider-knob", Color.LIGHT_GRAY);
//		
//		skin.add("default-horizontal", sliderStyle);
 
//		int initOffset = -300;
//		int buttonWidth = 200;
//		int nrmOffset = -150;
//		int pos = Gdx.graphics.getHeight()+initOffset;
//		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
//		final TextButton singlePlayerButton = new TextButton("Single player",textButtonStyle);
//		singlePlayerButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, pos);
//		pos += nrmOffset;
//		
//		final TextButton multiPlayerButton = new TextButton("Multi player",textButtonStyle);
//		multiPlayerButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, pos);
//		pos += nrmOffset;
//		
//		final TextButton settingsButton = new TextButton("Settings",textButtonStyle);
//		settingsButton.addListener(new ChangeListener() {
//			
//			public void changed (ChangeEvent event, Actor actor) {
//				g.setScreen( new SettingsScreen());
// 
//			}
//		});
//		settingsButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, pos);
//		pos += nrmOffset;
//		
//		final TextButton exitButton = new TextButton("Exit",textButtonStyle);
//		exitButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, pos);
//		pos += nrmOffset;
		
		
//		Slider slider=new Slider(0,100,1,false,skin);
//
//		//
//			    Container<Slider> container=new Container<Slider>(slider);
//			    container.setTransform(true);   // for enabling scaling and rotation
////			    container.size(100, 60);
//			    container.setOrigin(container.getWidth() / 2, container.getHeight() / 2);
//			    container.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
//			    container.setScale(3);  //scale according to your requirement
//		
		ActorSlider slider = new ActorSlider();
			    stage.addActor(slider);
//			    stage.addActor(slider);
//		stage.addActor(background);
//		stage.addActor(singlePlayerButton);
//		stage.addActor(multiPlayerButton);
//		stage.addActor(settingsButton);
//		stage.addActor(exitButton);
		
//		stage.addActor(textButton);
//		stage.addActor(textButton);
 
	}
 
	public void render (float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
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
