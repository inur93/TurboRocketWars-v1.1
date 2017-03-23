package com.vormadal.turborocket.models.ammo;

import static com.vormadal.turborocket.configurations.PropKeys.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.controllers.WorldEntitiesController;
import com.vormadal.turborocket.models.WorldEntityData;
import com.vormadal.turborocket.models.actors.ActorBullet;

public class Bullet extends Ammo {

	private static final float BULLET_SPEED = getBulletSpeed();
	
	/**
	 * 
	 * @param v0 velocity of parent body, fx ship
	 * @param pos start position of shot, fx somewhere in front of ship
	 * @param dir firing direction, fx same direction as ship
	 * @param world, the one and only world
	 */
	public Bullet(Vector2 v0, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
		super(v0, pos, dir, getBulletDamage(), getBulletCost(), getBulletDuration(), entitiesController);
	}

	public Actor create(World world){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos.cpy());
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		float size = getBulletSize();
		shape.setAsBox(size, size);
	    body.createFixture(shape, getBulletDensity());
	 // add first velocity of ship given by initialVel then normal shots speed with ship direction
	    body.setLinearVelocity(v0);
	    body.applyLinearImpulse(dir.scl(BULLET_SPEED), pos, true);
	    body.setUserData(new WorldEntityData(this));
	    return (this.actor = new ActorBullet(this));
	}
	
	
	public static class NormalShotFactory implements AmmoFactory<Bullet>{

		public Bullet factory( Vector2 v0, Vector2 p0, Vector2 dir, WorldEntitiesController entitiesController) {
			return new Bullet(v0, p0, dir, entitiesController);
		}
		
		@Override
		public float getAmmoCost() {
			return getBulletCost();
		}
	}
}
