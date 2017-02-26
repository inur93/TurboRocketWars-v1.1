package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.vormadal.turborocket.utils.Setting;
import com.vormadal.turborocket.utils.Setting.ValueType;
import com.vormadal.turborocket.utils.Styles;

public class ActorSlider extends Container<Slider>{

	public ActorSlider(Setting setting, TextField sliderValue, float yPos) {
		super(new Slider(Integer.valueOf(setting.validation.minValue), 
				Integer.valueOf(setting.validation.maxValue),
				(setting.valueType.equals(ValueType.FLOAT) ? 0.1f : 1)
				,false, Styles.getSkin()));
		setTransform(true);
		setOrigin(getWidth()/2, getHeight()/2);
		setPosition(Gdx.graphics.getWidth()/2, yPos);
		getActor().setValue(Float.valueOf(setting.value));
		this.getActor().addListener(new EventListener() {
			
			@Override
			public boolean handle(Event event) {
				sliderValue.setText("" + ActorSlider.this.getActor().getValue());
				return true;
			}
		});
	}
	
	

}
