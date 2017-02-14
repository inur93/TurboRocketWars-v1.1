package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Fragment extends Ammo {

	private static int FRAGMENTS_AMMO_COST = 1;
	private float impFactor = 150;
	public Fragment(Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
		super(initialVel, pos, dir, world);
		
	}

	@Override
	public void create() {
		createBody();
		createActor();
	}
	
	private void createBody(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10f, 15f);
	    body.createFixture(shape, 10);
		body.applyLinearImpulse(dir.scl(impFactor), pos, true);
//		body.setAngularVelocity(0.6f);
	}
	
	private void createActor(){
		
	}

	public static class FragmentFactory implements AmmoFactory<Fragment>{

		public Fragment factory(Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
			return new Fragment(initialVel, pos, dir, world);
		}
		
		@Override
		public int getAmmoCost() {
			return FRAGMENTS_AMMO_COST;
		}
		
	}

	
}

