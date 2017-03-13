package com.vormadal.turborocket.utils.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.vormadal.turborocket.utils.styles.Styles.Const;

public class ButtonStyles {

	private static TextButtonStyle buttonStyle;
	private static TextButtonStyle leftArrowStyle;
	private static TextButtonStyle rightArrowStyle;
	private static TextButtonStyle toggleButtonStyle;

	public static TextButtonStyle defaultStyle(){
		if(buttonStyle == null) initDefault();
		return buttonStyle;
	}
	
	public static TextButtonStyle leftArrowStyle(){
		if(leftArrowStyle == null) initLeftArrowStyle();
		return leftArrowStyle;
	}
	
	public static TextButtonStyle rightArrowStyle(){
		if(rightArrowStyle == null) initRightArrowStyle();
		return rightArrowStyle;
	}
	
	public static TextButtonStyle toggleButtonStyle(){
		if(toggleButtonStyle == null) initToggleStyle();
		return toggleButtonStyle;
	}
	

	private static void initDefault() {
		Skin skin = Styles.getSkin();
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.newDrawable(Const.BTN_DEFAULT, Color.LIGHT_GRAY);
		buttonStyle.down = skin.newDrawable(Const.BTN_DEFAULT, Color.LIGHT_GRAY);
		buttonStyle.checked = skin.newDrawable(Const.BTN_HOVER, Color.BLUE);
		buttonStyle.over = skin.newDrawable(Const.BTN_HOVER, Color.WHITE);
		buttonStyle.font = skin.getFont(Const.FONT_DEFAULT);
		buttonStyle.checkedOffsetX = 1;
	}

	private static void initLeftArrowStyle(){
		Skin skin = Styles.getSkin();
		leftArrowStyle = new TextButtonStyle();
		leftArrowStyle.up = skin.newDrawable(Const.BTN_LEFT_ARROW, Color.WHITE);
		leftArrowStyle.down = skin.newDrawable(Const.BTN_LEFT_ARROW, Color.LIGHT_GRAY);	
		//				leftArrowStyle.checked = skin.newDrawable(Const.BTN_TYPE2, Color.BLUE);
		leftArrowStyle.over = skin.newDrawable(Const.BTN_LEFT_ARROW, Color.SKY);
		leftArrowStyle.disabled = skin.newDrawable(Const.BTN_LEFT_ARROW, Color.DARK_GRAY);
		leftArrowStyle.font = skin.getFont(Const.FONT_DEFAULT);
		leftArrowStyle.checkedOffsetX = 1;
	}
	
	private static void initRightArrowStyle(){
		Skin skin = Styles.getSkin();
		rightArrowStyle = new TextButtonStyle();
		rightArrowStyle.up = skin.newDrawable(Const.BTN_RIGHT_ARROW, Color.WHITE);
		rightArrowStyle.down = skin.newDrawable(Const.BTN_RIGHT_ARROW, Color.LIGHT_GRAY);
//		rightArrowStyle.checked = skin.newDrawable(Const.BTN_TYPE2, Color.BLUE);
		rightArrowStyle.over = skin.newDrawable(Const.BTN_RIGHT_ARROW, Color.SKY);
		rightArrowStyle.disabled = skin.newDrawable(Const.BTN_RIGHT_ARROW, Color.DARK_GRAY);
		rightArrowStyle.font = skin.getFont(Const.FONT_DEFAULT);
		rightArrowStyle.checkedOffsetX = 1;
	}
	
	private static void initToggleStyle(){
		Skin skin = Styles.getSkin();
		toggleButtonStyle = new TextButtonStyle();
		toggleButtonStyle.up = skin.newDrawable(Const.BTN_UNCHECKED, Color.LIGHT_GRAY);
//		toggleButtonStyle.down = skin.newDrawable(Const.BTN_TYPE1, Color.LIGHT_GRAY);
		toggleButtonStyle.checked = skin.newDrawable(Const.BTN_CHECKED, Color.WHITE);
//		buttonStyle.over = skin.newDrawable(Const.BTN_TYPE2, Color.WHITE);
		toggleButtonStyle.font = skin.getFont(Const.FONT_DEFAULT);
		toggleButtonStyle.checkedOffsetX = 1;
	}
}
