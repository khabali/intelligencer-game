package com.game;

import com.game.pathfinding.Terrain;

public class World extends com.artemis.World {
	
	private Terrain terrain;
	
	public void setTerrain(Terrain t) {
		terrain = t;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}

}
