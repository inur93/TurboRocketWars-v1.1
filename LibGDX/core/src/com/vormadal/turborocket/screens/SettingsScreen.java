package com.vormadal.turborocket.screens;

import static com.vormadal.turborocket.configurations.Styles.getScrollStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.vormadal.turborocket.configurations.ButtonStyles;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.configurations.LabelStyles;
import com.vormadal.turborocket.configurations.Setting;
import com.vormadal.turborocket.configurations.Styles;
import com.vormadal.turborocket.configurations.XMLReader;
import com.vormadal.turborocket.controllers.InputManager;
import com.vormadal.turborocket.controllers.InputManager.INPUT_MODE;
import com.vormadal.turborocket.controllers.TurboRocketWarsGame;
import com.vormadal.turborocket.exceptions.NoConfigException;
import com.vormadal.turborocket.models.actors.ActorBackground;
import com.vormadal.turborocket.models.actors.settings.ActorSetting;
import com.vormadal.turborocket.models.actors.settings.ActorSlider;
import com.vormadal.turborocket.models.actors.settings.ActorToggle;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputConfiguration.InputType;
import com.vormadal.turborocket.utils.InputListener;
import com.vormadal.turborocket.utils.InputListenerAdapter;


public class SettingsScreen extends BasicScreen{

	private static final String SETTINGS_CONFIG = "default-settings.config";
	
	private TurboRocketWarsGame game;
	private InputManager inputManager;
	
	private List<ActorSetting> settings;
	public SettingsScreen(TurboRocketWarsGame game) {
		super("Settings", Styles.Const.settingsMenuBackground, new InputConfiguration(InputType.ARROWS));
		this.game = game;
		create();
	}

	private void create(){
		settings = new ArrayList<>();
		
		Table table = new Table();	
		try {
			HashMap<String, Setting> settings = new XMLReader().loadSettings(SETTINGS_CONFIG).settings;
			settings.forEach((k,v) -> {
				ActorSetting s = null;
				switch(v.settingType){
				case TOGGLE:
					s = new ActorToggle(v);
					break;
				case SLIDER:
					s = new ActorSlider(v);
					break;
				default:
					break;
				}
				if(s!=null){
					this.settings.add(s);
					s.addToTable(table);
				}
			});
		} catch (NoConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ScrollPane scrollPane = new ScrollPane(table, getScrollStyle());

		scrollPane.setWidth(Gdx.graphics.getWidth());
		scrollPane.setHeight(Gdx.graphics.getHeight()-200);
		scrollPane.setY(100);
		scrollPane.layout();
		//stage.addActor(new ActorBackground(ConfigManager.instance().getSettingValue(Styles.Const.settingsMenuBackground)));
		stage.addActor(scrollPane);

		//add cancel button
		TextButton cancelBtn = new TextButton("CANCEL", ButtonStyles.defaultStyle());
		this.placeWidget(cancelBtn, Position.BOT_LEFT);
		cancelBtn.addListener(new ChangeListener() {	
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				esc();
			}
		});

		//add save btn
		TextButton saveBtn = new TextButton("SAVE", ButtonStyles.defaultStyle());
		this.placeWidget(saveBtn, Position.BOT_RIGHT);
		saveBtn.addListener(new ChangeListener() {	
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				save();
			}
		});
	}

	@Override
	public void resize (int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void dispose () {

	}

	@Override
	public void show() {
		if(inputManager == null){
			List<InputListener> listeners = new ArrayList<>();
			listeners.add(this);
			inputManager = new InputManager(listeners, INPUT_MODE.SIMPLE);
			inputManager.addInputProcessor(stage);
		}
		Gdx.input.setInputProcessor(inputManager);
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

	@Override
	public void esc() {
		this.settings.forEach((v)->{
			v.resetValue();
		});
		this.game.gotoMainMenu();
	}

	private void save() {
//		ConfigManager.instance().saveSettings();
		this.settings.forEach(v->{
			ConfigManager.instance().saveSetting(v.getId(), v.getValue());	
			v.saveValue();
		});	
		esc();
	}
}
