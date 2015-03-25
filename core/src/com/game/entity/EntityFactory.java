package com.game.entity;

import com.artemis.Entity;
import com.artemis.World;
import com.game.component.DirectionComponent;
import com.game.component.MapComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;

public class EntityFactory {
	
	public static Entity createUnivers(World world, String fileName) {
		Entity e = world.createEntity();
		MapComponent map = new MapComponent(fileName);
		e.addComponent(map);
		return e;
	}
	
	public static Entity createHero(World world) {
		Entity e = world.createEntity();
		e.addComponent(new SpriteComponent("woman.png", 8, 9));
		e.addComponent(new PositionComponent(0, 0));
		e.addComponent(new MovementComponent(MovementComponent.IDLE, 1.5f));
		e.addComponent(new DirectionComponent(DirectionComponent.RIGHT));
		return e;
	}

}
