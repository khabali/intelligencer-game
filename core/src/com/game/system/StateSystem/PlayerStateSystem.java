package com.game.system.StateSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.game.component.AttackComponent;
import com.game.component.MovementComponent;
import com.game.component.SpriteComponent;
import com.game.component.StateComponent;
import com.game.component.tag.PlayerComponentTag;
import com.game.entity.state.PlayerEntityState;

public class PlayerStateSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<StateComponent> stateComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
	@Mapper ComponentMapper<AttackComponent> attackComponentMapper;
	@Mapper ComponentMapper<SpriteComponent> spriteComponentMapper;
	
	private AttackComponent attack;
	private MovementComponent movement;
	private StateComponent state;
	private SpriteComponent sprite;
	
	public PlayerStateSystem() {
		super(Aspect.getAspectForAll(PlayerComponentTag.class, StateComponent.class));
	}
	
	@Override
	// process entities having state component
	protected void process(Entity e) {
		state = stateComponentMapper.get(e);
		movement = movementComponentMapper.get(e);
		attack = attackComponentMapper.get(e);
		sprite = spriteComponentMapper.get(e);
		
		if (movement != null) {
			processMovement();
		}
		if (attack != null) {
			processAttack();
		}
	}
	
	private void processAttack() {
		if (attack.isAttacking) {
			state.currentState = PlayerEntityState.valueOf("ATTACK_" + attack.getActiveWeapon().name);
			if (sprite.animationFinished) {
				state.currentState = PlayerEntityState.IDLE;
				attack.isAttacking = false;
			}
		}
	}
	
	private void processMovement() {
		if (!movement.isMoving) state.currentState = PlayerEntityState.IDLE;
		else {
			if (movement.isRunning) state.currentState = PlayerEntityState.RUN;
			else state.currentState = PlayerEntityState.WALK;
		}
	}

}
