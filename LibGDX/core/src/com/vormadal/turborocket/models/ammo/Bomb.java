package com.vormadal.turborocket.models.ammo;

import static com.vormadal.turborocket.utils.PropKeys.getBombCost;
import static com.vormadal.turborocket.utils.PropKeys.getBombExplosionImpulse;
import static com.vormadal.turborocket.utils.PropKeys.getBombNumberFragments;
import static com.vormadal.turborocket.utils.PropKeys.getBombTimeToDetonate;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.WorldEntityData;
import com.vormadal.turborocket.models.actors.ActorBomb;


public class Bomb  extends Ammo{

	private float impFactor = getBombExplosionImpulse(); //20
	private long timeToDetonate = getBombTimeToDetonate();//1000;//BombTimeToDetonate(); // msec
	private int numberFragments = getBombNumberFragments(); //10;//BombNumberFragments();
	private Task task;
	public Bomb(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
		super(initialVel, pos, dir, entitiesController);
		this.damage = 10;//BombDamage();
	}

	@Override
	public Actor create(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(0.4f, 0.4f);
//		body.createFixture(shape, SHOT_DENSITY);
		body.applyLinearImpulse(dir.scl(impFactor), pos, true);
		task = new Task() {
			
			@Override
			public void run() {
				double step = Math.PI*2/numberFragments;
				for(int i = 0; i < numberFragments; i++){
//					com.badlogic.gdx.math.MathUtils.ma m = Mat22.createRotationalTransform((float) step*i);
					float angle = (float)step*i;
					Vector2 dir = new Vector2(0f,1f).rotateRad(angle);
					Vector2 position = Bomb.this.pos.add(dir.cpy().scl(7f));
					new Fragment(new Vector2(), position, dir, entitiesController);
				}
				Bomb.this.entitiesController.destroyWhenReady(Bomb.this);
			}
		};
		Timer.schedule(task, timeToDetonate);
		
		body.setUserData(new WorldEntityData(this));
		return (this.actor = new ActorBomb(this));
	}

	@Override
	public Actor destroy(World world) {
		task.cancel();
		return super.destroy(world);
	}
	
	public static class BombFactory implements AmmoFactory<Bomb>{

		public Bomb factory(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
			return new Bomb(initialVel, pos, dir, entitiesController);
		}
		@Override
		public float getAmmoCost() {
			return getBombCost();
		}
	}

}