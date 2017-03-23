package com.vormadal.turborocket.configurations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class LabelStyles {

	private static LabelStyle defaultStyle;
	private static LabelStyle titleStyle;
	
	public static LabelStyle getDefaultStyle(){
		if(defaultStyle == null) initDefaultStyle();
		return defaultStyle;
	}
	
	public static LabelStyle getTitleStyle(){
		if(titleStyle == null) initTitleStyle();
		return titleStyle;
	}
	
	private static void initTitleStyle() {
		titleStyle = new LabelStyle();
		titleStyle.font = Styles.getTitleFont();
		titleStyle.fontColor = Color.WHITE;
	}

	private static void initDefaultStyle(){
		// create label style
		defaultStyle = new LabelStyle();
		defaultStyle.font = Styles.getDefaultFont();
		defaultStyle.fontColor = Color.WHITE;
	}
	
}
