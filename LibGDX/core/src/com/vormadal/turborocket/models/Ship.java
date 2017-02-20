package com.vormadal.turborocket.models;

import static com.vormadal.turborocket.utils.ConfigUtil.*;
import static com.vormadal.turborocket.utils.PropKeys.*;
import static java.lang.Math.toDegrees;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.WorldEntitiesController;
import com.vormadal.turborocket.models.actors.ActorShip;
import com.vormadal.turborocket.models.ammo.Ammo;
import com.vormadal.turborocket.models.ammo.Cannon;
import com.vormadal.turborocket.tasks.AmmoRegenTask;
import com.vormadal.turborocket.tasks.HPRegenTask;
import com.vormadal.turborocket.utils.B2Separator;
import com.vormadal.turborocket.utils.PropKeys;

public class Ship<A1 extends Ammo, A2 extends Ammo> implements WorldEntity{
	private Vector2[] shapeVectors = new Vector2[] { 
			new Vector2(-7f, -5f).scl(readFloat(SHIP_SCALE)),
			new Vector2(0f, 0f).scl(readFloat(SHIP_SCALE)),
			new Vector2(7f, -5f).scl(readFloat(SHIP_SCALE)), 
			new Vector2(0f, 9f).scl(readFloat(SHIP_SCALE)) };
	
	
	private WorldEntitiesController entitiesController;

	private Vector2 boostVec = new Vector2(0, readFloat(SHIP_BOOST_IMPULSE));

	private int lives = readInt(SHIP_LIVES);
	private final float maxHitPoints = readFloat(SHIP_MAX_HP);
	private float hitPoints = maxHitPoints;

	private String id;
	private String type;
	private volatile Body body;
	private volatile ActorShip actor;
	private float regenHP = readFloat(SHIP_REGEN_HP);
	private int regenAmmo = readInt(SHIP_REGEN_AMMO);
	private final float rotationSpeed = readFloat(SHIP_ROTATION_SPEED);
	
	private Cannon<A1> cannonStd;
	private Cannon<A2> cannon1;

	private boolean isRotationLocked = false;
	private boolean isWinner = false;
	//initial value is true due to the fact that the body is not created right away
	private boolean isDestroyed = true; 
	private Vector2 spawnPoint;

	private long hpRegenFrequence = readLong(SHIP_HP_REGEN_FREQUENCY); // msec until next regen
	private long ammoRegenFrequence = readLong(SHIP_AMMO_REGEN_FREQUENCY);

	private HPRegenTask hpRegenerator = null;
	private AmmoRegenTask ammoRegen = null;

	public Ship(
			WorldEntitiesController entitiesController,
			Vector2 position, 
			Cannon<A1> stdCannon, 
			Cannon<A2> specialCannon) {
		this.id = String.valueOf(PropKeys.nextId());
		this.entitiesController = entitiesController;
		
		this.spawnPoint = position.cpy();
		this.cannonStd = stdCannon;
		this.cannon1 = specialCannon;
		this.actor = new ActorShip(this);
		entitiesController.createWhenReady(this);
	}

	public synchronized Actor create(World world) {
		if(!isDestroyed()) return null;
		
		System.out.println("create: " + this.id);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(spawnPoint.cpy());
		
		body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1/readFloat(SHIP_SCALE);
		fixtureDef.friction = 0.3f;

		B2Separator.seperate(body, fixtureDef, shapeVectors, 1);
		body.setUserData(new WorldEntityData(this));
		isDestroyed = false;
		return this.actor;
	}
	
	public synchronized Actor destroy(World world){
		if(isDestroyed()) return null;
		System.out.println("destroy: " + id);
		
		world.destroyBody(body);
		isDestroyed = true;
		return this.actor;
	}

	public boolean isDestroyed(){
		return this.isDestroyed;
	}
	public void setCannonNormal(Cannon<A1> cannon) {
		this.cannonStd = cannon;
	}

	public void setCannonSpecial(Cannon<A2> cannon) {
		this.cannon1 = cannon;
	}

