package com.vormadal.turborocket.models.actors.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.vormadal.turborocket.configurations.ButtonStyles;
import com.vormadal.turborocket.configurations.Setting;

public class ActorToggle extends ActorSetting {

	private TextButton button;
	private String defaultValue;
	
	public ActorToggle(Setting setting) {
		super(setting);
		defaultValue = setting.value;
		button = new TextButton(setting.name, ButtonStyles.toggleButtonStyle());
		button.scaleBy(0.1f,0.1f);

		boolean checked = Boolean.valueOf(setting.value);
		button.setChecked(checked);
		//button.setSize(140, 25);
		button.setX(Gdx.graphics.getWidth()/2-button.getWidth()/2);	
		button.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				
				return true;
			}
		});
	}

	@Override
	public void addToTable(Table table) {
		table.add(button).width(140).height(25).padBottom(PADDING).padTop(PADDING);
		table.row();
	}

	@Override
	public void setValue(String value) {
		try{
			boolean val = Boolean.valueOf(value);
			button.setChecked(val);
		}catch(Exception e){
			
		}
	}

	@Override
	public String getValue() {
		return String.valueOf(button.isChecked());
	}

	@Override
	public void resetValue() {
		button.setChecked(Boolean.valueOf(defaultValue));
	}

	@Override
	public void saveValue() {
		defaultValue = String.valueOf(button.isChecked());
	}

}
