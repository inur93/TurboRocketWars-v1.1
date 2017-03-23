package com.vormadal.turborocket.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.controllers.WorldEntitiesController;
import com.vormadal.turborocket.models.configs.MapObjectConfig;

public class MapObject implements WorldEntity{

	private MapObjectConfig config;
	private Body body;
	public MapObject(WorldEntitiesController entitiesController, MapObjectConfig config) {
		this.config = config;
		entitiesController.createWhenReady(this);
	}
	@Override
	public Actor create(World world) {
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2());
		body = world.createBody(groundBodyDef);
		ChainShape groundBox = new ChainShape();
		groundBox.createChain(config.vertices.toArray(new Vector2[0]));
		body.createFixture(groundBox, 0);
		body.setUserData(new WorldEntityData(this));
		return null;
	}

	@Override
	public Actor destroy(World world) {
		world.destroyBody(body);
		return null;
	}

}
