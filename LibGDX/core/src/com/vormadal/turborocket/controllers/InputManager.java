package com.vormadal.turborocket.controllers;

import static com.vormadal.turborocket.utils.InputConfiguration.InputCommands.BOOST;
import static com.vormadal.turborocket.utils.InputConfiguration.InputCommands.FIRE_NORMAL;
import static com.vormadal.turborocket.utils.InputConfiguration.InputCommands.FIRE_SPECIAL;
import static com.vormadal.turborocket.utils.InputConfiguration.InputCommands.LEFT;
import static com.vormadal.turborocket.utils.InputConfiguration.InputCommands.RIGHT;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Timer;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputConfiguration.InputCommands;
import com.vormadal.turborocket.utils.InputListener;

public class InputManager extends Timer.Task implements InputProcessor{

	private List<InputListener> listeners;
	private List<InputProcessor> eventListeners = new ArrayList<>();
	private Timer timer;

	public enum INPUT_MODE {SIMPLE, GAME};
	private INPUT_MODE mode;

	public InputManager(List<InputListener> listeners, INPUT_MODE mode) {
		this.listeners = listeners;
		this.mode = mode;
		if(mode.equals(INPUT_MODE.GAME)){
			timer = new Timer();
			timer.scheduleTask(this, 0f, 1.0f/60f);
		}
	}
	@Override
	public void run() {
		processInputs();
	}

	public void addInputProcessor(InputProcessor listener){
		eventListeners.add(listener);
	}
	
	@Override
	public synchronized void cancel() {
		if(mode.equals(INPUT_MODE.GAME)){
			super.cancel();
			timer.clear();
		}
	}

	private void processInputs(){
		for(InputListener listener : listeners){
			InputConfiguration c = listener.getInputConfiguration();

			if(c.isDown(LEFT)) listener.left();
			if(c.isDown(RIGHT)) listener.right();
			if(c.isDown(BOOST)) listener.boost();
			if(c.isDown(FIRE_NORMAL)) listener.shootNormal();
			if(c.isDown(FIRE_SPECIAL)) listener.shootSpecial();
			if(c.isDown(InputCommands.ENTER)) listener.enter();
			if(c.isDown(InputCommands.ESC)) listener.esc();
			if(!(c.isDown(LEFT) || c.isDown(RIGHT))) listener.leftRightReleased();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		for(InputListener listener : listeners){
			listener.getInputConfiguration().down(keycode);
		}
		if(mode.equals(INPUT_MODE.SIMPLE)){
			processInputs();
		}
		for(InputProcessor listener : eventListeners){
			listener.keyDown(keycode);
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		for(InputListener listener : listeners){
			listener.getInputConfiguration().up(keycode);
		}
		for(InputProcessor listener : eventListeners){
			listener.keyUp(keycode);
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		for(InputProcessor listener : eventListeners){
			listener.keyTyped(character);
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for(InputProcessor listener : eventListeners){
			listener.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for(InputProcessor listener : eventListeners){
			listener.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for(InputProcessor listener : eventListeners){
			listener.touchDragged(screenX, screenY, pointer);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		for(InputProcessor listener : eventListeners){
			listener.mouseMoved(screenX, screenY);
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		for(InputProcessor listener : eventListeners){
			listener.scrolled(amount);
		}
		return false;
	}
}
