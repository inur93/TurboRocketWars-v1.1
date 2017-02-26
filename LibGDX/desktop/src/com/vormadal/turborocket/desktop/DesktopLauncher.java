package com.vormadal.turborocket.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vormadal.turborocket.TurboRocketWarsGame;

import test.MenuScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		config.width = config.width*2;
		config.height = config.height*2;
//		config.use
//		new LwjglApplication(new TurboRocketWarsGame(), config);
		new LwjglApplication(new Game() {
			
			@Override
			public void create() {
				setScreen(new MenuScreen(this));
			}
		}, config);
	}
}
