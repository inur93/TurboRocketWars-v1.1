package com.vormadal.turborocket.screens;

import static com.vormadal.turborocket.configurations.Styles.getScrollStyle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.configurations.LabelStyles;
import com.vormadal.turborocket.configurations.Setting;
import com.vormadal.turborocket.configurations.Styles;
import com.vormadal.turborocket.configurations.XMLReader;
import com.vormadal.turborocket.controllers.InputManager;
import com.vormadal.turborocket.controllers.TurboRocketWarsGame;
import com.vormadal.turborocket.controllers.InputManager.INPUT_MODE;
import com.vormadal.turborocket.models.actors.ActorBackground;
import com.vormadal.turborocket.models.actors.ActorSettingList;
import com.vormadal.turborocket.models.actors.ActorSetting;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputListenerAdapter;
import com.vormadal.turborocket.utils.InputConfiguration.InputType;
import com.vormadal.turborocket.utils.InputListener;


public class SettingsScreen extends InputListenerAdapter implements Screen{

	private static final String SETTINGS_CONFIG = "default-settings.config";
	private ActorSettingList scrollView;
	private Stage stage;
	private TurboRocketWarsGame game;
	private InputManager inputManager;
	public SettingsScreen(TurboRocketWarsGame game) {
		super(new InputConfiguration(InputType.ARROWS));
		this.game = game;
		create();
	}
	
	private void create(){
		stage = new Stage();
				
//		List<Setting> settings = new XMLReader().loadSettings(SETTINGS_CONFIG);
		
		
		
//		scrollView = new ActorSettingList(settings, stage);
		Table table = new Table();
		table.add(new Label("test", LabelStyles.getDefaultStyle()));
		table.row();
		table.add(new Label("test1", LabelStyles.getDefaultStyle()));
		table.row();
		table.add(new Label("test2", LabelStyles.getDefaultStyle()));
		table.row();
		table.add(new Label("test3", LabelStyles.getDefaultStyle()));
		table.row();
		table.add(new Label("test4", LabelStyles.getDefaultStyle()));
		table.row();
		table.add(new Label("test5", LabelStyles.getDefaultStyle()));
		table.row();
		ScrollPane scrollPane = new ScrollPane(table, getScrollStyle());
//		scrollPane.setSize(200, 200);
		Table container = new Table();
		container.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
		
		
//		table.layout();
		container.add(scrollPane).width(500).height(100).expand();
		container.row();
		
		scrollPane.layout();
//		scrollPane.setFadeScrollBars(false);
//		scrollPane.setScrollingDisabled(false, true);
//		scrollPane.setScrollPercentY(100);
//		scrollPane.setScrollBarPositions(false, true);
//		scrollPane.updateVisualScroll();
//		container.layout();
//		Gdx.input.setInputProcessor(stage);
		//		
//		scrollPane.setSize(500, 1000);
//		scrollPane.layout();
//		Table table = new Table();
//		table.add(scrollPane).width(500).height(500);
//		table.row();
		
		
//		stage.addActor(table);
//		stage.addActor(scrollPane);
		stage.addActor(new ActorBackground(ConfigManager.getInstance().getSettingValue(Styles.Const.settingsMenuBackground)));
		stage.addActor(container);
//		int yPos = 100;
//		for(Setting s : settings){
//			if(s.editable){
//				new ActorSetting(stage, s, yPos);
//				yPos += 25;
//			}
//		}
		
//		
		
		
//		stage.addActor(scrollView);
		
	}
	
	@Override
	public void resize (int width, int height) {
		//		stage.setViewport(width, height, false);
		stage.getViewport().setScreenSize(width, height);
//		scrollView.getStage().
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
		this.game.gotoMainMenu();
	}
	
}
