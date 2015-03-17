package com.game.entity;

import com.artemis.Entity;
import com.artemis.World;
import com.game.component.MapComponent;

public class EntityFactory {
	
	public static Entity createUnivers(World world, String fileName) {
		Entity e = world.createEntity();
		MapComponent map = new MapComponent(fileName);
		e.addComponent(map);
		return e;
	}

}
