package com.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.game.debug.GameFPSLogger;
import com.game.debug.GameLogger;
import com.game.input.GameInput;
import com.game.input.InputHandler;
import com.game.state.GameStateManager;

public class MyGdxGame extends ApplicationAdapter {

	private final String tag = getClass().getName();

	private GameStateManager gsm;
	private InputHandler inputHandler;

	@Override
	public void create() {

		// LOG LEVEL
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		GameLogger.debug(tag, "Initialisation des logs : LEVEL DEBUG");

		//
		this.inputHandler = new InputHandler();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(inputHandler);
		multiplexer.addProcessor(new GestureDetector(inputHandler));
		Gdx.input.setInputProcessor(multiplexer);

		// Initialisation du game state manager
		gsm = new GameStateManager();

		GameLogger.debug(tag, "Game created");
	}

	@Override
	public void render() {

		// clear screen
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update();
		gsm.draw();

		// update inpute informations
		GameInput.getInstance().update(Gdx.graphics.getDeltaTime());

		// FPSLogger
		GameFPSLogger.log();
	}

	@Override
	public void resize(int width, int height) {
		gsm.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
		gsm.pause();
		GameLogger.debug(tag, "Game paused");
	}

	@Override
	public void resume() {
		super.resume();
		gsm.resume();
		GameLogger.debug(tag, "Game resumed");
	}

	@Override
	public void dispose() {
		super.dispose();
		gsm.dispose();
		GameLogger.debug(tag, "Game diposed");
	}

}
