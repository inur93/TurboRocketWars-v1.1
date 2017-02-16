package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.actors.ActorBomb;

public class Bomb  extends Ammo{

	private static int BOMB_AMMO_COST = 10;//BombAmmoCost();
	private float impFactor = 20;
	private long timeToDetonate = 1000;//BombTimeToDetonate(); // msec
	private int numberFragments = 10;//BombNumberFragments();
	
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
//		body.setUserData(new UserDataProp(USER_DATA_SHOT, Color.WHITE, 1, true, this));
		Timer.schedule(new Task() {
			
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
		}, timeToDetonate);
		
		return (this.actor = new ActorBomb(this));
	}

	
	public static class BombFactory implements AmmoFactory<Bomb>{

		public Bomb factory(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
			return new Bomb(initialVel, pos, dir, entitiesController);
		}
		@Override
		public int getAmmoCost() {
			return BOMB_AMMO_COST;
		}
	}

}