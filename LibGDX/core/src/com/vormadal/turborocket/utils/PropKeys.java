package com.vormadal.turborocket.utils;

import static com.vormadal.turborocket.utils.ConfigUtil.MAPS_PATH;
import static com.vormadal.turborocket.utils.ConfigUtil.readBoolean;
import static com.vormadal.turborocket.utils.ConfigUtil.readFloat;
import static com.vormadal.turborocket.utils.ConfigUtil.readInt;
import static com.vormadal.turborocket.utils.ConfigUtil.readLong;
import static com.vormadal.turborocket.utils.ConfigUtil.readString;
import static com.vormadal.turborocket.utils.ConfigUtil.resetConfigPath;
import static com.vormadal.turborocket.utils.ConfigUtil.saveProp;
import static com.vormadal.turborocket.utils.ConfigUtil.setConfigPath;

import com.vormadal.turborocket.models.configs.MapConfig;
public class PropKeys {

	private static int NEXT_ID = 0;
	public static int nextId() {
		return NEXT_ID++;
	}
	
	//screen configs
	private static final String STATS_BAR_HEIGHT = "STATS_BAR_HEIGHT";
	private static final String HP_BAR_LENGTH = "HP_BAR_SIZE";
	private static final String HP_BAR_HEIGHT = "HP_BAR_HEIGHT";
	private static final String STATS_BAR_X_OFFSET = "STATS_BAR_X_OFFSET";
	private static final String STATS_BAR_Y_OFFSET = "STATS_BAR_Y_OFFSET";
	
	private static final String MAP_KEYS = "MAP_KEYS";
	private static final String PLATFORM_FRICTION = "PLATFORM_FRICTION";
	
	// AMMO
	private static final String AMMO_DMG = "AMMO_DMG";
	private static final String AMMO_COST = "AMMO_COST";
	private static final String AMMO_DURATION = "AMMO_DURATION";
	
	private static final String BULLET_COST = "BULLET_COST";
	private static final String BULLET_SPEED = "BULLET_SPEED";
	private static final String BULLET_DENSITY = "BULLET_DENSITY";
	
	private static final String BOMB_COST = "BOMB_COST";
	private static final String BOMB_SPEED = "BOMB_SPEED";
	private static final String BOMB_DENSITY = "BOMB_DENSITY";
	private static final String BOMB_NUMBER_FRAGMENTS = "BOMB_NUMBER_FRAGMENTS";
	private static final String BOMB_TIME_TO_DETONATE = "BOMB_TIME_TO_DETONATE";
	private static final String BOMB_EXPLOSION_IMPULSE = "BOMB_EXPLOSION_IMPULSE";
	
	private static final String FRAGMENT_COST = "FRAGMENT_COST";
	private static final String FRAGMENT_IMPULSE = "FRAGMENT_IMPULSE";
	
	private static final String SEEKER_COST = "SEEKER_COST";
	private static final String SEEKER_BEFORE_SEEKING = "SEEKER_BEFORE_SEEKING";
	private static final String SEEKER_SUPER_SEEKER = "SEEKER_SUPER_SEEKER";
	private static final String SEEKER_SPEED = "SEEKER_SPEED";
	private static final String SEEKER_INIT_SPEED = "SEEKER_INIT_SPEED";
	private static final String SEEKER_TIME_BEFORE_SEEK = "SEEKER_TIME_BEFORE_SEEK";
	private static final String SEEKER_DENSITY = "SEEKER_DENSITY";
	
	//SHIP
	private static final String SHIP_LIVES = "SHIP_LIVES";
	private static final String SHIP_MAX_HP = "SHIP_MAX_HP";
	private static final String SHIP_REGEN_HP = "SHIP_REGEN_HP";
	private static final String SHIP_REGEN_AMMO = "SHIP_REGEN_AMMO";
	private static final String SHIP_MAX_AMMO = "SHIP_MAX_AMMO";
	private static final String SHIP_HP_REGEN_FREQUENCY = "SHIP_HP_REGEN_FREQUENCY";
	private static final String SHIP_AMMO_REGEN_FREQUENCY = "SHIP_AMMO_REGEN_FREQUENCY";
	private static final String SHIP_ROTATION_SPEED = "SHIP_ROTATION_SPEED";
	private static final String SHIP_BOOST_IMPULSE = "SHIP_BOOST_IMPULSE";
	private static final String SHIP_SCALE = "SHIP_SCALE";
	private static final String SHIP_DENSITY = "SHIP_DENSITY";
	
	
	
