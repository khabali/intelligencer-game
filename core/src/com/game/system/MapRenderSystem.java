package com.game.system;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.game.World;
import com.game.map.Map;

public class MapRenderSystem extends VoidEntitySystem {
	private final String tag = getClass().getName();
	private IsometricTiledMapRenderer isometricTiledMapRenderer;
	private OrthographicCamera camera;

	public MapRenderSystem(OrthographicCamera cam) {
		this.camera = cam;
	}
	
	@Override
	protected void initialize() {
		TiledMap tiled = ((Map)((World)this.world).getTerrain()).getTiled();
		this.isometricTiledMapRenderer = new IsometricTiledMapRenderer(tiled);
	}
	
	@Override
    protected void begin() {
		camera.update();
		isometricTiledMapRenderer.setView(camera);
	}

	@Override
	protected void processSystem() {
		isometricTiledMapRenderer.render();	
	}

}
