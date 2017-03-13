package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.models.Map;
import com.vormadal.turborocket.models.ammo.Bullet;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.utils.ActorUtil;

public class ActorMap extends Actor{
	private Texture tex;// = new Texture(Gdx.files.internal("maps/frozen-olaf.jpg"));//"maps/base-map1.png"));
	private Sprite sprite;// = new Sprite(tex);
	public ActorMap(Map map){
		this(map.getConfig());
	}
	
	public ActorMap(MapConfig config){
		tex = new Texture(Gdx.files.internal(config.backgroundPath));
		sprite = new Sprite(tex);		
//		sprite.setBounds(0, 0, config.width, config.height);
		setBounds(0, 0, config.width, config.height);
		System.out.println(getHeight() + ";" + getWidth() + ";" + getOriginX() + ";" + getOriginY() + ";" + getScaleX() + ";" + getScaleY());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.draw(sprite, getX(), getY(), 
				getOriginX(), getOriginY(), 
				getWidth(), getHeight(), 
				getScaleX(), getScaleY(), 
				sprite.getRotation());
	}
}
