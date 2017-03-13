package com.vormadal.turborocket.models.configs;

import com.badlogic.gdx.math.Vector2;

public class PlatformConfig {

	public Vector2 pointA;
	public Vector2 pointB;
	
	public String toString(){
		return "\t\tPlatform[\n"
				+ "\t\t" + (pointA == null ? new Vector2().toString() : pointA.toString()) + "\n"
				+ "\t\t" + (pointB == null ? new Vector2().toString() : pointB.toString()) + "\n"
				+ "\t\t]\n";
	}
}
