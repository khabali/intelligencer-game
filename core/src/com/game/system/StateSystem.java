package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.World;
import com.game.component.AttackComponent;
import com.game.component.MovementComponent;
import com.game.component.PlayerComponent;
import com.game.component.SpriteComponent;
import com.game.component.State;
import com.game.component.StateComponent;
import com.game.input.GameInput;
import com.game.input.TouchButton;

public class StateSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<StateComponent> stateComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
	@Mapper ComponentMapper<AttackComponent> attackComponentMapper;
	@Mapper ComponentMapper<SpriteComponent> spriteComponentMapper;
	
	private StateComponent state;
	private MovementComponent movement;
	private AttackComponent attack;
	private SpriteComponent sprite;

	public StateSystem() {
		super(Aspect.getAspectForAll(StateComponent.class));
	}

	@Override
	// process entities having state component
	protected void process(Entity e) {
		if (stateComponentMapper.has(e)) {
			state = stateComponentMapper.get(e);
			movement = movementComponentMapper.get(e);
			attack = attackComponentMapper.get(e);
			sprite = spriteComponentMapper.get(e);
			
			if (attack != null && attack.isAttacking) { // entity can attack
				if (state.state == State.Fire) {
					// fire animation should be played once before resetting to idle
					if (sprite.animationFinished) {
						state.setState(State.Idle);
						attack.isAttacking = false;
					}
				}else {
					state.setState(State.Fire);
				}
			}else if (!movement.isMoving) {
				state.setState(State.Idle);
			}else {
				if (movement.isRunning) {
					state.setState(State.Run);
				}else {
					state.setState(State.Walk);
				}
			}
		}
	}

}
