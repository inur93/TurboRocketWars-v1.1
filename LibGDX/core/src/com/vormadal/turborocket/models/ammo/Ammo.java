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

public abstract class Ammo{

	protected World world;
	protected Body body;
	protected double damage = readDouble(AMMO_DMG); // default value
	protected int ammoCost = readInt(AMMO_COST);
	protected Vector2 initialVel;
	protected Vector2 pos;
	protected Vector2 dir;
	protected long startTime; // ms
	protected final long shotDuration = readLong(AMMO_DURATION); //ms
	
	public Ammo(Vector2 initialVel, Vector2 pos, Vector2 dir, World world) {
		this.initialVel = initialVel;
		this.pos = pos;
		this.dir = dir.cpy();
		this.dir.nor();
		this.world = world;	
//		GameController.tasks.add(this);
//		GameController.shots.add(this);
		this.startTime = System.currentTimeMillis();
	}
	
	public abstract void create();
	
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
	public void destroy(){
		world.destroyBody(getBody());
//		GameController.shots.remove(this);
	}

	public double getDamage(){
		return this.damage;
	}
	
	
	public Body getBody(){
		return this.body;
	}

}
