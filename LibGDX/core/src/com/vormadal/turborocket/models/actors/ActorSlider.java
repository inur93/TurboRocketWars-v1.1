package com.vormadal.turborocket.models.actors;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.vormadal.turborocket.configurations.Setting;
import com.vormadal.turborocket.configurations.SliderStyles;
import com.vormadal.turborocket.configurations.Styles;
import com.vormadal.turborocket.configurations.Setting.ValueType;

public class ActorSlider extends Container<Slider>{

	private static DecimalFormat df = new DecimalFormat();
	
	public ActorSlider(Setting setting, TextField sliderValue, float yPos) {
		super(new Slider(Float.valueOf(setting.validation.minValue), 
				Integer.valueOf(setting.validation.maxValue),
				(setting.valueType.equals(ValueType.FLOAT) ? 0.1f : 1)
				,false, SliderStyles.getSliderHorizontalStyle()));
		
		df.setMaximumFractionDigits(2);
//		setTransform(true);
		
		setOrigin(getWidth()/2, getHeight()/2);
		setPosition(Gdx.graphics.getWidth()/2, yPos);
		getActor().setValue(Float.valueOf(setting.value));
		this.getActor().addListener(new EventListener() {
			
			@Override
			public boolean handle(Event event) {
				if(event.toString().equals("touchDragged")){
					float value = ActorSlider.this.getActor().getValue();
					sliderValue.setText(df.format(value));
				}
				return true;
			}
		});
	}
	

}
