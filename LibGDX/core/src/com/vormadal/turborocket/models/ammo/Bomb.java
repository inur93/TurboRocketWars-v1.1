package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Bomb  extends Ammo implements Runnable{

	private static int BOMB_AMMO_COST = 10;//BombAmmoCost();
	private float impFactor = 20;
	private long timeToDetonate = 1000;//BombTimeToDetonate(); // msec
	private int numberFragments = 10;//BombNumberFragments();
	
	public Bomb(Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
		super(initialVel, pos, dir, world);
		this.damage = 10;//BombDamage();
	}

	@Override
	public void create() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.4f, 0.4f);
//		body.createFixture(shape, SHOT_DENSITY);
		body.applyLinearImpulse(dir.scl(impFactor), pos, true);
//		body.setUserData(new UserDataProp(USER_DATA_SHOT, Color.WHITE, 1, true, this));
		new Thread(this).start();
	}

	public static class BombFactory implements AmmoFactory<Bomb>{

		public Bomb factory(Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
			return new Bomb(initialVel, pos, dir, world);
		}
		@Override
		public int getAmmoCost() {
			return BOMB_AMMO_COST;
		}
	}

	public void run() {
		try {
			Thread.sleep(timeToDetonate);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Vector2 d = new Vector2(0, 1);
		double step = Math.PI*2/numberFragments;
//		for(int i = 0; i < numberFragments; i++){
//			com.badlogic.gdx.math.MathUtils.ma m = Mat22.createRotationalTransform((float) step*i);
//			new Fragments(new Vec2(), this.pos.add(m.mul(d).mul(7f)), m.mul(d), world);
//		}
//		GameController.bodiesToDelete.add(this.body);
	}

}