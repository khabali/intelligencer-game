package com.game;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.component.MovementComponent;
import com.game.entity.EntityFactory;
import com.game.system.InputSystem;
import com.game.system.MapRenderSystem;
import com.game.system.MovementSystem;
import com.game.system.SpriteRenderSystem;

public class MyGdxGame extends ApplicationAdapter {

	//
	private final String tag = getClass().getName();

	//
	private World world;
	private OrthographicCamera camera;
	private ScreenViewport viewport;
	Entity hero;

	@Override
	public void create() {

		// LOG LEVEL
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// initialisation du camera
		float screenW = Gdx.graphics.getWidth();
		float screenH = Gdx.graphics.getHeight();

		this.camera = new OrthographicCamera(screenW, screenH);
		this.camera.position.set(screenW/2, screenH/2, 0);

		world = new World(); // World is part of the artemis framework
		world.setSystem(new MapRenderSystem(camera));
		world.setSystem(new InputSystem(camera));
		world.setSystem(new MovementSystem());
		world.setSystem(new SpriteRenderSystem(camera));
		world.initialize();

		// create the univers entity which contains the map componenet
		EntityFactory.createUnivers(world).addToWorld();
		hero = EntityFactory.createHero(world);
		hero.addToWorld();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		//super.resize(width, height);
		camera.setToOrtho(false, width, height);
		//viewport.update(width, height);
		//camera.update();
		//camera.viewportWidth = width;
		//camera.viewportHeight = height;
		//camera.position.set(0, 0, 0);
		/*
		camera.viewportWidth = width * (width / height);
		camera.viewportHeight = height;
		camera.position.set(0, 0, 0);
		camera.update();
		*/
		//world.getSystem(MapRenderSystem.class).updateView();

		Gdx.app.debug(tag, "viewPort resized w:" + camera.viewportWidth + " h:"
				+ camera.viewportHeight);
	}

}
