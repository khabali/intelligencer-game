package com.game.system;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.game.map.Map;

public class MapRenderSystem extends VoidEntitySystem {
	private final String tag = getClass().getName();
	private IsometricTiledMapRenderer isometricTiledMapRenderer;
	private OrthographicCamera camera;
	private Map map;

	public MapRenderSystem(OrthographicCamera cam, Map m) {
		this.camera = cam;
		this.map = m;
		this.isometricTiledMapRenderer = new IsometricTiledMapRenderer(map.getTiled());
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
