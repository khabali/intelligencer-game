package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.game.component.MapComponent;

public class MapRenderSystem extends EntityProcessingSystem  {
	@Mapper ComponentMapper<MapComponent> mapc;
	
	private IsometricTiledMapRenderer isometricTiledMapRenderer;
	private OrthographicCamera orthographicCamera;
	
	public MapRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(MapComponent.class));
		this.isometricTiledMapRenderer = new IsometricTiledMapRenderer(null);
		this.orthographicCamera = camera;
		//orthographicCamera.position.set(Gdx.graphics.getWidth()/ 2, Gdx.graphics.getHeight()/ 2, 0);
	}
	
	protected void process(Entity e) {
		// Get the components from the entity using component mappers.
		MapComponent map = mapc.get(e);
		// Set the tiled map to render (because it can render different maps on the fly)
		isometricTiledMapRenderer.setMap(map.getTiled());
		// Update camera
		orthographicCamera.update();
		// Set the camera view
		isometricTiledMapRenderer.setView(orthographicCamera);
		// Render on screen
		isometricTiledMapRenderer.render();
	}

}
