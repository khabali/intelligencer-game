package com.game.entity;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.game.World;
import com.game.component.MovementAIComponent;
import com.game.component.AttackComponent;
import com.game.component.Direction;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.RadarComponent;
import com.game.component.SpriteComponent;
import com.game.component.StateComponent;
import com.game.component.tag.PlayerComponentTag;
import com.game.component.tag.SoldierComponentTag;
import com.game.entity.state.PlayerEntityState;
import com.game.entity.state.SoldierEntityState;
import com.game.resources.AssetsManager;
import com.game.weapon.KnifeWeapon;

public class EntityFactory {

	public static Entity createVillager(World world) {
		Entity e = world.createEntity();		
		e.addComponent(new SoldierComponentTag());
		e.addComponent(new SpriteComponent(AssetsManager.SPRITE_SHEET_VILLAGER, "villager"));
		e.addComponent(new PositionComponent(8, 0));
		//
		MovementComponent movementComponent = new MovementComponent(world.getTerrain(), 2.1f);
		movementComponent.setTarget(8, -6);
		e.addComponent(movementComponent);
		
		e.addComponent(new RadarComponent());
		e.addComponent(new MovementAIComponent());
		e.addComponent(new StateComponent(SoldierEntityState.IDLE));
		e.addComponent(new DirectionComponent(Direction.FRONT));
		return e;
	}

	public static Entity createArcher(World world) {
		Entity e = world.createEntity();
		e.addComponent(new PlayerComponentTag());
		e.addComponent(new SpriteComponent(AssetsManager.SPRITE_SHEET_ARCHER, "Archer"));
		e.addComponent(new PositionComponent(10, 10));
		e.addComponent(new MovementComponent(world.getTerrain(), 2.1f));
		e.addComponent(new StateComponent(PlayerEntityState.IDLE));
		e.addComponent(new DirectionComponent(Direction.RIGHT));
		// attack component and weapon
		AttackComponent attackComponent = new AttackComponent();
		attackComponent.registerActivateWeapon(new KnifeWeapon("knife"));
		e.addComponent(attackComponent);
		return e;
	}

}
