package com.vormadal.turborocket.models.ammo;

import static com.vormadal.turborocket.utils.PropKeys.getSeekerCost;
import static com.vormadal.turborocket.utils.PropKeys.getSeekerDensity;
import static com.vormadal.turborocket.utils.PropKeys.getSeekerInitSpeed;
import static com.vormadal.turborocket.utils.PropKeys.getSeekerSpeed;
import static com.vormadal.turborocket.utils.PropKeys.getSeekerSuperSeeker;
import static com.vormadal.turborocket.utils.PropKeys.getSeekerTimeBeforeSeek;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.WorldEntityData;
import com.vormadal.turborocket.models.actors.ActorSeekerMissile;

public class SeekerMissile extends Ammo {

	private static final float seekerSpeed = getSeekerSpeed();
	private static final float initialSpeed = getSeekerInitSpeed();
	private static final long timeBeforeSeeking = getSeekerTimeBeforeSeek(); // msec
	private static final boolean superSeekerOn = getSeekerSuperSeeker();
	private static float seekerUpdateFrequency = 0.2f;
	private boolean cacheShips = true;
	private Task seekerTask;

	public SeekerMissile(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
		super(initialVel, pos, dir, entitiesController);
	}

	@Override
	public Actor create(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.4f, 0.4f);
		body.createFixture(shape, getSeekerDensity());
		shape.dispose();
		body.applyLinearImpulse(dir.scl(initialSpeed), pos, true);
		//		body.setUserData(new UserDataProp(USER_DATA_SHOT, Color.WHITE, 1, true, this));

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
					float curLength = pos.sub(s.getBody().getPosition()).len();
					if(target == null || curLength < shortest){
						target = s;
						shortest = curLength;
					}
				}
				long startTime = System.currentTimeMillis();

				Vector2 direction = target.getBody().getPosition().sub(getBody().getPosition());
				direction.nor();
				Vector2 directedSpeed = direction.scl(seekerSpeed*10);
				Vector2 targetSpeed = target.getBody().getLinearVelocity();

				if(superSeekerOn)getBody().setLinearVelocity(directedSpeed.add(targetSpeed));
				else getBody().applyForceToCenter(directedSpeed.add(targetSpeed).scl(50), true);

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

