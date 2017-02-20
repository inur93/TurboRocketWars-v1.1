package com.vormadal.turborocket;

import java.util.List;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.vormadal.turborocket.models.Player;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.utils.InputConfiguration;
import static com.vormadal.turborocket.utils.InputConfiguration.InputCommands.*;

public class InputManager extends Timer.Task implements InputProcessor{

//	private Ship<?,?> ship, ship2;
//	private boolean left, right, down, up;
//	private boolean a, d, s, w;
	private List<Player> players;
	
	public InputManager(List<Player> players) {
		this.players = players;
	}
	@Override
	public void run() {
		for(Player p : players){
			InputConfiguration c = p.getInputConfig();
			Ship<?,?> s = p.getShip();
			
			if(c.isDown(LEFT)) s.left();
			if(c.isDown(RIGHT)) s.right();
			if(c.isDown(BOOST)) s.boost();
			if(c.isDown(FIRE_NORMAL)) s.shootNormal();;
			if(c.isDown(FIRE_SPECIAL)) s.shootSpecial();;
			if(!(c.isDown(LEFT) || c.isDown(RIGHT))) s.stopRotation();;
			
		}
	}
	@Override
	public boolean keyDown(int keycode) {
		for(Player p : players){
			p.getInputConfig().down(keycode);
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		for(Player p : players){
			p.getInputConfig().up(keycode);
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
