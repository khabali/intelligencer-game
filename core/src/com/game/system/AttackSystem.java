package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.World;
import com.game.component.AttackComponent;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.State;
import com.game.component.StateComponent;
import com.game.input.GameInput;
import com.game.input.TouchButton;
import com.game.map.Map;

public class AttackSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<AttackComponent> attackComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
	@Mapper ComponentMapper<DirectionComponent> directionComponentMapper;
	
	private AttackComponent attack;
	private MovementComponent movement;
	private DirectionComponent direction;
	
	private OrthographicCamera camera;
	private Map map;
	
	public AttackSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(AttackComponent.class));
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		this.map = ((Map)((World)this.world).getTerrain());
	}

	@Override
	// process entities having attack component
	protected void process(Entity e) {
		if (attackComponentMapper.has(e)) {
			attack = attackComponentMapper.getSafe(e);
			movement = movementComponentMapper.get(e);
			direction = directionComponentMapper.get(e);
			
			if (attack.targetToKill != null) { // if true, character has a target to kill
				if (!movement.isMoving) { // means character has arrived to target
					if (attack.gotWeapon()) {
						killTarget();
					}
				}
			}
		}
	}
	
	private void killTarget() {
		//targetToKill.deleteFromWorld();
		attack.isAttacking = true;
		direction.direction = attack.targetToKill.getComponent(DirectionComponent.class).direction;
		attack.targetToKill = null;
	}

}
