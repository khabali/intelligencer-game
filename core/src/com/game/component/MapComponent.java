package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapComponent extends Component {
	
	private TiledMap tiledMap;
	
	public MapComponent(String fileName) {
		tiledMap = new TmxMapLoader().load(fileName);
	}
	
	public TiledMap getTiled() {
		return this.tiledMap;
	}

}
