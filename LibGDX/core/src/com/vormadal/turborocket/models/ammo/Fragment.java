package com.vormadal.turborocket.models.ammo;

import static com.vormadal.turborocket.configurations.PropKeys.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.controllers.WorldEntitiesController;
import com.vormadal.turborocket.models.WorldEntityData;
import com.vormadal.turborocket.models.actors.ActorAmmo;

public class Fragment extends Ammo {

	private float impFactor = getFragmentImpulse();//150;
	public Fragment(Vector2 initialVel, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
		super(initialVel, pos, dir, getFragmentDamage(), getFragmentCost(), getFragmentDuration(), entitiesController);
		
	}

	@Override
	public Actor create(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		float size = getFragmentSize();
		shape.setAsBox(size, size);
	    body.createFixture(shape, getFragmentDensity());
		body.applyLinearImpulse(dir.scl(impFactor), pos, true);
		body.setUserData(new WorldEntityData(this));
		String texPath = ConfigManager.instance().getSettingValue(TEX_FRAGMENT);
		return (this.actor = new ActorAmmo(this, texPath));
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

