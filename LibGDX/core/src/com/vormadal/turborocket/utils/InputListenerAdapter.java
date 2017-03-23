package com.vormadal.turborocket.utils;

public class InputListenerAdapter implements InputListener {

	private InputConfiguration config;
	public InputListenerAdapter(InputConfiguration config) {
		this.config = config;
	}
	@Override
	public InputConfiguration getInputConfiguration() {
		return this.config;
	}

	@Override
	public void setInputConfiguration(InputConfiguration config) {
		this.config = config;
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void right() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void boost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shootNormal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shootSpecial() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftRightReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void esc() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter() {
		// TODO Auto-generated method stub
		
	}

}
