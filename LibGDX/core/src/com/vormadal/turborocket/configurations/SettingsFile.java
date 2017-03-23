package com.vormadal.turborocket.configurations;

import java.util.HashMap;
import java.util.List;

public class SettingsFile {

	private String path;
	private String root;
	public HashMap<String, Setting> settings;
	
	public SettingsFile(String path, String root){
		this.path = path != null ? path : "unknown";
		this.root = root != null ? root : "";
	}
	
	public String getPath(){
		return this.path;
	}
	
	public String getRoot(){
		return this.root;
	}
	
}
