package com.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.game.pathfinding.Terrain;

public class Map implements Terrain {
	
	//public static int TILEWIDTH = 64;
	//public static int TILEHEIGHT = 32;

	// map properties
	public static final String PROP_WIDTH = "width";
	public static final String PROP_HEIGHT = "height";
	public static final String PROP_TILE_WIDTH = "tilewidth";
	public static final String PROP_TILE_HEIGHT = "tileheight";

	private TiledMap tiledMap;
	private TiledMapTileLayer grassLayer;

	public Map(String fileName) {
		tiledMap = new TmxMapLoader().load(fileName);
		grassLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
	}
	 
	@Override
	// this the width in cells
	public int getWidth() {
		return tiledMap.getProperties().get(PROP_WIDTH, Integer.class);
	}
	
	@Override
	// this the height in cells
	public int getHeight() {
		return tiledMap.getProperties().get(PROP_HEIGHT, Integer.class);
	}
	
	// this is the width in pixels
	public int getPixelWidth() {
		return getWidth() * getTileWidth();
	}
	
	// this is the height in pixels
	public int getPixelHeight() {
		return getHeight() * getTileHeight();
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

		if (outOfBounds(mapx, mapy)) return null;
		else return new Vector2((int)mapx, (int)mapy);
	}
	
	public Vector2 mapToScreen(float row, float col) {
		Vector2 v = new Vector2();
		v.x = (row-col) * getTileHeight() + getTileWidth() / 2;
		v.y = (row + col) * (getTileHeight() / 2);
		v.x -= getTileHeight();
		v.y += getTileHeight()/2;
		return v;
	}

	@Override
	public boolean passable(int x, int y) {
		if (outOfBounds(x, y)) return false;
		if (grassLayer.getCell(Math.abs(y), x) == null) return true;
		return !grassLayer.getCell(Math.abs(y), x).getTile().getProperties().containsKey("blocked");
	}

	@Override
	public int cost(int x, int y) {
		return 1;
	}
	
	public boolean outOfBounds(float x, float y) {
		return -y < 0 || x < 0 || x > getWidth() || -y > getHeight();
	}

}
