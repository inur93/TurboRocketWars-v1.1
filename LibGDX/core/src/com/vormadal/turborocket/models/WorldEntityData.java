package com.vormadal.turborocket.models;

import com.vormadal.turborocket.models.ammo.Ammo;

public class WorldEntityData {

	public enum EntityType {SHIP, AMMO, PLATFORM, MAP}
	private final EntityType type;
	private final WorldEntity entity;
	
	public WorldEntityData(Ship<?,?> ship){
		this.type = EntityType.SHIP;
		this.entity = ship;
	}
	
	public WorldEntityData(Ammo ammo){
		this.type = EntityType.AMMO;
		this.entity = ammo;
	}
	
	public WorldEntityData(Map map){
		this.type = EntityType.MAP;
		this.entity = map;
	}
	
	public WorldEntityData(MapObject mapObject){
		this.type = EntityType.MAP;
		this.entity = mapObject;
	}
	
	public WorldEntityData(Platform platform){
		this.type = EntityType.PLATFORM;
		this.entity = platform;
	}
	
	public WorldEntity getEntity(){
		return this.entity;
	}
	
	public EntityType getType(){
		return this.type;
	}
}