	public static void setDefault(){
		
		saveProp(STATS_BAR_HEIGHT, 50);
		saveProp(HP_BAR_LENGTH, 100);
		saveProp(HP_BAR_HEIGHT, 10);
		saveProp(STATS_BAR_X_OFFSET, 10);
		saveProp(STATS_BAR_Y_OFFSET, 10);
		saveProp(MAP_KEYS, "maps/map1.config");
		saveProp(PLATFORM_FRICTION, 5);
		
		
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
		saveProp(SHIP_DENSITY, 1);
		
		//default ammo
		saveProp(AMMO_COST, 2);
		saveProp(AMMO_DMG, 2);
		saveProp(AMMO_DURATION, 6000); //msec
		
		//bullet
		saveProp(BULLET_COST, 1);
		saveProp(BULLET_DENSITY, 0.2);
		saveProp(BULLET_SPEED, 1600);
		
		//Bomb
		saveProp(BOMB_COST, 5);
		saveProp(BOMB_DENSITY, 2);
		saveProp(BOMB_SPEED, 30);
		saveProp(BOMB_EXPLOSION_IMPULSE, 20);
		saveProp(BOMB_TIME_TO_DETONATE, 1000);
		saveProp(BOMB_NUMBER_FRAGMENTS, 10);
		
		saveProp(FRAGMENT_COST, 0.1);
		saveProp(FRAGMENT_IMPULSE, 500);
		
		//seeker
		saveProp(SEEKER_COST, 3);
		saveProp(SEEKER_DENSITY, 1);
		saveProp(SEEKER_SPEED, 200);
		saveProp(SEEKER_INIT_SPEED, 150);
		saveProp(SEEKER_BEFORE_SEEKING, 2);
		saveProp(SEEKER_TIME_BEFORE_SEEK, 2);
		saveProp(SEEKER_SUPER_SEEKER, true);
	}



	public static int getNEXT_ID() {
		return NEXT_ID;
	}



	public static void setNEXT_ID(int nEXT_ID) {
		NEXT_ID = nEXT_ID;
	}



	public static int getStatsBarHeight() {
		return readInt(STATS_BAR_HEIGHT);
	}



	public static int getHpBarLength() {
		return readInt(HP_BAR_LENGTH);
	}



	public static int getHpBarHeight() {
		return readInt(HP_BAR_HEIGHT);
	}



	public static int getStatsBarXOffset() {
		return readInt(STATS_BAR_X_OFFSET);
	}



	public static int getStatsBarYOffset() {
		return readInt(STATS_BAR_Y_OFFSET);
	}

	public static String[] getMapKeys(){
		setConfigPath(MAPS_PATH);
		String keysRaw = readString(MAP_KEYS);
		resetConfigPath();
		return keysRaw.split(";");
	}
	
	public static MapConfig getMapConfig(String key){
		setConfigPath(MAPS_PATH);
		MapConfig config = new XMLParser().readMapConfig(key);
		resetConfigPath();
		return config;
	}
	
	public static float getPlatformFriction(){
		return readFloat(PLATFORM_FRICTION);
	}


	public static float getAmmoDmg() {
		return readFloat(AMMO_DMG);
	}



	public static float getAmmoCost() {
		return readFloat(AMMO_COST);
	}



	public static int getAmmoDuration() {
		return readInt(AMMO_DURATION);
	}



	public static float getBulletCost() {
		return readFloat(BULLET_COST);
	}



	public static float getBulletSpeed() {
		return readFloat(BULLET_SPEED);
	}



	public static float getBulletDensity() {
		return readFloat(BULLET_DENSITY);
	}



	public static float getBombCost() {
		return readFloat(BOMB_COST);
	}



	public static float getBombSpeed() {
		return readFloat(BOMB_SPEED);
	}



	public static float getBombDensity() {
		return readFloat(BOMB_DENSITY);
	}

	public static float getBombExplosionImpulse(){
		return readFloat(BOMB_EXPLOSION_IMPULSE);
	}
	
	public static long getBombTimeToDetonate(){
		return readLong(BOMB_TIME_TO_DETONATE);
	}
	
	public static int getBombNumberFragments(){
		return readInt(BOMB_NUMBER_FRAGMENTS);
	}
	
	public static float getFragmentCost(){
		return readFloat(FRAGMENT_COST);
	}
	
	public static float getFragmentImpulse(){
		return readFloat(FRAGMENT_IMPULSE);
	}


	public static float getSeekerCost() {
		return readFloat(SEEKER_COST);
	}



//	public static int getSeekerBeforeSeeking() {
//		return SEEKER_BEFORE_SEEKING;
//	}



	public static boolean getSeekerSuperSeeker() {
		return readBoolean(SEEKER_SUPER_SEEKER);
	}



	public static float getSeekerSpeed() {
		return readFloat(SEEKER_SPEED);
	}



	public static float getSeekerInitSpeed() {
		return readFloat(SEEKER_INIT_SPEED);
	}



	public static int getSeekerTimeBeforeSeek() {
		return readInt(SEEKER_TIME_BEFORE_SEEK);
	}



	public static float getSeekerDensity() {
		return readFloat(SEEKER_DENSITY);
	}



	public static int getShipLives() {
		return readInt(SHIP_LIVES);
	}



	public static float getShipMaxHp() {
		return readFloat(SHIP_MAX_HP);
	}



	public static float getShipRegenHp() {
		return readFloat(SHIP_REGEN_HP);
	}



	public static float getShipRegenAmmo() {
		return readFloat(SHIP_REGEN_AMMO);
	}



	public static float getShipMaxAmmo() {
		return readFloat(SHIP_MAX_AMMO);
	}



	public static int getShipHpRegenFrequency() {
		return readInt(SHIP_HP_REGEN_FREQUENCY);
	}



	public static int getShipAmmoRegenFrequency() {
		return readInt(SHIP_AMMO_REGEN_FREQUENCY);
	}



	public static float getShipRotationSpeed() {
		return readFloat(SHIP_ROTATION_SPEED);
	}



	public static float getShipBoostImpulse() {
		return readFloat(SHIP_BOOST_IMPULSE);
	}

	public static float getShipDensity(){
		return readFloat(SHIP_DENSITY);
	}

	public static float getShipScale() {
		return readFloat(SHIP_SCALE);
	}
	
	
	
	
}
