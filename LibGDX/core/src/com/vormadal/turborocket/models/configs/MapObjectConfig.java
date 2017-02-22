package com.vormadal.turborocket.models.configs;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class MapObjectConfig {

	public List<Vector2> vertices = new ArrayList<>();
	
	public String toString(){
		String vertices = "";
		for(Vector2 v : this.vertices){
			vertices += "\t\t"+ v.toString() + "\n";
		}
		return "\t\tMapObject[\n"
				+ vertices
				+ "\t\t]\n";
	}
}
