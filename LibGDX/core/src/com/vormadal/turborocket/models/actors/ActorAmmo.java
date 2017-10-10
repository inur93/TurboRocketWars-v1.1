package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.models.ammo.Ammo;
import com.vormadal.turborocket.utils.ActorUtil;

public class ActorAmmo extends Actor {
	
	private Ammo ammo;
	private Sprite sprite;
	public ActorAmmo(Ammo ammo, String texture){
		this.ammo = ammo;
		Texture tex = new Texture(Gdx.files.internal(texture));
		this.sprite = new Sprite(tex);
		Vector2 bounds = ActorUtil.getSize(ammo.getBody());
		sprite.setBounds(0, 0, bounds.x, bounds.y);
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Vector2 pos = ammo.getBody().getPosition();
		
		sprite.setRotation(ammo.getBody().getAngle()*MathUtils.radiansToDegrees);
		batch.draw(sprite, pos.x, pos.y, 
				sprite.getOriginX(), sprite.getRegionY(), 
				sprite.getWidth(), sprite.getHeight(), 
				sprite.getScaleX(), sprite.getScaleY(), 
				sprite.getRotation());
	}

}
