package com.knightlight.tribal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.knightlight.tribal.TribalCore;

public class DesktopLauncher {

	public static void main(String[] argv) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tribal";
		config.width = 640;//800;
		config.height = 960;//480;
		config.samples = 4;
		config.depth = 0;
		config.vSyncEnabled = true;

		config.fullscreen = false;
		new LwjglApplication(new TribalCore(), config);
	}

}
