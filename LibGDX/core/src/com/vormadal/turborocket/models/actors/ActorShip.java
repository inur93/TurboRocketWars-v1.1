package com.vormadal.turborocket.models.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
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
import com.vormadal.turborocket.utils.ActorUtil;

import sun.misc.PostVMInitHook;
import static java.lang.Math.round;
public class ActorShip extends Actor {

	private Ship<?,?> ship;
	private Texture tex = new Texture(Gdx.files.internal("ships/ship-blue.png"));
	private Sprite sprite = new Sprite(tex);
	private ArrayList<Vector2> shipPoints = new ArrayList<>();
	//test
	private BitmapFont font = new BitmapFont();
	public ActorShip(Ship<?, ?> ship){
		this.ship = ship;
		Body body = ship.getBody();
		
//		System.out.println("height: " + heightHigh + "-" + heightLow);
//		System.out.println("width: " + widthHigh + "-" + widthLow);
//		System.out.println("radius: " + radius + " count: " + shapeCount);
//		System.out.println("width,height: " + width +"," + height);
		Vector2 bounds = ActorUtil.getSize(body);
		sprite.setBounds(0, 0, bounds.x, bounds.y);
//		getStage().getCamera().pr

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Body body = ship.getBody();

		Vector2 pos = body.getPosition();


		sprite.setRotation(ship.getBody().getAngle()*MathUtils.radiansToDegrees);

		batch.draw(sprite, pos.x-sprite.getWidth()/2, pos.y-sprite.getHeight()/2, 
				sprite.getOriginX(), sprite.getRegionY(), 
				sprite.getWidth(), sprite.getHeight(), 
				sprite.getScaleX(), sprite.getScaleY(), 
				sprite.getRotation());
		
		int tpos = 10;
//		int p0x = round(pos.x), p0y = round(pos.y);
//		int p1x = round(pos.x+sprite.getWidth()/2), p1y = round(pos.y+sprite.getHeight());
//		int p2x = round(pos.x+sprite.getWidth()), p2y = round(pos.y);
//		//		int p3x = round(pos.x), p3y = round(pos.y);
//
//
		font.draw(batch, ("scaleX:" + sprite.getScaleX() + ";scaleY:" + sprite.getScaleY()), 10, (tpos+=15));
		font.draw(batch, ("originX:" + sprite.getOriginX() + ";originY:" + sprite.getOriginY()), 10, (tpos+=15));
		font.draw(batch, ("width:" + sprite.getWidth() + ";height:" + sprite.getHeight()), 10, (tpos+=15));
		
//		font.draw(batch, ("p1(x,y)=(" + Math.round(p1x) + "," + Math.round(p1y) + ")"), 10, (tpos+=15));
//		font.draw(batch, ("p2(x,y)=(" + Math.round(p2x) + "," + Math.round(p2y) + ")"), 10, (tpos+=15));
//		font.draw(batch, ("dh(y1,y2)=(" + Math.round(p1y-p0y) + "," + Math.round(p2y-p0y) + ")"), 10, (tpos+=15));
//		font.draw(batch, ("dw(x1,x2)=(" + Math.round(p1x-p0x) + "," + Math.round(p2x-p0x) + ")"), 10, (tpos+=30));
//
//		int num = 0;
//		for(Vector2 v : shipPoints){
//			font.draw(batch, ("p" + num++ + "(x,y)=(" + Math.round(p0x+v.x) + "," + Math.round(p0y+v.y) + ")"), 10, (tpos+=15));	
//		}

		//		font.draw(batch, ("p3(x,y)=(" + Math.round(p3x) + "," + Math.round(p3y) + ")"), 10, (tpos+=15));

		//		font.draw(batch, ("p0(x,y)=(" + Math.round(p0x) + "," + Math.round(p0y) + ")"), 10, (tpos+=15));
		//		font.draw(batch, ("p0(x,y)=(" + Math.round(p0x) + "," + Math.round(p0y) + ")"), 10, (tpos+=15));
		//		font.draw(batch, ("p0(x,y)=(" + Math.round(p0x) + "," + Math.round(p0y) + ")"), 10, (tpos+=15));



	}
}
