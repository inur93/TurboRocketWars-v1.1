package com.vormadal.turborocket;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.vormadal.turborocket.models.Ship;

public class InputManager extends Timer.Task implements InputProcessor{

	private Ship<?,?> ship, ship2;
	private boolean left, right, down, up;
	private boolean a, d, s, w;
	
	
	public InputManager(Ship<?, ?> ship, Ship<?, ?> ship2) {
		this.ship = ship;
		this.ship2 = ship2;
	}
	@Override
	public void run() {
		if(left) ship.left();
		if(right) ship.right();
		if(up) ship.boost();
		if(down) ship.shootNormal();
		
		if(!(left || right)){
			ship.stopRotation();
		}
		
		if(a) ship2.left();
		if(d) ship2.right();
		if(w) ship2.boost();
		if(s) ship2.shootNormal();
		
		if(!(a || d)){
			ship2.stopRotation();
		}
	}
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case Input.Keys.RIGHT:
			right = true;
			break;
		case Input.Keys.LEFT:
			left = true;
			break;
		case Input.Keys.UP:
			up = true;
			break;
		case Input.Keys.DOWN:
			down = true;
			break;
		case Input.Keys.A:
			a = true;
			break;
		case Input.Keys.D:
			d = true;
			break;
		case Input.Keys.W:
			w = true;
			break;
		case Input.Keys.S:
			s = true;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Input.Keys.RIGHT:
			right = false;
			break;
		case Input.Keys.LEFT:
			left = false;
			break;
		case Input.Keys.UP:
			up = false;
			break;
		case Input.Keys.DOWN:
			down = false;
			break;
		case Input.Keys.A:
			a = false;
			break;
		case Input.Keys.D:
			d = false;
			break;
		case Input.Keys.W:
			w = false;
			break;
		case Input.Keys.S:
			s = false;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
