package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.configs.ShipConfig;
import com.vormadal.turborocket.utils.ActorUtil;
public class ActorShip extends Actor {

	private Ship<?,?> ship;
	private Texture tex;
	private Sprite sprite;
	private boolean initialized = false;

	public ActorShip(Ship<?,?> ship, ShipConfig config) {
		this.ship = ship;
		String texturePath = ConfigManager.instance().getSettingValue(config.textureId);
		this.tex = new Texture(Gdx.files.internal(texturePath));
		this.sprite = new Sprite(tex);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {


		if(!initialized){

			initialized = true;
		}
		if(ship != null){
			Body body = ship.getBody();
			Vector2 pos = body.getPosition();

			sprite.setRotation(ship.getBody().getAngle()*MathUtils.radiansToDegrees);

			batch.draw(sprite, pos.x-sprite.getWidth()/2, pos.y-sprite.getHeight()/2, 
					sprite.getOriginX(), sprite.getOriginY(), 
					sprite.getWidth(), sprite.getHeight(), 
					sprite.getScaleX(), sprite.getScaleY(), 
					sprite.getRotation());

		}else{
			//'Demo' Version when players select ship, the body is not required
			batch.draw(sprite, this.getX(), this.getY(), 
					this.getOriginX(), this.getOriginY(), 
					this.getWidth(), this.getHeight(), 
					this.getScaleX(), this.getScaleY(), 
					this.getRotation());
		}
	}
}
