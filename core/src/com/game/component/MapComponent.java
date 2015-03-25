package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapComponent extends Component {

	public static int TILEWIDTH = 64;
	public static int TILEHEIGHT = 32;

	// map properties
	public static final String PROP_WIDTH = "width";
	public static final String PROP_HEIGHT = "height";
	public static final String PROP_TILE_WIDTH = "tilewidth";
	public static final String PROP_TILE_HEIGHT = "tileheight";

	private TiledMap tiledMap;

	public MapComponent(String fileName) {
		tiledMap = new TmxMapLoader().load(fileName);
	}

	public int getWidth() {
		return tiledMap.getProperties().get(PROP_WIDTH, Integer.class);
	}

	public int getHeight() {
		return tiledMap.getProperties().get(PROP_HEIGHT, Integer.class);
	}

	public int getTileWidth() {
		return tiledMap.getProperties().get(PROP_TILE_WIDTH, Integer.class);
	}

	public int getTileHeight() {
		return tiledMap.getProperties().get(PROP_TILE_HEIGHT, Integer.class);
	}

	public TiledMap getTiled() {
		return this.tiledMap;
	}

}
