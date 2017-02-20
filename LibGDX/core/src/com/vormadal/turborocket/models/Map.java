package com.vormadal.turborocket.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.actors.ActorMap;

public class Map implements WorldEntity{

	private float width, height;
	private Body groundBody;
	private WorldEntitiesController entityController;
	public Map(WorldEntitiesController entityController, float width, float height){
		this.entityController = entityController;
		this.width = width;
		this.height = height;
		this.entityController.createWhenReady(this);
	}
	@Override
	public Actor create(World world) {
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2());
		groundBody = world.createBody(groundBodyDef);
		ChainShape groundBox = new ChainShape();
		Vector2[] mapPoints = new Vector2[]{new Vector2(), 	new Vector2(width, 0),
										new Vector2(width, height), 	new Vector2(0, height)};
		groundBox.createLoop(mapPoints);

		groundBody.createFixture(groundBox, 0);
		groundBody.setUserData(new WorldEntityData(this));
		ActorMap actor = new ActorMap(this);
		actor.setZIndex(1);
		return actor;
	}

	@Override
	public Actor destroy(World world) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public float getWidth(){
		return this.width;
	}
	
	public float getHeight(){
		return this.height;
	}

}
