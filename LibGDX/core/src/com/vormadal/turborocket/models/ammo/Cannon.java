package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.vormadal.turborocket.WorldEntitiesController;

import static com.vormadal.turborocket.utils.PropKeys.*;
import static com.vormadal.turborocket.utils.ConfigUtil.*;

public class Cannon<T extends Ammo> {

	private WorldEntitiesController entitiesController;
	private AmmoFactory<T> ammoFactory;

	private float cannonDirection = 1;
	private Vector2[] cannonPos;

	private int maxAmmo = readInt(SHIP_MAX_AMMO);
	private int ammo = maxAmmo;

	private long reloadTime = 100; // msec default
	private long timeOfLastShot = -1;

	public Cannon(WorldEntitiesController entitiesController, AmmoFactory<T> shotFactory, int cannonNumber,
			boolean cannonPointForward, long reloadTime) {
		this.entitiesController = entitiesController;
		this.ammoFactory = shotFactory;
		if (reloadTime > 0)
			this.reloadTime = reloadTime;
		if (!cannonPointForward)
			cannonDirection = -1;

		cannonPos = new Vector2[cannonNumber];
		switch (cannonNumber) {
		case 1:
			cannonPos = new Vector2[] { new Vector2(0, 7) };
			break;
		case 2:
			cannonPos = new Vector2[] { new Vector2(2, 5), new Vector2(-2, 5) };
			break;
		case 3:
		default:
			cannonPos = new Vector2[] { new Vector2(2.5f, 7), new Vector2(0, 10), new Vector2(-2.5f, 7) };
			break;
		}

		if (!cannonPointForward) {
			for (int i = 0; i < cannonPos.length; i++)
				cannonPos[i] = cannonPos[i].scl(-1);
		}
	}

	/**
	 * 
	 * @param p0
	 *            start position of projectile
	 * @param v0
	 *            start velocity of projectile - this would be the velocity of
	 *            the ship that affects the projectile.
	 * @param angle
	 *            the angleof the direction the projectile is being fired.
	 * @return true if fired successfully. False if reload has not finished or
	 *         if there is not enough ammo.
	 */
	public synchronized boolean fire(Vector2 p0, Vector2 v0, float angle) {
		if (!isLoaded())
			return false;
		if (!hasAmmo())
			return false;
		Vector2 dir = new Vector2(0, 1).rotateRad(angle);

		for (int i = 0; i < cannonPos.length; i++) {
			Vector2 pos = p0.cpy().add(cannonPos[i].cpy().rotateRad(angle));
			System.out.println("dir: " + dir + "; cannondir: " + cannonDirection);
			ammoFactory.factory(v0, pos, dir.cpy().scl(cannonDirection), entitiesController);

		}

		return true;
	}
	
	public int getAmmoCount(){
		return this.ammo;
	}
	
	public int getMaxAmmoCount(){
		return this.maxAmmo;
	}

	public void regen(int regenAmmo) {
		this.ammo += regenAmmo;
		if (this.ammo > this.maxAmmo)
			this.ammo = this.maxAmmo;
	}

	private boolean hasAmmo() {
		return this.ammoFactory.getAmmoCost() <= this.ammo;
	}

	private boolean isLoaded() {
		long newTime = System.currentTimeMillis();
		if (newTime - timeOfLastShot > this.reloadTime) {
			timeOfLastShot = newTime;
			return true;
		}
		return false;
	}
	

}
