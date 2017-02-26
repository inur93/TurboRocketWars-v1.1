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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.vormadal.turborocket.models.actors.ActorSlider;
import com.vormadal.turborocket.utils.Setting;
import com.vormadal.turborocket.utils.Setting.SettingType;
import com.vormadal.turborocket.utils.Setting.ValueType;
import com.vormadal.turborocket.utils.Styles;
import com.vormadal.turborocket.utils.XMLParser;

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
		this. settings = new XMLParser().readSettingsConfig("C:/workspace/TurboRocketWars-v1.1/LibGDX/core/assets/default-settings.config");
//		this.settings = new ArrayList<>();
//		settings.add(createSetting("setting1", "0", "0", "10", "", SettingType.SLIDER, ValueType.INT));
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



	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		int yPos = 100;
		for(Setting s : this.settings){
			if(s.settingType.equals(SettingType.SLIDER)){
				TextField test = new TextField("test", Styles.getTextFieldStyle());
				Label sliderLabel = new Label(s.name, Styles.getLabelStyle());
				Label sliderValue = new Label(s.value, Styles.getLabelStyle());
				ActorSlider slider = new ActorSlider(s, test, yPos);

				//		System.out.println("label width: " + sliderLabel.getWidth());
				//		sliderLabel.setOrigin(sliderLabel.getWidth()/2, -sliderLabel.getHeight()/2);
				sliderLabel.setPosition(Gdx.graphics.getWidth()/2 - (100+sliderLabel.getWidth()), yPos - sliderLabel.getHeight()/2);
				sliderValue.setPosition(Gdx.graphics.getWidth()/2 + 100, yPos - sliderValue.getHeight()/2);
				test.setPosition(Gdx.graphics.getWidth()/2 + 100, yPos - test.getHeight()/2);
				stage.addActor(slider);
				stage.addActor(sliderLabel);
				stage.addActor(sliderValue);
			}
			yPos += 25;
		}

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
