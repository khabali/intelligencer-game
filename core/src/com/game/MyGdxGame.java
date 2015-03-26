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
import com.game.component.MovementComponent;
import com.game.entity.EntityFactory;
import com.game.system.InputSystem;
import com.game.system.MapRenderSystem;
import com.game.system.MovementSystem;
import com.game.system.SpriteRenderSystem;

public class MyGdxGame extends ApplicationAdapter implements GestureListener {

	//
	private final String tag = getClass().getName();

	//
	private World world;
	private OrthographicCamera camera;
	Entity hero;

	@Override
	public void create() {

		// LOG LEVEL
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// initialisation du camera
		float screenW = Gdx.graphics.getWidth();
		float screenH = Gdx.graphics.getHeight();

		this.camera = new OrthographicCamera();
		camera.viewportWidth = screenW * (screenW / screenH);
		camera.viewportHeight = screenH;
		camera.position.set(0, 0, 0);
		camera.position.set(0, 0, 0);
		camera.update();

		// load map
		TiledMap map = new TmxMapLoader().load("iso_map_test2.tmx");

		world = new World(); // World is part of the artemis framework
		world.setSystem(new MapRenderSystem(camera, map));
		world.setSystem(new InputSystem());
		world.setSystem(new MovementSystem());
		world.setSystem(new SpriteRenderSystem());
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
		super.resize(width, height);
		camera.viewportWidth = width * (width / height);
		camera.viewportHeight = height;
		camera.position.set(0, 0, 0);
		camera.update();
		world.getSystem(MapRenderSystem.class).updateView(camera);

		Gdx.app.debug(tag, "viewPort resized w:" + camera.viewportWidth + " h:"
				+ camera.viewportHeight);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		MovementComponent mov = hero.getComponent(MovementComponent.class);
		mov.doMove(10, 5);
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
