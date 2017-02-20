package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.utils.ActorUtil;
public class ActorShip extends Actor {

	private Ship<?,?> ship;
	private Texture tex = new Texture(Gdx.files.internal("ships/ship-blue.png"));
//	private Texture verTex = new Texture(Gdx.files.internal("axis-vertical.png"));
//	private Texture horTex = new Texture(Gdx.files.internal("axis-horizontal.png"));
	
	private Sprite sprite = new Sprite(tex);
	private boolean initialized = false;
//	private Sprite ver = new Sprite(verTex);
//	private Sprite hor = new Sprite(horTex);
//	private ArrayList<Vector2> shipPoints = new ArrayList<>();
	//test
//	private BitmapFont font = new BitmapFont();
	public ActorShip(Ship<?, ?> ship){
		this.ship = ship;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Body body = ship.getBody();

		if(!initialized){
			Vector2 bounds = ActorUtil.getSize(body);
			sprite.setBounds(0, 0, bounds.x, bounds.y);
			initialized = true;
		}
		Vector2 pos = body.getPosition();


		sprite.setRotation(ship.getBody().getAngle()*MathUtils.radiansToDegrees);
		
		batch.draw(sprite, pos.x-sprite.getWidth()/2, pos.y-sprite.getHeight()/2, 
				sprite.getOriginX(), sprite.getOriginY(), 
				sprite.getWidth(), sprite.getHeight(), 
				sprite.getScaleX(), sprite.getScaleY(), 
				sprite.getRotation());


	}
}
