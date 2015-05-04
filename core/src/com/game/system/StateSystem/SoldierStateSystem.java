package com.game.system.StateSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.game.component.MovementComponent;
import com.game.component.StateComponent;
import com.game.component.tag.SoldierComponentTag;
import com.game.entity.state.PlayerEntityState;

public class SoldierStateSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<StateComponent> stateComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
	
	private MovementComponent movement;
	private StateComponent state;

	public SoldierStateSystem() {
		super(Aspect.getAspectForAll(SoldierComponentTag.class, StateComponent.class));
	}

	@Override
	protected void process(Entity e) {
		state = stateComponentMapper.get(e);
		movement = movementComponentMapper.get(e);
		
		if (movement != null) {
			processMovement();
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