	public void lockRotation() {
		isRotationLocked = true;
	}

	public void unlockRotation() {
		isRotationLocked = false;
	}

//	public String getId() {
//		return this.id;
//	}

	public String getType() {
		return this.type;
	}

	public Body getBody() {
		return this.body;
	}

	public Cannon<?> getNormalCannon(){
		return this.cannonStd;
	}
	
	public Cannon<?> getSpecialCannon(){
		return this.cannon1;
	}


	public void shootNormal() {
		if (this.lives <= 0) return;
		boolean success = cannonStd.fire(this.getBody().getPosition().cpy(), 
					this.getBody().getLinearVelocity().cpy(), 
					this.getBody().getAngle());
		
	}

	public void shootSpecial() {
		if (this.lives <= 0) return;
		boolean success = cannon1.fire(this.getBody().getPosition().cpy(), 
					this.getBody().getLinearVelocity().cpy(), 
					this.getBody().getAngle());
	}

	
	public void boost() {
		if (this.lives <= 0)
			return;	
		body.applyLinearImpulse(boostVec.cpy().rotateRad(body.getAngle()), body.getPosition(), true);

	}

	public void left() {
		if (this.lives <= 0)
			return;
		if (isRotationLocked)
			return;
		body.applyAngularImpulse(rotationSpeed, true);

	}

	public void right() {
		if (this.lives <= 0)
			return;
		if (isRotationLocked)
			return;

		body.applyAngularImpulse(-rotationSpeed, true);
	}

	public void stopRotation(){
		if(body == null) return;
		body.setAngularVelocity(0);
	}

	public float getMaxHitPoints() {
		return this.maxHitPoints;
	}

	public float getCurHitPoints() {
		return this.hitPoints;
	}

	public void regenerateHP() {
		if (hpRegenerator == null || hpRegenerator.hasStopped()) {
			hpRegenerator = new HPRegenTask(this);
		}
		if (hitPoints < maxHitPoints) {
			this.hitPoints += regenHP;
			if (hitPoints > maxHitPoints)
				hitPoints = maxHitPoints;
		}
	}

	public void regenerateAmmo() {
		if (ammoRegen == null || ammoRegen.hasStopped()) {
			ammoRegen = new AmmoRegenTask(this);
		}
		cannonStd.regen(regenAmmo);
		cannon1.regen(regenAmmo);
	}

	public void stopAmmoRegen() {
		if (ammoRegen != null) {
			ammoRegen.stop();
			ammoRegen = null;
		}
	}

	public void stopHPRegen() {
		if (hpRegenerator != null) {
			hpRegenerator.stop();
			hpRegenerator = null;
		}
	}

	public long getHPRegenFrequency() {
		return this.hpRegenFrequence;
	}

	public long getAmmoRegenFrequency() {
		return this.ammoRegenFrequence;
	}

	public int getLives() {
		return this.lives;
	}

	public void attack(double dmg) {
		this.hitPoints -= dmg;
	}

	public Vector2 getSpawPoint() {
		return this.spawnPoint.cpy();
	}

	public void setSpawnPoint(Vector2 point) {
		this.spawnPoint = point.cpy();
		respawn();
	}

	public void die() {
		this.lives -= 1;
		if (this.lives <= 0) {
			this.lives = 0;
			this.hitPoints = 0;
			return;
		}
		respawn();
	}

	private void respawn() {
		this.hitPoints = maxHitPoints;
		cannonStd.regen(Integer.MAX_VALUE);
		cannon1.regen(Integer.MAX_VALUE);
		this.entitiesController.destroyWhenReady(this);
		this.entitiesController.createWhenReady(this);
//		 this.body.getAngle()
	}

	public boolean isWinner() {
		return this.isWinner;
	}

	public void setShipGameState() {

	}

	public void setWinner() {
		this.isWinner = true;
	}

	public Object getHPRegenRatio() {
		return this.regenHP;
	}

	public String getId() {
		return this.id;
	}
}
