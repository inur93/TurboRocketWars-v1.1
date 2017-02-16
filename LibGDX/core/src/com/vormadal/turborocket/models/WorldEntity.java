package com.vormadal.turborocket.models;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public interface WorldEntity {

	public Actor create(World world);
	public Actor destroy(World world);
}
