package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static com.vormadal.turborocket.utils.ConfigUtil.*;
import static com.vormadal.turborocket.utils.PropKeys.*;

public class Bullet extends Ammo {

	private final float NORMAL_SHOT_SPEED = readFloat(BULLET_SPEED);
	
	/**
	 * 
	 * @param initialVel velocity of parent body, fx ship
	 * @param pos start position of shot, fx somewhere in front of ship
	 * @param dir firing direction, fx same direction as ship
	 * @param world, the one and only world
	 */
	public Bullet(Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
		super(initialVel, pos, dir, world);
	}

	private void createBody(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos.cpy());
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.2f, 0.2f);
	    body.createFixture(shape, readFloat(BULLET_DENSITY));
	    
	}
	
	@Override
	public void create() {
		// create body and adds it to the world at given position
	    createBody();
	    
	    // add first velocity of ship given by initialVel then normal shots speed with ship direction
	    body.setLinearVelocity(initialVel);
	    body.applyLinearImpulse(dir.scl(NORMAL_SHOT_SPEED).cpy(), pos, true);
	    
	    // important to add userdata otherwise no way to determine type of body at collision detection
//	    body.setUserData(new UserDataProp(USER_DATA_SHOT, Color.WHITE, 1, true, this));
	}
	
	public static class NormalShotFactory implements AmmoFactory<Bullet>{

		public Bullet factory( Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
			return new Bullet(initialVel, pos, dir, world);
		}
		
		@Override
		public int getAmmoCost() {
			return readInt(BULLET_COST);
		}
	}
}
