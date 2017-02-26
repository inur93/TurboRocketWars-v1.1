package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.vormadal.turborocket.utils.Styles;

public class ActorSlider extends Container<Slider>{

	public ActorSlider() {
		super(new Slider(0,100,1,false, Styles.getSkin()));
		setTransform(true);
		setOrigin(getWidth()/2, getHeight()/2);
		setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
		
//		new Label(text, skin)
//		Slider slider=new Slider(0,100,1,false,Styles.getSkin());

		//
//		Container<Slider> container=new Container<Slider>(slider);
//		container.setTransform(true);   // for enabling scaling and rotation
//		//			    container.size(100, 60);
//		container.setOrigin(container.getWidth() / 2, container.getHeight() / 2);
//		container.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
//		container.setScale(3);  //scale according to your requirement
		
		

	}
	
	

}
