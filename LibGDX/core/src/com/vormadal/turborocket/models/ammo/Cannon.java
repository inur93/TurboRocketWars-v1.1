package com.vormadal.turborocket.models.ammo;

import static com.vormadal.turborocket.utils.ConfigUtil.readFloat;
import static com.vormadal.turborocket.utils.PropKeys.*;

import com.badlogic.gdx.math.Vector2;
import com.vormadal.turborocket.WorldEntitiesController;

public class Cannon<T extends Ammo> {

	private WorldEntitiesController entitiesController;
	private AmmoFactory<T> ammoFactory;

	private float cannonDirection = 1;
	private Vector2[] cannonPos;

	private float maxAmmo = getShipMaxAmmo();
	private float ammo = maxAmmo;

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
		float p0y = 10;
		float p1y = 14;
		switch (cannonNumber) {
		case 1:
			cannonPos = new Vector2[] { new Vector2(0, p1y) };
			break;
		case 2:
			cannonPos = new Vector2[] { new Vector2(2, p0y), new Vector2(-2, p0y) };
			break;
		case 3:
		default:
			cannonPos = new Vector2[] { new Vector2(2.5f, p0y), new Vector2(0, p1y), new Vector2(-2.5f, p0y) };
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
//			System.out.println("dir: " + dir + "; cannondir: " + cannonDirection);
			ammo -= ammoFactory.getAmmoCost();
			ammoFactory.factory(v0, pos, dir.cpy().scl(cannonDirection), entitiesController);

		}
		System.out.println("ammo left: " + ammo);
		return true;
	}
	
	public float getAmmoCount(){
		return this.ammo;
	}
	
	public float getMaxAmmoCount(){
		return this.maxAmmo;
	}

	public void regen(float regenAmmo) {
		this.ammo += regenAmmo;
		if (this.ammo > this.maxAmmo)
			this.ammo = this.maxAmmo;
	}

	private boolean hasAmmo() {
		return this.ammoFactory.getAmmoCost()*this.cannonPos.length <= this.ammo;
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
