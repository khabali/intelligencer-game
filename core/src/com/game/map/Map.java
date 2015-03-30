package com.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class Map {
	
	//public static int TILEWIDTH = 64;
	//public static int TILEHEIGHT = 32;

	// map properties
	public static final String PROP_WIDTH = "width";
	public static final String PROP_HEIGHT = "height";
	public static final String PROP_TILE_WIDTH = "tilewidth";
	public static final String PROP_TILE_HEIGHT = "tileheight";

	private TiledMap tiledMap;

	public Map(String fileName) {
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
	
	public Vector2 screenToMap(float x, float y) {
		float tileWidthHalf = getTileWidth()/2;
		float tileHeightHalf = getTileHeight()/2;
		x -= tileWidthHalf;
		float mapx = ((x / tileWidthHalf + y / tileHeightHalf) / 2);
		float mapy = ((y / tileHeightHalf - (x / tileWidthHalf)) / 2);
		mapy -= 1;
		return new Vector2((int)mapx, (int)mapy);
	}
	
	public Vector2 mapToScreen(float row, float col, int spriteWidth) {
		Vector2 v = new Vector2();
		v.x = (row-col) * getTileHeight() - (spriteWidth - getTileWidth()) / 2;
		v.y = (row + col) * (getTileHeight() / 2);
		return v;
	}

}
