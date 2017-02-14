package com.vormadal.turborocket.tasks;

import com.vormadal.turborocket.models.Ship;

public class HPRegenTask extends Regenerator {

	private Ship<?,?> ship;
	public HPRegenTask(Ship<?,?> ship) {
		super(ship.getHPRegenFrequency());
		this.ship = ship;
	}

	@Override
	public void regen() {
		ship.regenerateHP();
	}

}
