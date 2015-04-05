package com.game.state;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.game.debug.GameLogger;
import com.game.entity.EntityFactory;
import com.game.input.GameInput;
import com.game.map.Map;
import com.game.resources.GameAssetsManager;
import com.game.resources.GameRessources;
import com.game.system.MapRenderSystem;
import com.game.system.MovementSystem;
import com.game.system.SpriteRenderSystem;

public class Level1GameState extends GameStateAdapter {

	private World world;
	private OrthographicCamera camera;
	private Entity hero;
	private GameAssetsManager gameAssetsManager;

	public Level1GameState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {

		// initialisation du camera
		float screenW = Gdx.graphics.getWidth();
		float screenH = Gdx.graphics.getHeight();

		this.camera = new OrthographicCamera(screenW, screenH);
		this.camera.position.set(screenW / 2, screenH / 2, 0);

		// map init
		Map map = new Map(GameRessources.LVL1_MAP_TMX);

		world = new World(); // World is part of the artemis framework
		world.setSystem(new MapRenderSystem(camera, map));
		world.setSystem(new MovementSystem(camera, map));
		world.setSystem(new SpriteRenderSystem(camera));
		world.initialize();

		gameAssetsManager = new GameAssetsManager();
		gameAssetsManager.loadRessource(GameRessources.SPRITE_SHEET_WOMEN,
				GameAssetsManager.TEXTURE);

		// finish loading
		gameAssetsManager.finishLoading();

		hero = EntityFactory.createHero(world, gameAssetsManager
				.getTextureRessource(GameRessources.SPRITE_SHEET_WOMEN));
		hero.addToWorld();

		// Enemies
		Entity soldier = EntityFactory.createSoldier(world, gameAssetsManager
				.getTextureRessource(GameRessources.SPRITE_SHEET_WOMEN));
		soldier.addToWorld();

	}

	@Override
	public void update() {
		handleInput();

		// update game logic, animation, collesion etc
	}

	@Override
	public void handleInput() {

		// Return to menu is escape is pressed
		if (GameInput.getInstance().getEscapeButton().isPressed()) {
			gsm.setState(GameStateManager.STATE_MENU);
		}

		// update camera
		if (GameInput.getInstance().getTouchButton().isPaned()) {
			Vector2 delta = GameInput.getInstance().getTouchButton().delta;
			camera.position.add(-delta.x * camera.zoom, delta.y * camera.zoom,
					0);
		}

	}

	@Override
	public void draw() {
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(false, width, height);
		GameLogger.debug(tag, "viewPort resized w:" + camera.viewportWidth
				+ " h:" + camera.viewportHeight);
	}

	@Override
	public void dispose() {
		super.dispose();
		gameAssetsManager.dispose();
	}

}
