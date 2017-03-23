package com.vormadal.turborocket.models.ammo;

import static com.vormadal.turborocket.configurations.PropKeys.getSeekerCacheShips;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerCost;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerDamage;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerDensity;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerDuration;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerInitSpeed;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerSize;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerSpeed;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerSuperSeeker;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerTimeBeforeSeek;
import static com.vormadal.turborocket.configurations.PropKeys.getSeekerUpdateFrequency;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.vormadal.turborocket.controllers.WorldEntitiesController;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.WorldEntityData;
import com.vormadal.turborocket.models.actors.ActorSeekerMissile;

public class SeekerMissile extends Ammo {

	private final float seekerSpeed = getSeekerSpeed();
	private final float initialSpeed = getSeekerInitSpeed();
	private final long timeBeforeSeeking = getSeekerTimeBeforeSeek(); // msec
	private final boolean superSeekerOn = getSeekerSuperSeeker();
	private float seekerUpdateFrequency = getSeekerUpdateFrequency(); //sec
	private boolean cacheShips = getSeekerCacheShips();
	private Task seekerTask;

	public SeekerMissile(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
		super(initialVel, pos, dir, getSeekerDamage(), getSeekerCost(), getSeekerDuration(), entitiesController);
	}

	@Override
	public Actor create(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		float size = getSeekerSize();
		shape.setAsBox(size, size);
		body.createFixture(shape, getSeekerDensity());
		shape.dispose();
		body.applyLinearImpulse(dir.scl(initialSpeed), pos, true);

		seekerTask = new Task() {
			@SuppressWarnings("rawtypes")
			private ArrayList<Ship> ships = null;
			@Override
			public void run() {
				if(ships == null || cacheShips){
					ships = entitiesController.getEntities(Ship.class);
				}
				
				float shortest = 0;
				Ship<?,?> target = null;
				for(Ship<?,?> s : ships){
					float curLength = getBody().getPosition().sub(s.getBody().getPosition()).len();
					if(target == null || curLength < shortest){
						target = s;
						shortest = curLength;
					}
				}
				long startTime = System.currentTimeMillis();

				Vector2 direction = target.getBody().getPosition().sub(getBody().getPosition());
				direction.nor();
				Vector2 directedSpeed = direction.scl(seekerSpeed);
				Vector2 targetSpeed = target.getBody().getLinearVelocity();

				if(superSeekerOn)getBody().setLinearVelocity(directedSpeed.add(targetSpeed));
				else getBody().applyForceToCenter(directedSpeed.add(targetSpeed), true);

				// cancels the task when lifetime of ammo is out. and then destroy the Box2d body
				if(System.currentTimeMillis() - startTime < shotDuration){
					this.cancel();
					entitiesController.destroyWhenReady(SeekerMissile.this);
				}
			}
		};
		Timer.schedule(seekerTask, timeBeforeSeeking, seekerUpdateFrequency);
		body.setUserData(new WorldEntityData(this));
		
		return (this.actor = new ActorSeekerMissile(this));
	}
	
	@Override
	public Actor destroy(World world) {
		seekerTask.cancel();
		return super.destroy(world);	
	}

	public static class SeekerFactory implements AmmoFactory<SeekerMissile>{

		public SeekerMissile factory(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
			return new SeekerMissile(initialVel, pos, dir, entitiesController);
		}

		@Override
		public float getAmmoCost() {
			return getSeekerCost();
		}

	}
}

