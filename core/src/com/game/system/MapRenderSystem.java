package com.game.system;

import java.util.Iterator;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.game.component.MapComponent;
import com.game.component.PositionComponent;

public class MapRenderSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<MapComponent> mapComponentMapper;

	private final String tag = getClass().getName();
	private IsometricTiledMapRenderer isometricTiledMapRenderer;
	private OrthographicCamera camera;

	public MapRenderSystem(OrthographicCamera cam) {
		super(Aspect.getAspectForAll(MapComponent.class));
		this.camera = cam;
	}
	
	@Override
    protected void begin() {
		camera.update();
		isometricTiledMapRenderer.setView(camera);
	}

	@Override
	protected void process(Entity e) {	
		isometricTiledMapRenderer.render();	
	}
	
	@Override
	protected void inserted(Entity e) {
		MapComponent map = mapComponentMapper.get(e);
		this.isometricTiledMapRenderer = new IsometricTiledMapRenderer(map.getTiled());
		this.isometricTiledMapRenderer.setView(camera);
	}

}
