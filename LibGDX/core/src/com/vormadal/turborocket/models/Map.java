package com.vormadal.turborocket.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.actors.ActorMap;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.models.configs.MapObjectConfig;
import com.vormadal.turborocket.models.configs.PlatformConfig;

public class Map implements WorldEntity{

	private float width, height;
	private Body groundBody;
	private String backgroundPath;
	private List<Platform> platforms = new ArrayList<>();
	private List<MapObject> mapObjects = new ArrayList<>();
	private Vector2[] spawnPoints;
	private MapConfig config;
	public Map(WorldEntitiesController entityController, MapConfig config){
		this.config = config;
		this.width = config.width;
		this.height = config.height;
		this.backgroundPath = config.backgroundPath;
		entityController.createWhenReady(this);
		this.spawnPoints = new Vector2[config.platforms.size()];
		int index = 0;
		for(PlatformConfig pc : config.platforms){
			this.platforms.add(new Platform(entityController, pc));
			Vector2 spawnPoint = new Vector2(pc.pointA).add(pc.pointB).scl(0.5f).add(0,5);
			this.spawnPoints[index++] = spawnPoint;
			
		}
		for(MapObjectConfig mo : config.mapObjects){
			this.mapObjects.add(new MapObject(entityController, mo));
		}

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
		for(Platform p : this.platforms){
			p.destroy(world);
		}
		for(MapObject mo : this.mapObjects){
			mo.destroy(world);
		}
		world.destroyBody(groundBody);
		return null;
	}
	
	public Vector2[] getSpawnPoints(){
		return this.spawnPoints;
	}
	
	public float getWidth(){
		return this.width;
	}
	
	public float getHeight(){
		return this.height;
	}
	public String getBackgroundPath() {
		return this.backgroundPath;
	}
	
	public MapConfig getConfig(){
		return this.config;
	}

}
