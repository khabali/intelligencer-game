package com.game.entity;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;

public class EntityFactory {

	public static Entity createHero(World world, Texture sheetTexture) {
		Entity e = world.createEntity();
		e.addComponent(new SpriteComponent(sheetTexture, 8, 9));
		e.addComponent(new PositionComponent(0, 0));
		e.addComponent(new MovementComponent(MovementComponent.IDLE, 1.5f));
		e.addComponent(new DirectionComponent(DirectionComponent.RIGHT));
		return e;
	}

	public static Entity createSoldier(World world, Texture sheetTexture) {
		Entity e = world.createEntity();
		e.addComponent(new SpriteComponent(sheetTexture, 8, 9));
		e.addComponent(new PositionComponent(10, 10));
		e.addComponent(new MovementComponent(MovementComponent.IDLE, 1.5f));
		e.addComponent(new DirectionComponent(DirectionComponent.FRONT));
		return e;
	}

}
