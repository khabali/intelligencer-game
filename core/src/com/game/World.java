package com.game;

import com.artemis.Entity;
import com.game.component.PositionComponent;
import com.game.pathfinding.Terrain;

public class World extends com.artemis.World {
	
	private Terrain terrain;
	
	public void setTerrain(Terrain t) {
		terrain = t;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public Entity getEntityAt(int row, int col) {
		int i=0;
		Entity e = this.getEntity(i);
		while (e != null) {
			PositionComponent pos = e.getComponent(PositionComponent.class);
			if (pos != null) {
				if (pos.colPos == col && pos.rowPos == row) return e;
			}
			e = this.getEntity(++i);
		}
		return null;
	}

}
