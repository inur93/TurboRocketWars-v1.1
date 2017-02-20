package com.vormadal.turborocket.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vormadal.turborocket.TurboRocketWarsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = config.width*2;
		config.height = config.height*2;
		new LwjglApplication(new TurboRocketWarsGame(), config);
	}
}
