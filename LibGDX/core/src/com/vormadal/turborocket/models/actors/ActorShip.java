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
	private Texture tex;// = new Texture(Gdx.files.internal("ships/ship-elsa.png"));
//	private Texture verTex = new Texture(Gdx.files.internal("axis-vertical.png"));
//	private Texture horTex = new Texture(Gdx.files.internal("axis-horizontal.png"));
	
	private Sprite sprite;// = new Sprite(tex);
	private boolean initialized = false;
//	private Sprite ver = new Sprite(verTex);
//	private Sprite hor = new Sprite(horTex);
	
	public ActorShip(Ship<?, ?> ship){
		this.ship = ship;
	}

	public ActorShip(ShipConfig config) {
		String texturePath = ConfigManager.getInstance().getSettingValue(config.textureId);
		this.tex = new Texture(Gdx.files.internal(texturePath));
		this.sprite = new Sprite(tex);
		
		
//		Vector2 bounds = new Vector2(x, y)
//		sprite.setBounds(0, 0, bounds.x, bounds.y);
//		sprite.setOrigin(bounds.x/2, bounds.y/2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Body body = ship.getBody();

		if(!initialized){
			
			initialized = true;
		}
		if(ship != null){
		Vector2 pos = body.getPosition();

//		ship.getBody().getPosition()
		sprite.setRotation(ship.getBody().getAngle()*MathUtils.radiansToDegrees);
		
		batch.draw(sprite, pos.x-sprite.getWidth()/2, pos.y-sprite.getHeight()/2, 
				sprite.getOriginX(), sprite.getOriginY(), 
				sprite.getWidth(), sprite.getHeight(), 
				sprite.getScaleX(), sprite.getScaleY(), 
				sprite.getRotation());

		}else{
//			batch.draw(sprite, sprite.getX(), sprite.getY(), 
//					sprite.getOriginX(), sprite.getOriginY(), 
//					sprite.getWidth(), sprite.getHeight(), 
//					sprite.getScaleX(), sprite.getScaleY(), 
//					sprite.getRotation());
			batch.draw(sprite, this.getX(), this.getY(), 
					this.getOriginX(), this.getOriginY(), 
					this.getWidth(), this.getHeight(), 
					this.getScaleX(), this.getScaleY(), 
					this.getRotation());
		}
	}
}
