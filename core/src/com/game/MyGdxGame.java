package com.game;

import com.artemis.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.entity.EntityFactory;
import com.game.system.MapRenderSystem;

public class MyGdxGame extends ApplicationAdapter {	
	private OrthographicCamera camera;
	private World world;
	
	private int defaultWeight = 480;
	private int defaultHeight = 320;
	private int scale = 2;
	
	@Override
	public void create () {
		camera  = new OrthographicCamera();
		world = new World(); // World is part of the artemis framework
		
		camera.setToOrtho(false, defaultWeight * scale, defaultHeight * scale);	
		world.setSystem(new MapRenderSystem(camera));
		world.initialize();
		
		// create the univers entity which contains the map componenet
		EntityFactory.createUnivers(world, "iso_map_test2.tmx").addToWorld();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		world.process();
	}
}
