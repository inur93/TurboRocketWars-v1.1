package com.vormadal.turborocket.utils;

import java.util.HashMap;

import com.badlogic.gdx.Input;

public class InputConfiguration {

	public enum InputType {WASD, ARROWS};
	public enum InputCommands {BOOST, LEFT, RIGHT, FIRE_NORMAL, FIRE_SPECIAL, ESC, ENTER}
	
	private HashMap<Integer, InputCommands> keyMappings = new HashMap<>();
	private HashMap<InputCommands, Boolean> keyValues = new HashMap<>();
	
	public InputConfiguration(InputType type){
		switch(type){
		case ARROWS:
			keyMappings.put(Input.Keys.UP, InputCommands.BOOST);
			keyMappings.put(Input.Keys.LEFT, InputCommands.LEFT);
			keyMappings.put(Input.Keys.RIGHT, InputCommands.RIGHT);
			keyMappings.put(Input.Keys.DOWN, InputCommands.FIRE_NORMAL);
			keyMappings.put(Input.Keys.CONTROL_RIGHT, InputCommands.FIRE_SPECIAL);
			keyMappings.put(Input.Keys.ESCAPE, InputCommands.ESC);
			keyMappings.put(Input.Keys.ENTER, InputCommands.ENTER);
			break;
		case WASD:
			keyMappings.put(Input.Keys.W, InputCommands.BOOST);
			keyMappings.put(Input.Keys.A, InputCommands.LEFT);
			keyMappings.put(Input.Keys.D, InputCommands.RIGHT);
			keyMappings.put(Input.Keys.S, InputCommands.FIRE_NORMAL);
			keyMappings.put(Input.Keys.CONTROL_LEFT, InputCommands.FIRE_SPECIAL);
			break;
		}
	}
	
	public void down(int key){
		InputCommands cmd = keyMappings.get(key);
		if(cmd != null){
			set(cmd, true);
		}
	}
	
	public void up(int key){
		InputCommands cmd = keyMappings.get(key);
		if(cmd != null){
			set(cmd, false);
		}
	}
	
	private void set(InputCommands cmd, boolean value){
		keyValues.put(cmd, value);
	}
	
	public boolean isDown(InputCommands cmd){
		Boolean val = keyValues.get(cmd);
		if(val == null) return false;
		return val;
	}
	
}
