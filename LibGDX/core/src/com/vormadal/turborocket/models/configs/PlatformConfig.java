package com.vormadal.turborocket.models.configs;

import com.badlogic.gdx.math.Vector2;

public class PlatformConfig {

	public Vector2 pointA;
	public Vector2 pointB;
	
	public String toString(){
		return "\t\tPlatform[\n"
				+ "\t\t" + pointA.toString() + "\n"
				+ "\t\t" + pointB.toString() + "\n"
				+ "\t\t]\n";
	}
}
