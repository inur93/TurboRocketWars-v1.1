package com.vormadal.turborocket.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.actors.ActorMap;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.models.configs.PlatformConfig;
import static com.vormadal.turborocket.utils.PropKeys.*;

public class Platform implements WorldEntity{

	
	private PlatformConfig config;
	private Body body;
	
	public Platform(WorldEntitiesController entityController, PlatformConfig config) {
		this.config = config;
		entityController.createWhenReady(this);
	}
	
	@Override
	public Actor create(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(new Vector2());
		
		body = world.createBody(bodyDef);
		ChainShape shape = new ChainShape();
		shape.createChain(new Vector2[]{config.pointA, config.pointB});
		Fixture fix = body.createFixture(shape, 0);
		fix.setFriction(getPlatformFriction());
		body.setUserData(new WorldEntityData(this));
		return null;
	}

	@Override
	public Actor destroy(World world) {
		world.destroyBody(this.body);
		return null;
	}

	
}
