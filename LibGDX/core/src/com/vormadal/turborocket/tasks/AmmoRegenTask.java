package com.vormadal.turborocket.tasks;

import com.vormadal.turborocket.models.Ship;

public class AmmoRegenTask extends Regenerator{

	private Ship<?,?> ship;
	public AmmoRegenTask(Ship<?,?> ship) {
		super(ship.getAmmoRegenFrequency());
		this.ship = ship;
	}

	@Override
	public void regen() {
		ship.regenerateAmmo();
	}

}
