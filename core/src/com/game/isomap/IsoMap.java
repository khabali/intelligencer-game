package com.game.isomap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class IsoMap {
	
	private TiledMap tiledMap;
	private IsometricTiledMapRenderer isometricTiledMapRenderer;
	private OrthographicCamera orthographicCamera;
	
	public IsoMap(String fileName) {
		tiledMap = loadMap(fileName);
		isometricTiledMapRenderer = new IsometricTiledMapRenderer(tiledMap);
	}
	
	
	
	private  TiledMap loadMap(String fileName){
		 return new TmxMapLoader().load(fileName);
	}
	
	public void render(){
		isometricTiledMapRenderer.setView(orthographicCamera);
		isometricTiledMapRenderer.render();
	}
	
	public void setOrthographicCamera(OrthographicCamera orthographicCamera) {
		this.orthographicCamera = orthographicCamera;
	}

}
