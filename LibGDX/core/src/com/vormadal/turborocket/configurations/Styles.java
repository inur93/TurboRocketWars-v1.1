package com.vormadal.turborocket.configurations;

import java.util.HashMap;

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
	private static HashMap<String, Setting> settings;
	protected static void init(SkinConfig config){
		skin = new Skin();
		settings = config.settings.settings;
		//add skins for buttons 
		
		skin.add(Const.BTN_DEFAULT, new Texture(new FileHandle(getPath(Const.BTN_DEFAULT))));
		skin.add(Const.BTN_HOVER, new Texture(new FileHandle(getPath(Const.BTN_HOVER))));
		skin.add(Const.BTN_LEFT_ARROW, new Texture(new FileHandle(getPath(Const.BTN_LEFT_ARROW))));
		skin.add(Const.BTN_RIGHT_ARROW, new Texture(new FileHandle(getPath(Const.BTN_RIGHT_ARROW))));
		skin.add(Const.BTN_CHECKED, new Texture(new FileHandle(getPath(Const.BTN_CHECKED))));
		skin.add(Const.BTN_UNCHECKED, new Texture(new FileHandle(getPath(Const.BTN_UNCHECKED))));
		
		//add skins for slider
		skin.add(Const.H_SLIDER_BACKGROUND, new Texture(new FileHandle(getPath(Const.H_SLIDER_BACKGROUND))));
		skin.add(Const.V_SLIDER_BACKGROUND, new Texture(new FileHandle(getPath(Const.V_SLIDER_BACKGROUND))));
		skin.add(Const.SLIDER_KNOB, new Texture(new FileHandle(getPath(Const.SLIDER_KNOB))));
		
		//text field
		skin.add(Const.TEXT_FIELD_CURSOR, new Texture(new FileHandle(getPath(Const.TEXT_FIELD_CURSOR))));
		
		//add font
		ConfigManager mgr = ConfigManager.instance();
		String defaultFontPath = mgr.getDefaultFontPath();
		String customDefaultFont = null;
		if((customDefaultFont = getPath(Const.FONT_DEFAULT)) != null) defaultFontPath = customDefaultFont;
		
		String titleFontPath = mgr.getTitleFontPath();
		String customTitleFontPath = null;
		if((customTitleFontPath = getPath(Const.FONT_TITLE)) != null) titleFontPath = customTitleFontPath;
		defaultFont = new BitmapFont(new FileHandle("fonts/default-font.fnt"));
		titleFont = new BitmapFont(new FileHandle("fonts/title-font.fnt"));
		
		skin.add(Const.FONT_DEFAULT,defaultFont);


		//create textfield style
		textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = defaultFont;
		textFieldStyle.fontColor = Color.YELLOW;
		textFieldStyle.focusedFontColor = Color.RED;
		textFieldStyle.cursor = skin.newDrawable(Const.TEXT_FIELD_CURSOR, Color.BLACK);
		

		
		scrollStyle = new ScrollPaneStyle();
		//scrollStyle.background = skin.newDrawable(Const.V_SLIDER_BACKGROUND, Color.WHITE);
		scrollStyle.vScroll = skin.newDrawable(Const.V_SLIDER_BACKGROUND, Color.WHITE);
		scrollStyle.vScrollKnob = skin.newDrawable(Const.SLIDER_KNOB, Color.WHITE);
		scrollStyle.vScroll = skin.newDrawable(Const.V_SLIDER_BACKGROUND, Color.WHITE);
		
	}
	
	private static String getPath(String id){
		try{
		return settings.get(id).value;
		}catch(NullPointerException e){
			System.out.println("no setting found for: " + id);
			return null;
		}
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
		return skin;
	}

	public static TextFieldStyle getTextFieldStyle(){
		return textFieldStyle;
	}


	public static ScrollPaneStyle getScrollStyle(){
		return scrollStyle;
	}
	public class Const{
		
		//button
//		public static final String BTN_STYLE_DEFAULT = "default";
		public static final String BTN_LEFT_ARROW = "left-arrow";
		public static final String BTN_RIGHT_ARROW = "right-arrow";
		public static final String BTN_DEFAULT = "btn-default";
		public static final String BTN_HOVER = "btn-hover";
		public static final String BTN_CHECKED = "toggle-checked";
		public static final String BTN_UNCHECKED = "toggle-unchecked";
		public static final String BTN_TOGGLE_STYLE_DEFAULT = "toggle-default";

		//slider
//		public static final String SLIDER_STYLE_DEFAULT = "default-horizontal";
		public static final String V_SLIDER_BACKGROUND = "v-slider-background";
		public static final String H_SLIDER_BACKGROUND = "h-slider-background";
		public static final String SLIDER_KNOB = "slider-knob";
		
		//font
		public static final String FONT_DEFAULT = "default-font";
		public static final String FONT_TITLE= "title-font";
		public static final String TEXT_FIELD_CURSOR = "text-field-cursor";
		
		//backgrounds
		public static final String mapSelectMenuBackground = "map-select-menu-background";
		public static final String settingsMenuBackground = "settings-menu-background";
		public static final String mainMenuBackground = "main-menu-background";
		public static final String playerOptionsMenuBackground = "player-options-menu-background";
		
	}
}
