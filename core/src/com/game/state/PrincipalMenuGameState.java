package com.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.input.GameInput;
import com.game.resources.GameAssetsManager;
import com.game.resources.GameRessources;

public class PrincipalMenuGameState extends GameStateAdapter {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture background;

	private GameAssetsManager assetsManager;

	public PrincipalMenuGameState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {

		assetsManager = new GameAssetsManager();
		assetsManager.loadRessource(GameRessources.MENU_BG,
				GameAssetsManager.TEXTURE);
		assetsManager.finishLoading();

		background = assetsManager.getTextureRessource(GameRessources.MENU_BG);
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
}
