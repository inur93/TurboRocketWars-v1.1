package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.models.ammo.Bullet;
import com.vormadal.turborocket.utils.ActorUtil;

public class ActorBullet extends Actor {
	private Bullet bullet;
	private Texture tex = new Texture(Gdx.files.internal("ammo/fragment.png"));
	private Sprite sprite = new Sprite(tex);
	public ActorBullet(Bullet bullet){
		this.bullet = bullet;
		Vector2 bounds = ActorUtil.getSize(this.bullet.getBody());
		sprite.setBounds(0, 0, bounds.x, bounds.y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Vector2 pos = bullet.getBody().getPosition();
		
		sprite.setRotation(bullet.getBody().getAngle()*MathUtils.radiansToDegrees);
		batch.draw(sprite, pos.x, pos.y, 
				sprite.getOriginX(), sprite.getRegionY(), 
				sprite.getWidth(), sprite.getHeight(), 
				sprite.getScaleX(), sprite.getScaleY(), 
				sprite.getRotation());
	}
}
