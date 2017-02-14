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
import com.vormadal.turborocket.models.ammo.Ammo;
import com.vormadal.turborocket.models.ammo.Cannon;
import com.vormadal.turborocket.tasks.AmmoRegenTask;
import com.vormadal.turborocket.tasks.HPRegenTask;
import com.vormadal.turborocket.utils.B2Separator;

public class Ship<A1 extends Ammo, A2 extends Ammo> {
	private Vector2[] shapeVectors = new Vector2[] { 
			new Vector2(-7f, -5f).scl(readFloat(SHIP_SCALE)),
			new Vector2(0f, 0f).scl(readFloat(SHIP_SCALE)),
			new Vector2(7f, -5f).scl(readFloat(SHIP_SCALE)), 
			new Vector2(0f, 9f).scl(readFloat(SHIP_SCALE)) };

	private Vector2 boostVec = new Vector2(0, readFloat(SHIP_BOOST_IMPULSE));

	private int lives = readInt(SHIP_LIVES);
	private final double maxHitPoints = readDouble(SHIP_MAX_HP);
	private double hitPoints = maxHitPoints;

	private String id;
	private String type;
	private volatile Body body;
	private volatile World world;
	private double regenHP = readDouble(SHIP_REGEN_HP);
	private int regenAmmo = readInt(SHIP_REGEN_AMMO);
	private final int maxAmmo = readInt(SHIP_MAX_AMMO);
	private final float rotationSpeed = readFloat(SHIP_ROTATION_SPEED);
	private int ammo = maxAmmo;
	private Cannon<A1> cannonStd;
	private Cannon<A2> cannon1;

	private boolean isRotationLocked = false;
	private boolean isWinner = false;

	private Vector2 spawnPoint;

	private long timeOfLastShot = 0;
	private long timeOfLastRegen = 0;
	private long hpRegenFrequence = readLong(SHIP_HP_REGEN_FREQUENCY); // msec until next regen
	private long ammoRegenFrequence = readLong(SHIP_AMMO_REGEN_FREQUENCY);

	private HPRegenTask hpRegenerator = null;
	private AmmoRegenTask ammoRegen = null;

	public Ship(World world, Vector2 position, Cannon<A1> stdCannon, Cannon<A2> specialCannon) {
		this.spawnPoint = position;
		this.world = world;
		this.body = getNewBody(position.cpy(), world);
		this.cannonStd = stdCannon;
		this.cannon1 = specialCannon;
	}

	private Body getNewBody(Vector2 pos, World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;

		System.out.println("pos: " + pos);
		bodyDef.position.set(pos.cpy());
		Body body = null;

		body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1/readFloat(SHIP_SCALE);
		fixtureDef.friction = 0.3f;

		B2Separator.seperate(body, fixtureDef, shapeVectors, 1);
		return body;
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

	public int getMaxAmmo() {
		return this.maxAmmo;
	}

	public int getAmmoCount() {
		return this.ammo;
	}

	public String getType() {
		return this.type;
	}

	public Body getBody() {
		return this.body;
	}

	private boolean checkTime(long tolerance) {
		long newTime = System.currentTimeMillis();
		if (newTime - timeOfLastShot > tolerance) {
			timeOfLastShot = newTime;
			return true;
		}
		return false;
	}

	public void shootNormal() {
		if (this.lives <= 0) return;
		if (checkTime(cannonStd.getReloadTime())){
			cannonStd.fire(this.getBody().getPosition(), 
					this.getBody().getLinearVelocity().cpy(), 
					this.getBody().getAngle());
		}
	}

	public void shootSpecial() {
		if (this.lives <= 0) return;
		if (checkTime(cannon1.getReloadTime()))
			cannon1.fire(this.getBody().getPosition(), 
					this.getBody().getLinearVelocity().cpy(), 
					this.getBody().getAngle());
	}

	public boolean hasAmmo(int amount) {
		if (this.ammo >= amount)
			return true;
		return false;
	}

	public void boost() {
		if (this.lives <= 0)
			return;
		float angle = (float)(toDegrees(body.getAngle()));		
		//body.setAngularVelocity(0); // easier to fly

		body.applyLinearImpulse(boostVec.cpy().rotate(angle), body.getPosition(), true);
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
		if (ammo < maxAmmo) {
			this.ammo += regenAmmo;
			if (ammo > maxAmmo)
				ammo = maxAmmo;
		}
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
			world.destroyBody(getBody());
			this.lives = 0;
			this.hitPoints = 0;
			return;
		}
		respawn();
	}

	private void respawn() {
		world.destroyBody(body);
		body = getNewBody(spawnPoint.cpy(), world);
		this.hitPoints = maxHitPoints;
		this.ammo = maxAmmo;
	}

	public boolean isWinner() {
		return this.isWinner;
	}

	public void setShipGameState() {

	}

	public void setWinner() {
		this.isWinner = true;
	}

	public void useAmmo(int ammoCost) {
		this.ammo -= ammoCost;
	}

	public Object getHPRegenRatio() {
		return this.regenHP;
	}
}
