package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.WorldEntityData;
import com.vormadal.turborocket.models.actors.ActorFragment;
import static com.vormadal.turborocket.utils.PropKeys.*;

public class Fragment extends Ammo {

	private float impFactor = getFragmentImpulse();//150;
	public Fragment(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
		super(initialVel, pos, dir, entitiesController);
		
	}

	@Override
	public Actor create(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10f, 15f);
	    body.createFixture(shape, 10);
		body.applyLinearImpulse(dir.scl(impFactor), pos, true);
		body.setUserData(new WorldEntityData(this));
		return (this.actor = new ActorFragment(this));
	}
	


	public static class FragmentFactory implements AmmoFactory<Fragment>{

		public Fragment factory(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
			return new Fragment(initialVel, pos, dir, entitiesController);
		}
		
		@Override
		public float getAmmoCost() {
			return getFragmentCost();
		}
		
	}

	
}

