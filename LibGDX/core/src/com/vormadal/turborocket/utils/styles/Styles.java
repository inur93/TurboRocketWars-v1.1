package com.vormadal.turborocket.utils.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public class Styles {

	private static Skin skin;
	private static BitmapFont defaultFont;
	private static BitmapFont titleFont;
	private static BitmapFont buttonFont;
	
	private static TextFieldStyle textFieldStyle;
	private static ScrollPaneStyle scrollStyle;
	
	private static void init(){
		skin = new Skin();

		//add skins for buttons
		skin.add(Const.BTN_DEFAULT, new Texture(Gdx.files.internal("menu/buttons/olaf-1.jpg")));
		skin.add(Const.BTN_HOVER, new Texture(Gdx.files.internal("menu/buttons/olaf-2.jpg")));
		skin.add(Const.BTN_LEFT_ARROW, new Texture(Gdx.files.internal("menu/buttons/arrow-left.png")));
		skin.add(Const.BTN_RIGHT_ARROW, new Texture(Gdx.files.internal("menu/buttons/arrow-right.png")));
		skin.add(Const.BTN_CHECKED, new Texture(Gdx.files.internal("menu/buttons/olaf-happy.png")));
		skin.add(Const.BTN_UNCHECKED, new Texture(Gdx.files.internal("menu/buttons/olaf-sad.png")));
		
		//add skins for slider
		skin.add(Const.SLIDER_BACKGROUND, new Texture(Gdx.files.internal("menu/buttons/slider.png")));
		skin.add(Const.V_SLIDER_BACKGROUND, new Texture(Gdx.files.internal("menu/buttons/v-slider.png")));
		skin.add(Const.SLIDER_KNOB, new Texture(Gdx.files.internal("menu/buttons/knob.png")));
		
		//text field
		skin.add(Const.TEXT_FIELD_CURSOR, new Texture(Gdx.files.internal("menu/buttons/cursor.png")));
		
		//add font
		defaultFont=new BitmapFont(new FileHandle("fonts/default-font.fnt"));
		titleFont = new BitmapFont(new FileHandle("fonts/title-font.fnt"));
		
		skin.add(Const.FONT_DEFAULT,defaultFont);


		//create textfield style
		textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = defaultFont;
		textFieldStyle.fontColor = Color.YELLOW;
		textFieldStyle.focusedFontColor = Color.RED;
		textFieldStyle.cursor = skin.newDrawable(Const.TEXT_FIELD_CURSOR, Color.BLACK);
		

		scrollStyle = new ScrollPaneStyle();
		scrollStyle.background = skin.newDrawable(Const.V_SLIDER_BACKGROUND, Color.WHITE);
		scrollStyle.vScrollKnob = skin.newDrawable(Const.SLIDER_KNOB, Color.WHITE);
		scrollStyle.vScroll = skin.newDrawable(Const.V_SLIDER_BACKGROUND, Color.WHITE);
		
	}

	protected static BitmapFont getDefaultFont() {
		return defaultFont;
	}
	
	protected static BitmapFont getTitleFont() {
		return titleFont;
	}
	
	protected static BitmapFont getButtonFont() {
		return buttonFont;
	}
	protected static Skin getSkin(){
		if(skin == null) init();
		return skin;
	}

	public static TextFieldStyle getTextFieldStyle(){
		if(skin == null) init();
		return textFieldStyle;
	}


	public static ScrollPaneStyle getScrollStyle(){
		if(skin == null) init();
		return scrollStyle;
	}
	public class Const{
		
		//button
		public static final String BTN_STYLE_DEFAULT = "default";
		public static final String BTN_LEFT_ARROW = "left-arrow";
		public static final String BTN_RIGHT_ARROW = "right-arrow";
		public static final String BTN_DEFAULT = "unselected";
		public static final String BTN_HOVER = "selected";
		public static final String BTN_CHECKED = "checked";
		public static final String BTN_UNCHECKED = "unchecked";
		public static final String BTN_TOGGLE_STYLE_DEFAULT = "toggle-default";

		//slider
		public static final String SLIDER_STYLE_DEFAULT = "default-horizontal";
		public static final String V_SLIDER_BACKGROUND = "v-slider";
		public static final String SLIDER_BACKGROUND = "slider-background";
		public static final String SLIDER_KNOB = "slider-knob";
		
		//font
		public static final String FONT_DEFAULT = "font-default";
		public static final String TEXT_FIELD_CURSOR = "text-field-cursor";
	}
}
