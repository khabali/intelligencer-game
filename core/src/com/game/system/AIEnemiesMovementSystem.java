package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.game.component.AIMovementComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;

public class AIEnemiesMovementSystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();

	@Mapper ComponentMapper<AIMovementComponent> _AIMovementCM;
	@Mapper	ComponentMapper<PositionComponent> positionCM;
	@Mapper	ComponentMapper<MovementComponent> movementCM;
	
	//
	private float watingTime = 0.0f;

	public AIEnemiesMovementSystem() {
		super(Aspect.getAspectForAll(AIMovementComponent.class, PositionComponent.class, MovementComponent.class));
	}

	@Override
	protected void process(Entity e) {
		if (_AIMovementCM.has(e)) {

			AIMovementComponent _AImovement = _AIMovementCM.get(e);
			PositionComponent position = positionCM.get(e);
			MovementComponent movement = movementCM.get(e);
			
			if (!movement.isMoving) {
				
				watingTime += Gdx.graphics.getDeltaTime();
				Gdx.app.debug(tag, "movingTime "+watingTime);
				
				if(watingTime >= _AImovement.waitingBeforRestart){
					watingTime = 0.0f;
					//
					int startCol = (int) position.defaultCol;
					int startRow = (int) position.defaultRow;
	
					//
					int targetCol = movement.targetCol;
					int targetRow = movement.targetRow;
					movement.doMoveFromTo(startRow, startCol, targetRow, targetCol,	false);
				}
			}
		}
	}

}
