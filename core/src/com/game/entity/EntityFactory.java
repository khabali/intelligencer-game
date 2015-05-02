package com.game.entity;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.game.World;
import com.game.component.AIMovementComponent;
import com.game.component.AttackComponent;
import com.game.component.Direction;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PlayerComponent;
import com.game.component.PositionComponent;
import com.game.component.RadarComponent;
import com.game.component.SpriteComponent;
import com.game.component.State;
import com.game.component.StateComponent;
import com.game.resources.AssetsManager;
import com.game.weapon.KnifeWeapon;

public class EntityFactory {

	public static Entity createVillager(World world) {
		Entity e = world.createEntity();
		TextureAtlas sheetTexture = AssetsManager.manager.get(
				AssetsManager.SPRITE_SHEET_VILLAGER, TextureAtlas.class);
		e.addComponent(new SpriteComponent(sheetTexture, "villager"));
		e.addComponent(new PositionComponent(8, 0));
		//
		MovementComponent movementComponent = new MovementComponent(world.getTerrain(), 2.1f);
		movementComponent.setTarget(8, -6);
		e.addComponent(movementComponent);
		
		e.addComponent(new RadarComponent());
		e.addComponent(new AIMovementComponent());
		e.addComponent(new StateComponent(State.Idle));
		e.addComponent(new DirectionComponent(Direction.FRONT));
		return e;
	}

	public static Entity createArcher(World world) {
		Entity e = world.createEntity();
		TextureAtlas sheetTexture = AssetsManager.manager.get(
				AssetsManager.SPRITE_SHEET_ARCHER, TextureAtlas.class);
		e.addComponent(new PlayerComponent());
		e.addComponent(new SpriteComponent(sheetTexture, "Archer"));
		e.addComponent(new PositionComponent(10, 10));
		e.addComponent(new MovementComponent(world.getTerrain(), 2.1f));
		e.addComponent(new StateComponent(State.Idle));
		e.addComponent(new DirectionComponent(Direction.RIGHT));
		// attack component and weapon
		KnifeWeapon knife = new KnifeWeapon("KnifeAttack");
		AttackComponent attackComponent = new AttackComponent();
		attackComponent.registerWeapon(knife);
		attackComponent.activateWeapon(knife);
		e.addComponent(attackComponent);
		return e;
	}

}
