package com.vormadal.turborocket.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Styles {

	private static Skin skin;
	private static TextButtonStyle buttonStyle;
	private static void init(){
		skin = new Skin();
		
		skin.add(Const.BTN_UNSELECTED, new Texture(Gdx.files.internal("menu/buttons/olaf-1.jpg")));
		skin.add(Const.BTN_SELECTED, new Texture(Gdx.files.internal("menu/buttons/olaf-2.jpg")));
		skin.add(Const.SLIDER_BACKGROUND, new Texture(Gdx.files.internal("menu/buttons/slider.png")));
		skin.add(Const.SLIDER_KNOB, new Texture(Gdx.files.internal("menu/buttons/knob.png")));
		
		// Store the default libgdx font under the name "default".
		BitmapFont bfont=new BitmapFont();
		
		skin.add(Const.FONT_DEFAULT,bfont);
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.newDrawable(Const.BTN_UNSELECTED, Color.LIGHT_GRAY);
		buttonStyle.down = skin.newDrawable(Const.BTN_UNSELECTED, Color.LIGHT_GRAY);
		buttonStyle.checked = skin.newDrawable(Const.BTN_SELECTED, Color.BLUE);
		buttonStyle.over = skin.newDrawable(Const.BTN_SELECTED, Color.WHITE);
		buttonStyle.font = skin.getFont(Const.FONT_DEFAULT);
		
		skin.add(Const.BTN_STYLE_DEFAULT, buttonStyle);
		
		SliderStyle sliderStyle = new SliderStyle();
		sliderStyle.background = skin.newDrawable(Const.SLIDER_BACKGROUND, Color.WHITE);
		sliderStyle.knob = skin.newDrawable(Const.SLIDER_KNOB, Color.LIGHT_GRAY);
		
		skin.add(Const.SLIDER_STYLE_DEFAULT, sliderStyle);
	}

	public static Skin getSkin(){
		if(skin == null) init();
		return skin;
	}
	public static TextButtonStyle getButtonStyle(){
		if(skin == null) init();
		return buttonStyle;
	}

	public class Const{
		public static final String BTN_STYLE_DEFAULT = "default";
		public static final String SLIDER_STYLE_DEFAULT = "default-horizontal";
		public static final String BTN_UNSELECTED = "unselected";
		public static final String BTN_SELECTED = "selected";
		public static final String FONT_DEFAULT = "font-default";
		public static final String SLIDER_BACKGROUND = "slider-background";
		public static final String SLIDER_KNOB = "slider-knob";
	}
}
