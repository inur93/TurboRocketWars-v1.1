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
import com.vormadal.turborocket.utils.ActorUtil;

public class ActorMap extends Actor{
	private Map map;
	private Texture tex = new Texture(Gdx.files.internal("maps/frozen.jpg"));//"maps/base-map1.png"));
	private Sprite sprite = new Sprite(tex);
	public ActorMap(Map map){
		this.map = map;
		sprite.setBounds(0, 0, map.getWidth(), map.getHeight());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.draw(sprite, 0, 0, 
				sprite.getOriginX(), sprite.getRegionY(), 
				sprite.getWidth(), sprite.getHeight(), 
				sprite.getScaleX(), sprite.getScaleY(), 
				sprite.getRotation());
	}
}
