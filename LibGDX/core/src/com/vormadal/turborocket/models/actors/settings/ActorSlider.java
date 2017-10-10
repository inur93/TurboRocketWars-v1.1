package com.vormadal.turborocket.models.actors.settings;

import java.text.DecimalFormat;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.vormadal.turborocket.configurations.LabelStyles;
import com.vormadal.turborocket.configurations.Setting;
import com.vormadal.turborocket.configurations.Setting.ValueType;
import com.vormadal.turborocket.configurations.SliderStyles;
import com.vormadal.turborocket.configurations.Styles;

public class ActorSlider extends ActorSetting{

	private static DecimalFormat df = new DecimalFormat();
	private Container<Slider> slider;
	private TextField valueField;
	private Label sliderLabel;

	private String defaultValue;

	public ActorSlider(Setting setting) {
		super(setting);	
		this.defaultValue = setting.value;
		df.setMaximumFractionDigits(2);
		
		valueField = new TextField(setting.value, Styles.getTextFieldStyle());
		valueField.setDisabled(false);
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

		slider = new Container<>(new Slider(Float.valueOf(setting.validation.minValue), 
				Integer.valueOf(setting.validation.maxValue),
				(setting.valueType.equals(ValueType.FLOAT) ? 0.1f : 1)
				,false, SliderStyles.getSliderHorizontalStyle()));		
		slider.getActor().setValue(Float.valueOf(setting.value));
		slider.getActor().addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				if(event.toString().equals("touchDragged")){
					float value = slider.getActor().getValue();
					valueField.setText(df.format(value));
				}
				return true;
			}

		});

		sliderLabel = new Label(setting.name, LabelStyles.getDefaultStyle());
	}


	@Override
	public void addToTable(Table table) {
		table.add(slider).width(140).height(25).padBottom(PADDING).padTop(PADDING);
		table.add(sliderLabel).padLeft(5).padRight(5);
		table.add(valueField);
		table.row();
	}

	@Override
	public void setValue(String value) {
		slider.getActor().setValue(Float.valueOf(value));
		valueField.setText(value);
	}

	@Override
	public String getValue() {
		return valueField.getText();
	}


	@Override
	public void resetValue() {
		slider.getActor().setValue(Float.valueOf(defaultValue));
		valueField.setText(defaultValue);
	}


	@Override
	public void saveValue() {
		defaultValue = valueField.getText();
	}
}
