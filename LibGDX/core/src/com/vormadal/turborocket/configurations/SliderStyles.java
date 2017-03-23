package com.vormadal.turborocket.configurations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.vormadal.turborocket.configurations.Styles.Const;

public class SliderStyles {

	private static SliderStyle sliderHorizontalStyle;

	public static SliderStyle getSliderHorizontalStyle(){
		if(sliderHorizontalStyle == null) initHorizontal();
		return sliderHorizontalStyle;
	}
	private static void initHorizontal(){
		Skin skin = Styles.getSkin();
		sliderHorizontalStyle = new SliderStyle();
		sliderHorizontalStyle.background = skin.newDrawable(Const.H_SLIDER_BACKGROUND, Color.WHITE);
		sliderHorizontalStyle.knob = skin.newDrawable(Const.SLIDER_KNOB, Color.LIGHT_GRAY);
	}
}
