package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.models.ammo.SeekerMissile;
import com.vormadal.turborocket.utils.ActorUtil;

public class ActorSeekerMissile extends Actor{
	private SeekerMissile seeker;
	private Texture tex = new Texture(Gdx.files.internal("ammo/fragment.png"));
	private Sprite sprite = new Sprite(tex);
	public ActorSeekerMissile(SeekerMissile seeker){
		this.seeker = seeker;
		Vector2 bounds = ActorUtil.getSize(seeker.getBody());
		sprite.setBounds(0, 0, bounds.x, bounds.y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Vector2 pos = seeker.getBody().getPosition();
		
		sprite.setRotation(seeker.getBody().getAngle()*MathUtils.radiansToDegrees);
		batch.draw(sprite, pos.x, pos.y, 
				sprite.getOriginX(), sprite.getRegionY(), 
				sprite.getWidth(), sprite.getHeight(), 
				sprite.getScaleX(), sprite.getScaleY(), 
				sprite.getRotation());
	}
}
