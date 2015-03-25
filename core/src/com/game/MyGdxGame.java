package com.game;

import com.artemis.ComponentType;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.entity.EntityFactory;
import com.game.system.MapRenderSystem;
import com.game.system.MovementSystem;
import com.game.system.SpriteRenderSystem;

public class MyGdxGame extends ApplicationAdapter implements GestureListener {	
	private OrthographicCamera camera;
	private World world;
	
	private int defaultWidth = 480;
	private int defaultHeight = 320;
	private int scale = 2;
	
	Entity hero;
	
	@Override
	public void create () {
		camera  = new OrthographicCamera();
		world = new World(); // World is part of the artemis framework
		
		camera.setToOrtho(false, defaultWidth * scale, defaultHeight * scale);	
		world.setSystem(new MapRenderSystem(camera));
		world.setSystem(new MovementSystem());
		world.setSystem(new SpriteRenderSystem(camera));
		world.initialize();
		
		// create the univers entity which contains the map componenet
		EntityFactory.createUnivers(world, "iso_map_test2.tmx").addToWorld();;
		
		hero = EntityFactory.createHero(world);
		hero.addToWorld();
		
		Gdx.input.setInputProcessor(new GestureDetector(20, 0.5f, 2, 0.15f, this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		world.process();
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
