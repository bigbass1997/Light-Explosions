package com.bigbass.lightexplosion.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bigbass.lightexplosion.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		/*
		 * If you intend to change the height, please note that the Options GUI is set respective to the origin, so they will have to be shifted.
		 */
		config.width = 1100;
		config.height = 900;
		
		config.resizable = false;
		
		config.vSyncEnabled = false;
		
		config.title = "Light Explosion";
		
		new LwjglApplication(new Main(), config);
	}
}
