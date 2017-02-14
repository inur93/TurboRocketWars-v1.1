package com.vormadal.turborocket.models.actors;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.ammo.Fragment;

import sun.misc.PostVMInitHook;

public class ActorShip extends Actor {

	private Ship<?,?> ship;
	private Texture tex = new Texture(Gdx.files.internal("ships/ship-blue.png"));
	private Sprite sprite = new Sprite(tex);
	public ActorShip(Ship<?, ?> ship){
		this.ship = ship;
//		sprite.scale(10f);
		Body body = ship.getBody();
		Array<Fixture> fixtures = body.getFixtureList();
		
		int shapeCount = 0;
		float radius = 0;
		float width = 0;
		float height = 0;
		float widthLow = Float.NaN, widthHigh = Float.NaN;
		float heightLow = Float.NaN, heightHigh = Float.NaN;
		for(Fixture f : fixtures){
			radius += f.getShape().getRadius();
			Type t = f.getType();
			switch (t) {
			case Chain:
				
				break;
			case Polygon:
				PolygonShape poly = (PolygonShape) f.getShape();
				
				boolean first = true;
				for(int i = 0; i < poly.getVertexCount(); i++){
					Vector2 point = new Vector2();
					poly.getVertex(i, point);
					System.out.println("point: " + point.x +"," + point.y);
					if(first){
						widthHigh = point.x;
						widthLow = point.x;
						heightLow = point.y;
						heightHigh = point.y;
						first = false;
					}
					if(widthHigh < point.x) widthHigh = point.x;
					if(widthLow > point.x) widthLow = point.x;
					if(heightHigh < point.y) heightHigh = point.y;
					if(heightLow > point.y) heightLow = point.y;
				}				
				break;
			case Circle:
				
				break;
			case Edge:
				
				break;
			default:
				break;
			}
			
			shapeCount++;
		}
		width = widthHigh-widthLow;
		height = heightHigh-heightLow;
		System.out.println("height: " + heightHigh + "-" + heightLow);
		System.out.println("width: " + widthHigh + "-" + widthLow);
		System.out.println("radius: " + radius + " count: " + shapeCount);
		System.out.println("width,height: " + width +"," + height);
		
		sprite.setBounds(0, 0, width, height);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Body body = ship.getBody();
		Vector2 pos = body.getPosition();
		float angle = (float) (body.getAngle()*(360/(Math.PI*2)));
		
		
		sprite.setRotation(angle);
		batch.draw(sprite, pos.x, pos.y, 
				sprite.getOriginX(), sprite.getRegionY(), 
				sprite.getWidth(), sprite.getHeight(), 
				sprite.getScaleX(), sprite.getScaleY(), 
				sprite.getRotation());
		
		
	}
}
