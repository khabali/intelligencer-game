package com.game.entity;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.game.World;
import com.game.component.Action;
import com.game.component.Direction;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;
import com.game.resources.AssetsManager;

public class EntityFactory {

	public static Entity createHero(World world) {
		Entity e = world.createEntity();
		TextureAtlas sheetTexture = AssetsManager.manager.get(AssetsManager.SPRITE_SHEET_VILLAGER, TextureAtlas.class);
		e.addComponent(new SpriteComponent(sheetTexture, "villager"));
		e.addComponent(new PositionComponent(0, 0));
		e.addComponent(new MovementComponent(world.getTerrain(), Action.idle, 2.1f));
		e.addComponent(new DirectionComponent(Direction.RIGHT));
		return e;
	}

	
}
