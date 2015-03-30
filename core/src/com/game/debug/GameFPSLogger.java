package com.game.debug;

import com.badlogic.gdx.graphics.FPSLogger;

public final class GameFPSLogger {

	private static final boolean loggin = false;
	private static FPSLogger logger = new FPSLogger();

	public static void log() {
		if (loggin) {
			logger.log();
		}
	}

	private GameFPSLogger() {
	}
}
