package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public interface AmmoFactory  <T extends Ammo>{
	T factory(Vector2 initialVel, Vector2 pos, Vector2 dir, World world);
	int getAmmoCost();
}