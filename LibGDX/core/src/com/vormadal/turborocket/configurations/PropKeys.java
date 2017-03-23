package com.vormadal.turborocket.configurations;

import java.util.HashMap;
public class PropKeys {

//	private static int NEXT_ID = 0;
//	public static int nextId() {
//		return NEXT_ID++;
//	}
	
	//screen configs
	private static final String STATS_BAR_HEIGHT = "STATS_BAR_HEIGHT";	//config
	private static final String HP_BAR_LENGTH = "HP_BAR_SIZE";			//config
	private static final String HP_BAR_HEIGHT = "HP_BAR_HEIGHT";		//config
	private static final String STATS_BAR_X_OFFSET = "STATS_BAR_X_OFFSET";	//config
	private static final String STATS_BAR_Y_OFFSET = "STATS_BAR_Y_OFFSET";	//config	
	private static final String MAP_KEYS = "MAP_KEYS";						//config

	private static final String PLATFORM_FRICTION = "PLATFORM_FRICTION";	//setting def=5
		
	private static final String BULLET_COST = "BULLET_COST";		//setting hidden
	private static final String BULLET_DAMAGE = "BULLET_DAMAGE";	//setting hidden
	private static final String BULLET_DURATION = "BULLET_DURATION";//setting hidden
	private static final String BULLET_SPEED = "BULLET_SPEED";		//setting hidden
	private static final String BULLET_DENSITY = "BULLET_DENSITY";	//setting hidden
	private static final String BULLET_SIZE = "BULLET_SIZE";		//setting hidden
	
	private static final String BOMB_COST = "BOMB_COST";
	private static final String BOMB_DAMAGE = "BOMB_DAMAGE";
	private static final String BOMB_SPEED = "BOMB_SPEED";
	private static final String BOMB_DENSITY = "BOMB_DENSITY";
	private static final String BOMB_NUMBER_FRAGMENTS = "BOMB_NUMBER_FRAGMENTS";
	private static final String BOMB_TIME_TO_DETONATE = "BOMB_TIME_TO_DETONATE";
	private static final String BOMB_EXPLOSION_IMPULSE = "BOMB_EXPLOSION_IMPULSE";
	private static final String BOMB_SIZE = "BOMB_SIZE";
	
	private static final String FRAGMENT_COST = "FRAGMENT_COST";
	private static final String FRAGMENT_DAMAGE = "FRAGMENT_DAMAGE";
	private static final String FRAGMENT_DURATION = "FRAGMENT_DURATION";
	private static final String FRAGMENT_IMPULSE = "FRAGMENT_IMPULSE";
	private static final String FRAGMENT_SIZE = "FRAGMENT_SIZE";
	private static final String FRAGMENT_DENSITY = "FRAGMENT_DENSITY";
	
	
	private static final String SEEKER_COST = "SEEKER_COST";
	private static final String SEEKER_DAMAGE = "SEEKER_DAMAGE"; //done
	private static final String SEEKER_DURATION = "SEEKER_DURATION"; //
	private static final String SEEKER_SIZE = "SEEKER_SIZE"; //
	private static final String SEEKER_SPEED = "SEEKER_SPEED"; //
	private static final String SEEKER_DENSITY = "SEEKER_DENSITY"; //
	private static final String SEEKER_INIT_SPEED = "SEEKER_INIT_SPEED"; //
//	private static final String SEEKER_BEFORE_SEEKING = "SEEKER_BEFORE_SEEKING"; //
	private static final String SEEKER_SUPER_SEEKER = "SEEKER_SUPER_SEEKER";
	private static final String SEEKER_TIME_BEFORE_SEEK = "SEEKER_TIME_BEFORE_SEEK";
	private static final String SEEKER_UPDATE_FREQUENCY = "SEEKER_UPDATE_FREQUENCY";	
	private static final String SEEKER_CACHE_SHIPS = "SEEKER_CACHE_SHIPS";
	
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
	
	private static HashMap<String, Setting> settingLookup = new HashMap<>();
	
	public static void setDefault(){
//		List<Setting> settings = new XMLReader().loadSettings("default-settings.config");
//		for(Setting s : settings){
//			settingLookup.put(s.id, s);
//		}
	}
	
	public static int readInt(String id){
		Setting s = settingLookup.get(id);	
		System.out.println("setting " + id + ": " + (s == null ? "null" : s.value));
		if(s == null || !s.validate()) return 0;
		return Integer.valueOf(s.value);
	}
	public static float readFloat(String id){
		Setting s = settingLookup.get(id);		
		System.out.println("setting " + id + ": " + (s == null ? "null" : s.value));
		if(s == null || !s.validate()) return 0;
		return Float.valueOf(s.value);
	}
	public static boolean readBoolean(String id){
		Setting s = settingLookup.get(id);		
		System.out.println("setting " + id + ": " + (s == null ? "null" : s.value));
		if(s == null || !s.validate()) return false;
		return Boolean.valueOf(s.value);
	}
	public static long readLong(String id){
		Setting s = settingLookup.get(id);		
		System.out.println("setting " + id + ": " + (s == null ? "null" : s.value));
		if(s == null || !s.validate()) return 0;
		return Long.valueOf(s.value);
	}
	
//	public static int getNEXT_ID() {
//		return NEXT_ID;
//	}



//	public static void setNEXT_ID(int nEXT_ID) {
//		NEXT_ID = nEXT_ID;
//	}



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
	
	public static float getPlatformFriction(){
		return readFloat(PLATFORM_FRICTION);
	}

	public static float getBulletCost() {
		return readFloat(BULLET_COST);
	}

	public static float getBulletDamage(){
		return readFloat(BULLET_DAMAGE);
	}
	
	public static long getBulletDuration(){
		return readLong(BULLET_DURATION);
	}


	public static float getBulletSpeed() {
		return readFloat(BULLET_SPEED);
	}
	
	public static float getBulletSize(){
		return readFloat(BULLET_SIZE);
	}



	public static float getBulletDensity() {
		return readFloat(BULLET_DENSITY);
	}

	public static float getBombDamage(){
		return readFloat(BOMB_DAMAGE);
	}

	public static float getBombCost() {
		return readFloat(BOMB_COST);
	}

	public static float getBombSize(){
		return readFloat(BOMB_SIZE);
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
	
	public static float getFragmentDamage(){
		return readFloat(FRAGMENT_DAMAGE);
	}
	
	public static long getFragmentDuration(){
		return readLong(FRAGMENT_DURATION);
	}
	
	public static float getFragmentImpulse(){
		return readFloat(FRAGMENT_IMPULSE);
	}

	public static float getFragmentDensity(){
		return readFloat(FRAGMENT_DENSITY);
	}
	
	public static float getFragmentSize(){
		return readFloat(FRAGMENT_SIZE);
	}

	public static float getSeekerCost() {
		return readFloat(SEEKER_COST);
	}

	public static float getSeekerDamage(){
		return readFloat(SEEKER_DAMAGE);
	}
	
	public static long getSeekerDuration(){
		return readLong(SEEKER_DURATION);
	}


	public static float getSeekerUpdateFrequency() {
		return readFloat(SEEKER_UPDATE_FREQUENCY);
	}

	public static boolean getSeekerCacheShips(){
		return readBoolean(SEEKER_CACHE_SHIPS);
	}

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
	
	public static float getSeekerSize(){
		return readFloat(SEEKER_SIZE);
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
