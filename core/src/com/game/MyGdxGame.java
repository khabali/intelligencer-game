package com.game;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.game.entity.EntityFactory;
import com.game.input.GameInput;
import com.game.input.InputHandler;
import com.game.map.Map;
import com.game.system.MapRenderSystem;
import com.game.system.MovementSystem;
import com.game.system.SpriteRenderSystem;

public class MyGdxGame extends ApplicationAdapter {

	//
	private final String tag = getClass().getName();

	//
	private InputHandler inputHandler;
	private World world;
	private OrthographicCamera camera;
	private Map map;

	@Override
	public void create() {

		// LOG LEVEL
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// input registration
		this.inputHandler = new InputHandler();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(inputHandler);
		multiplexer.addProcessor(new GestureDetector(inputHandler));
		Gdx.input.setInputProcessor(multiplexer);

		// camera init
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		
		// map init
		Map map = new Map("iso_map_test2.tmx");

		world = new World(); // World is part of the artemis framework
		world.setSystem(new MapRenderSystem(camera, map));
		world.setSystem(new MovementSystem(camera, map));
		world.setSystem(new SpriteRenderSystem(camera));
		world.initialize();

		// create the univers entity which contains the map componenet
		EntityFactory.createHero(world).addToWorld();
	}

	@Override
	public void render() {

		// clear screen
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// update camera
		if (GameInput.getInstance().getTouchButton().isPaned()) {
			Vector2 delta = GameInput.getInstance().getTouchButton().delta;
			camera.position.add(-delta.x * camera.zoom, delta.y * camera.zoom,
					0);
		}

		// world process
		world.process();

		// update inpute informations
		GameInput.getInstance().update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {

		camera.setToOrtho(false, width, height);
		Gdx.app.debug(tag, "viewPort resized w:" + camera.viewportWidth + " h:"
				+ camera.viewportHeight);
	}

}
