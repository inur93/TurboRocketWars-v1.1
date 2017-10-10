package com.vormadal.turborocket.models.ammo;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.configurations.PropKeys;
import com.vormadal.turborocket.controllers.WorldEntitiesController;
import com.vormadal.turborocket.models.WorldEntity;

public abstract class Ammo implements WorldEntity{

	protected WorldEntitiesController entitiesController;
	protected Body body;
	protected Actor actor;
	
	protected float damage;
	protected float ammoCost;
	protected final long shotDuration; //ms
	
	protected Vector2 v0;
	protected Vector2 pos;
	protected Vector2 dir;
	protected long startTime; // ms
	
	private String id; 
	
	public Ammo(Vector2 v0, 
			Vector2 pos, 
			Vector2 dir,
			float damage, 
			float cost, 
			long duration, 
			WorldEntitiesController entitiesController) {
		this.v0 = v0;
		this.pos = pos;
		this.dir = dir.cpy();
		this.dir.nor();
		this.damage = damage;
		this.ammoCost = cost;
		this.shotDuration = duration;
		this.id = UUID.randomUUID().toString();
		this.entitiesController = entitiesController;	
		this.startTime = System.currentTimeMillis();
		this.entitiesController.createWhenReady(this);
	}
	
	public abstract Actor create(World world);
	
	/**
	 * lifetime for shot. 
	 * @return true if shot should be removed because of timing out. 
	 * Otherwise false
	 */
	public boolean hasTimedOut(){
		if(System.currentTimeMillis()-startTime > shotDuration){
			return true;
		}
		return false;
	}
	
	public void collide(){
		this.entitiesController.destroyWhenReady(this);
	}
	
	/**
	 * removes body from world and from controller shots list
	 */
	public Actor destroy(World world){
		if(body == null) return null;
		this.actor.remove();
		world.destroyBody(getBody());
		this.actor = null;
		this.body = null;
		return null;
	}

	public double getDamage(){
		return this.damage;
	}
	
	
	public Body getBody(){
		return this.body;
	}

}
