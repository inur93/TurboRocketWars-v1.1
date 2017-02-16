package com.vormadal.turborocket.utils;

import static com.vormadal.turborocket.utils.ConfigUtil.*;
public class PropKeys {

	// AMMO
	public static final String AMMO_DMG = "AMMO_DMG";
	public static final String AMMO_COST = "AMMO_COST";
	public static final String AMMO_DURATION = "AMMO_DURATION";
	
	public static final String BULLET_COST = "BULLET_COST";
	public static final String BULLET_SPEED = "BULLET_SPEED";
	public static final String BULLET_DENSITY = "BULLET_DENSITY";
	
	public static final String SEEKER_COST = "SEEKER_COST";
	public static final String SEEKER_BEFORE_SEEKING = "SEEKER_BEFORE_SEEKING";
	public static final String SEEKER_SUPER_SEEKER = "SEEKER_SUPER_SEEKER";
	public static final String SEEKER_SPEED = "SEEKER_SPEED";
	public static final String SEEKER_INIT_SPEED = "SEEKER_INIT_SPEED";
	public static final String SEEKER_TIME_BEFORE_SEEK = "SEEKER_TIME_BEFORE_SEEK";
	public static final String SEEKER_DENSITY = "SEEKER_DENSITY";
	
	//SHIP
	public static final String SHIP_LIVES = "SHIP_LIVES";
	public static final String SHIP_MAX_HP = "SHIP_MAX_HP";
	public static final String SHIP_REGEN_HP = "SHIP_REGEN_HP";
	public static final String SHIP_REGEN_AMMO = "SHIP_REGEN_AMMO";
	public static final String SHIP_MAX_AMMO = "SHIP_MAX_AMMO";
	public static final String SHIP_HP_REGEN_FREQUENCY = "SHIP_HP_REGEN_FREQUENCY";
	public static final String SHIP_AMMO_REGEN_FREQUENCY = "SHIP_AMMO_REGEN_FREQUENCY";
	public static final String SHIP_ROTATION_SPEED = "SHIP_ROTATION_SPEED";
	public static final String SHIP_BOOST_IMPULSE = "SHIP_BOOST_IMPULSE";
	public static final String SHIP_SCALE = "SHIP_SCALE";
	
	public static void setDefault(){
		saveProp(SHIP_LIVES, 5);
		saveProp(SHIP_MAX_HP, 100);
		saveProp(SHIP_REGEN_HP, 5);
		saveProp(SHIP_REGEN_AMMO, 2);
		saveProp(SHIP_MAX_AMMO, 50);
		saveProp(SHIP_HP_REGEN_FREQUENCY, 200);
		saveProp(SHIP_AMMO_REGEN_FREQUENCY, 200);
		saveProp(SHIP_ROTATION_SPEED, 300);
		saveProp(SHIP_BOOST_IMPULSE, 100);
		saveProp(SHIP_SCALE, 1);
		
		saveProp(BULLET_SPEED, 100);
		
		saveProp(SEEKER_DENSITY, 1);
		saveProp(SEEKER_SPEED, 200);
		saveProp(SEEKER_INIT_SPEED, 150);
		saveProp(SEEKER_BEFORE_SEEKING, 2);
		saveProp(SEEKER_TIME_BEFORE_SEEK, 2);
		saveProp(SEEKER_SUPER_SEEKER, true);
	}
	
	
}
