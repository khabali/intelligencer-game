package com.game.system;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class MapRenderSystem extends VoidEntitySystem {

	private final String tag = getClass().getName();
	private IsometricTiledMapRenderer isometricTiledMapRenderer;

	public MapRenderSystem(OrthographicCamera camera, TiledMap map) {

		this.isometricTiledMapRenderer = new IsometricTiledMapRenderer(map);
		this.isometricTiledMapRenderer.setView(camera);
		Gdx.app.debug(tag, "instanciated ...");
	}

	public void updateView(OrthographicCamera camera) {
		this.isometricTiledMapRenderer.setView(camera);
	}

	@Override
	protected void processSystem() {
		isometricTiledMapRenderer.render();
	}

}
