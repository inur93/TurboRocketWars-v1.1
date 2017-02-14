package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static com.vormadal.turborocket.utils.ConfigUtil.*;
import static com.vormadal.turborocket.utils.PropKeys.*;

public class SeekerMissile extends Ammo {
	
	private static int SEEKER_AMMO_COST = readInt(SEEKER_COST);
	private float seekerSpeed = readFloat(SEEKER_SPEED);
	private float initialSpeed = readFloat(SEEKER_INIT_SPEED);
	private long timeBeforeSeeking = readLong(SEEKER_TIME_BEFORE_SEEK); // msec
	private boolean superSeekerOn = readBoolean(SEEKER_SUPER_SEEKER);
	
	public SeekerMissile(Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
		super(initialVel, pos, dir, world);
	}

	@Override
	public void create() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.4f, 0.4f);
		body.createFixture(shape, readFloat(SEEKER_DENSITY));
		body.applyLinearImpulse(dir.scl(initialSpeed), pos, true);
//		body.setUserData(new UserDataProp(USER_DATA_SHOT, Color.WHITE, 1, true, this));
//		new Thread(this).start();	
	}
	
	public static class SeekerFactory implements AmmoFaBombctory<SeekerMissile>{

		public SeekerMissile factory(Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
			return new SeekerMissile(initialVel, pos, dir, world);
		}
		
		@Override
		public int getAmmoCost() {
			return SEEKER_AMMO_COST;
		}
		
	}

	@Override
	public void run() {
		
		delay(timeBeforeSeeking);
		Ship target = null;
		float shortest = 0;
		for(Ship s : Ship.ships){
			float curLength = pos.sub(s.getBody().getPosition()).length();
			if(target == null || curLength < shortest){
				target = s;
				shortest = curLength;
			}
		}
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < shotDuration){
			Vec2 direction = target.getBody().getPosition().sub(getBody().getPosition());
			direction.normalize();
			Vec2 directedSpeed = direction.mul(seekerSpeed*10);
			Vec2 targetSpeed = target.getBody().getLinearVelocity();
			
			if(superSeekerOn)this.getBody().setLinearVelocity(directedSpeed.add(targetSpeed));
			else this.getBody().applyForceToCenter(directedSpeed.add(targetSpeed).mul(50));
			
			delay(100);
		}
	}
	
	private void delay(long msec){
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {}
	}
}

