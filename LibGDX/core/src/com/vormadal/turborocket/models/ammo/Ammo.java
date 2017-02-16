package com.vormadal.turborocket.models.ammo;

import static com.vormadal.turborocket.utils.ConfigUtil.readDouble;
import static com.vormadal.turborocket.utils.ConfigUtil.readInt;
import static com.vormadal.turborocket.utils.ConfigUtil.readLong;
import static com.vormadal.turborocket.utils.PropKeys.AMMO_COST;
import static com.vormadal.turborocket.utils.PropKeys.AMMO_DMG;
import static com.vormadal.turborocket.utils.PropKeys.AMMO_DURATION;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.WorldEntity;

public abstract class Ammo implements WorldEntity{

	protected WorldEntitiesController entitiesController;
	protected Body body;
	protected Actor actor;
	protected double damage = readDouble(AMMO_DMG); // default value
	protected int ammoCost = readInt(AMMO_COST);
	protected Vector2 v0;
	protected Vector2 pos;
	protected Vector2 dir;
	protected long startTime; // ms
	protected final long shotDuration = readLong(AMMO_DURATION); //ms
	
	public Ammo(Vector2 v0, Vector2 pos, Vector2 dir, WorldEntitiesController entitiesController) {
		this.v0 = v0;
		this.pos = pos;
		System.out.println("dir1: " + dir);
		this.dir = dir.cpy();
		System.out.println("dir2: " + this.dir);
		this.dir.nor();
		System.out.println("dir3: " + this.dir);

		this.entitiesController = entitiesController;	
//		GameController.tasks.add(this);
//		GameController.shots.add(this);
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
	
	public int ammoCost(){
		return this.ammoCost;
	}
	
	/**
	 * removes body from world and from controller shots list
	 */
	public Actor destroy(World world){
		world.destroyBody(getBody());
		return this.actor;
	}

	public double getDamage(){
		return this.damage;
	}
	
	
	public Body getBody(){
		return this.body;
	}

}
