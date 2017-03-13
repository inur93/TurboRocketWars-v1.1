package com.vormadal.turborocket.utils;

public interface InputListener {

	public InputConfiguration getInputConfiguration();
	public void setInputConfiguration(InputConfiguration config);
	
	public void left();
	public void right();
	public void boost();
	public void shootNormal();
	public void shootSpecial();
	public void leftRightReleased(); //needed for stopping rotation of ship
	
	/**
	 * meant for menus - but could be used for additional functionality while in game
	 */
	public void esc(); //
	/**
	 * meant for menus - but could be used for additional functionality while in game
	 */
	public void enter();
	
}
