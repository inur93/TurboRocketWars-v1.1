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

public class Ship<A1 extends Ammo, A2 extends Ammo> implements WorldEntity{
	private Vector2[] shapeVectors = new Vector2[] { 
			new Vector2(-7f, -5f).scl(readFloat(SHIP_SCALE)),
			new Vector2(0f, 0f).scl(readFloat(SHIP_SCALE)),
			new Vector2(7f, -5f).scl(readFloat(SHIP_SCALE)), 
			new Vector2(0f, 9f).scl(readFloat(SHIP_SCALE)) };
	
	
	private WorldEntitiesController entitiesController;

	private Vector2 boostVec = new Vector2(0, readFloat(SHIP_BOOST_IMPULSE));

	private int lives = readInt(SHIP_LIVES);
	private final double maxHitPoints = readDouble(SHIP_MAX_HP);
	private double hitPoints = maxHitPoints;

	private String id;
	private String type;
	private volatile Body body;
	private volatile ActorShip actor;
	private double regenHP = readDouble(SHIP_REGEN_HP);
	private int regenAmmo = readInt(SHIP_REGEN_AMMO);
	private final float rotationSpeed = readFloat(SHIP_ROTATION_SPEED);
	
	private Cannon<A1> cannonStd;
	private Cannon<A2> cannon1;

	private boolean isRotationLocked = false;
	private boolean isWinner = false;

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
		
		this.entitiesController = entitiesController;
		
		this.spawnPoint = position;
		this.cannonStd = stdCannon;
		this.cannon1 = specialCannon;
		entitiesController.createWhenReady(this);
	}

	public Actor create(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(spawnPoint.cpy());
		
		body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1/readFloat(SHIP_SCALE);
		fixtureDef.friction = 0.3f;

		B2Separator.seperate(body, fixtureDef, shapeVectors, 1);
		this.actor = new ActorShip(this);
		return this.actor;
	}
	
	public Actor destroy(World world){
		world.destroyBody(body);
		return this.actor;
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

	public String getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}

	public Body getBody() {
		return this.body;
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
		//body.setAngularVelocity(0); // easier to fly

		body.applyLinearImpulse(boostVec.cpy().rotateRad(body.getAngle()), body.getPosition(), true);
		//		body.setLinearVelocity(boostVec.cpy().rotate(angle).add(body.getLinearVelocity()));
//		System.out.println("boost dir: " + boostVec.cpy().rotate(angle) + " angle: " + angle);

	}

	public void left() {
		if (this.lives <= 0)
			return;
		if (isRotationLocked)
			return;
		//		body.setAngularVelocity(body.getAngularVelocity()+rotationSpeed);
		//		body.applyTorque(10, true);
		//		if(body.getAngularVelocity() < 0) {
		//			body.setAngularVelocity(0);
		//		}else{
		body.applyAngularImpulse(rotationSpeed, true);
		//		}
//		System.out.println("left: " + body.getAngularVelocity() + ";" + body.getAngle());

	}

	public void right() {
		if (this.lives <= 0)
			return;
		if (isRotationLocked)
			return;


		//		body.setAngularVelocity(body.getAngularVelocity()-rotationSpeed);
		//		body.applyTorque(10, true);
		//		if(body.getAngularVelocity() > 0){
		//			body.setAngularVelocity(0);
		//		}else{
		body.applyAngularImpulse(-rotationSpeed, true);
		//		}
//		System.out.println("right: " + body.getAngularVelocity() + ";" + body.getAngle());
	}

	public void stopRotation(){
		body.setAngularVelocity(0);
	}

	public double getMaxHitPoints() {
		return this.maxHitPoints;
	}

	public double getCurHitPoints() {
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
			this.entitiesController.destroyWhenReady(this);
			this.lives = 0;
			this.hitPoints = 0;
			return;
		}
		respawn();
	}

	private void respawn() {
		this.entitiesController.destroyWhenReady(this);
		this.entitiesController.createWhenReady(this);
		this.hitPoints = maxHitPoints;
		cannonStd.regen(Integer.MAX_VALUE);
		cannon1.regen(Integer.MAX_VALUE);
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
}
