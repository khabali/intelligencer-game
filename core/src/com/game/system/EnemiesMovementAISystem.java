package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.game.component.MovementAIComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;

public class EnemiesMovementAISystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();

	@Mapper ComponentMapper<MovementAIComponent> _AIMovementCM;
	@Mapper	ComponentMapper<PositionComponent> positionCM;
	@Mapper	ComponentMapper<MovementComponent> movementCM;
	
	//
	private float watingTime = 0.0f;

	public EnemiesMovementAISystem() {
		super(Aspect.getAspectForAll(MovementAIComponent.class, PositionComponent.class, MovementComponent.class));
	}
	

	@Override
	protected void process(Entity e) {
		if (_AIMovementCM.has(e)) {

			MovementAIComponent _AImovement = _AIMovementCM.get(e);
			PositionComponent position = positionCM.get(e);
			MovementComponent movement = movementCM.get(e);
			int walksCount = 0;
			if (!movement.isMoving) {
				
				watingTime += Gdx.graphics.getDeltaTime();// Acumulate wating time
				//Gdx.app.debug(tag, "movingTime "+watingTime);
				
				if(watingTime >= _AImovement.waitingBeforRestart){ //start moving
					watingTime = 0.0f; // Reset the wating time we are moving
					//
					int startCol = (int) position.defaultCol;
					int startRow = (int) position.defaultRow;
					//Gdx.app.debug(tag, "start row : " + startRow + " col : " + startCol);
					//
					int targetCol = movement.targetCol;
					int targetRow = movement.targetRow;
					//Gdx.app.debug(tag, "target row : " + targetRow + " col : " + targetCol);
					
					movement.doMoveFromTo(startRow, startCol, targetRow, targetCol,	false);
					walksCount++;
					if(walksCount>0){
						//position.setDefaultPos(targetRow, targetCol);
						//movement.setTarget(startRow, startCol);
						walksCount=0;
					}
				}
			}
		}
	}

}
