package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.vormadal.turborocket.utils.Setting;
import com.vormadal.turborocket.utils.styles.ButtonStyles;
import com.vormadal.turborocket.utils.styles.LabelStyles;
import com.vormadal.turborocket.utils.styles.Styles;

public class ActorSetting {

	public ActorSetting(Stage stage, Setting setting, float yPos) {
		switch(setting.settingType){
		case SLIDER:
			createSlider(stage, setting, yPos);
			break;
		case TEXT_FIELD:
			createTextField(stage, setting, yPos);
			break;
		case TOGGLE:
			createToggleButton(stage, setting, yPos);
			break;
		default:
			break;
		
		}
	}
	
	private void createToggleButton(Stage stage, Setting setting, float yPos) {
		final TextButton button = new TextButton(setting.name, ButtonStyles.toggleButtonStyle());
		Label sliderLabel = new Label(setting.name, LabelStyles.getDefaultStyle());
		boolean checked = Boolean.valueOf(setting.value);
		button.setChecked(checked);
		button.setSize(140, 25);
		button.setPosition(Gdx.graphics.getWidth()/2-button.getWidth()/2, yPos-button.getHeight()/2);
		
		button.setOrigin(button.getWidth()/2, button.getHeight()/2);
//		button.setPosition(Gdx.graphics.getWidth()/2, yPos);
		
		button.addListener(new EventListener() {
			
			@Override
			public boolean handle(Event event) {
				setting.value = String.valueOf(button.isChecked()); // just set it everytime.
				return true;
			}
		});
		
		sliderLabel.setPosition(Gdx.graphics.getWidth()/2 - (100+sliderLabel.getWidth()), yPos - sliderLabel.getHeight()/2);
		
		stage.addActor(button);
		stage.addActor(sliderLabel);
	}

	private void createTextField(Stage stage, Setting setting, float yPos) {
		
	}

	private void createSlider(Stage stage, Setting setting, float yPos){
		TextField valueField = new TextField(setting.value, Styles.getTextFieldStyle());
		valueField.setDisabled(false);
		Label sliderLabel = new Label(setting.name, LabelStyles.getDefaultStyle());
		ActorSlider slider = new ActorSlider(setting, valueField, yPos);

		valueField.addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				event.handle();
				if(event.toString().equals("touchDown")){
					valueField.selectAll();
				}else if(event.toString().equals("keyTyped")){
					String val = valueField.getText();
					if(setting.isValid(val) && val != null && val.length() > 0){
						slider.getActor().setValue(Float.valueOf(val));
					}
				}
				return true;
			}
		});
		sliderLabel.setPosition(Gdx.graphics.getWidth()/2 - (100+sliderLabel.getWidth()), yPos - sliderLabel.getHeight()/2);
		valueField.setPosition(Gdx.graphics.getWidth()/2 + 100, yPos - valueField.getHeight()/2);
		stage.addActor(slider);
		stage.addActor(sliderLabel);
		stage.addActor(valueField);
	}
}
