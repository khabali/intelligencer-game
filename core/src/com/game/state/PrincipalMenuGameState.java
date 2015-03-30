package com.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.input.GameInput;

public class PrincipalMenuGameState extends GameStateAdapter {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture background;

	public PrincipalMenuGameState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {

		background = new Texture(Gdx.files.internal("virtual_menu.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, background.getWidth(), background.getHeight());

		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

	}

	@Override
	public void update() {
		handleInput();
		camera.update();
	}

	@Override
	public void draw() {
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
	}

	@Override
	public void handleInput() {

		if (GameInput.getInstance().getEnterButton().isPressed()) {
			gsm.setState(GameStateManager.STATE_LEVEL1);
		}

		if (GameInput.getInstance().getEscapeButton().isPressed()) {
			Gdx.app.exit();
		}
	}
}
