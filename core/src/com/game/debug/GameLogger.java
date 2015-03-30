package com.game.debug;

import com.badlogic.gdx.Gdx;

public final class GameLogger {

	public static void debug(String tag, String message) {
		Gdx.app.debug(tag, message);
	}

	public static void info(String tag, String message) {
		Gdx.app.log(tag, message);
	}

	public static void error(String tag, String message) {
		Gdx.app.error(tag, message);
	}

	public static void error(String tag, String message, Throwable exception) {
		Gdx.app.error(tag, message, exception);
	}

	private GameLogger() {

	}

}
