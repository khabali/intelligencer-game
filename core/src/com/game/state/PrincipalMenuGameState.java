package com.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.input.GameInput;
import com.game.resources.AssetsManager;

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

		// load assets
		AssetsManager.load();
		while (!AssetsManager.manager.update()) {
			Gdx.app.debug(tag, "Loading assets : "+ (int) (AssetsManager.manager.getProgress() * 100) + " %");
		}
		
		Gdx.app.debug(tag, "Loading assets : "+ (int) (AssetsManager.manager.getProgress() * 100) + " %");

		background = AssetsManager.manager.get(AssetsManager.MENU_BG,
				Texture.class);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, background.getWidth(), background.getHeight());

		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

	}

	@Override
	public void update() {
		handleInput();
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

	@Override
	public void dispose() {
		super.dispose();
		AssetsManager.dispose();
	}
}
