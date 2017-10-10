package com.vormadal.turborocket.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.controllers.TurboRocketWarsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		ConfigManager mgr = ConfigManager.instance();
		
		config.fullscreen = mgr.useFullscreen();
		config.width = mgr.getScreenWidth();
		config.height = mgr.getScreenHeight();
//		config.use
		new LwjglApplication(new TurboRocketWarsGame(mgr), config);
		
//		new LwjglApplication(new Game() {
//			
//			@Override
//			public void create() {
//				setScreen(new MenuScreen(this));
//			}
//		}, config);
	}
}
