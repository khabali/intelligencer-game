package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapComponent extends Component {
	
	public static int TILEWIDTH = 64;
	public static int TILEHEIGHT = 32;
	
	private TiledMap tiledMap;
	
	public MapComponent(String fileName) {
		tiledMap = new TmxMapLoader().load(fileName);
	}
	
	public TiledMap getTiled() {
		return this.tiledMap;
	}

}
