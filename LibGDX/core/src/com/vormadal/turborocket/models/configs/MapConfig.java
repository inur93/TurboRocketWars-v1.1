package com.vormadal.turborocket.models.configs;

import java.util.ArrayList;
import java.util.List;

import com.sun.media.jfxmediaimpl.platform.Platform;

public class MapConfig {

	public float width;
	public float height;
	public String backgroundPath;
	public String name;
	public String description;
	
	public List<PlatformConfig> platforms = new ArrayList<>();
	public List<MapObjectConfig> mapObjects = new ArrayList<>();
	
	public String toString(){
		String platforms = "";
		String mapObjects = "";
		for(PlatformConfig p : this.platforms){
			platforms += p.toString();
		}
		for(MapObjectConfig o : this.mapObjects){
			mapObjects += o.toString();
		}
		return "MapConfig{\n"
				+ "width:" + width + ";\n"
				+ "height:" + height + ";\n"
				+ "background:" + backgroundPath + ";\n"
				+"\tPlatforms{\n"
				+ platforms
				+ "}\n"
				+ "\tMapObjects{\n"
				+ mapObjects
				+"}\n"
				+ "}";
	}
}
