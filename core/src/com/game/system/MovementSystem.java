package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.game.component.Direction;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;
import com.game.component.StateComponent;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<PositionComponent> positionComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
	@Mapper ComponentMapper<DirectionComponent> directionComponentMapper;
	@Mapper ComponentMapper<SpriteComponent> spriteComponentMapper;
	
	private static final String tag = "MovementSystem";
		
	private MovementComponent movementComponent;
	private PositionComponent positionComponent;
	private DirectionComponent directionComponent;
	
	private float walkingVelocity = 2.1f;
	private float runningVelocity = 5.5f;

	public MovementSystem() {
		super(Aspect.getAspectForAll(PositionComponent.class, MovementComponent.class, DirectionComponent.class));
	}
	


	@Override
	// process only entities that have a movement a position a direction
	protected void process(Entity e) {
		if (movementComponentMapper.has(e) && 
				positionComponentMapper.has(e) && 
				directionComponentMapper.has(e)) {
			positionComponent = positionComponentMapper.getSafe(e);
			movementComponent = movementComponentMapper.getSafe(e);		
			directionComponent = directionComponentMapper.getSafe(e);
			
			// Moving the object
			if (movementComponent.isMoving) {
				// next move
				nextStep();
				if (movementComponent.isRunning) {
					movementComponent.velocity = runningVelocity;
				}else {
					movementComponent.velocity = walkingVelocity;
				}
			}	
		}
	}
	
	private void changeSide() {
		if (positionComponent.rowPos<movementComponent.targetRow && positionComponent.colPos==movementComponent.targetCol) directionComponent.change(Direction.BACKRIGHT);
		if (positionComponent.rowPos>movementComponent.targetRow && positionComponent.colPos==movementComponent.targetCol) directionComponent.change(Direction.FRONTLEFT);
		if (positionComponent.rowPos==movementComponent.targetRow && positionComponent.colPos<movementComponent.targetCol) directionComponent.change(Direction.BACKLEFT);
		if (positionComponent.rowPos==movementComponent.targetRow && positionComponent.colPos>movementComponent.targetCol) directionComponent.change(Direction.FRONTRIGHT);
		if (positionComponent.rowPos<movementComponent.targetRow && positionComponent.colPos<movementComponent.targetCol) directionComponent.change(Direction.BACK);
		if (positionComponent.rowPos>movementComponent.targetRow && positionComponent.colPos>movementComponent.targetCol) directionComponent.change(Direction.FRONT);
		if (positionComponent.rowPos>movementComponent.targetRow && positionComponent.colPos<movementComponent.targetCol) directionComponent.change(Direction.LEFT);
		if (positionComponent.rowPos<movementComponent.targetRow && positionComponent.colPos>movementComponent.targetCol) directionComponent.change(Direction.RIGHT);
	}
	
	private void moveOnRow(float deltaTime) {
		if (positionComponent.rowPos < movementComponent.targetRow) {
			positionComponent.rowPos += movementComponent.velocity * deltaTime;
			if (positionComponent.rowPos > movementComponent.targetRow) positionComponent.rowPos = movementComponent.targetRow;
		}
		if (positionComponent.rowPos > movementComponent.targetRow) {
			positionComponent.rowPos -= movementComponent.velocity * deltaTime;
			if (positionComponent.rowPos < movementComponent.targetRow) positionComponent.rowPos = movementComponent.targetRow;
		}
	}
	
	private void moveOnCol(float deltaTime) {
		if (positionComponent.colPos < movementComponent.targetCol) {
			positionComponent.colPos += movementComponent.velocity * deltaTime;
			if (positionComponent.colPos > movementComponent.targetCol) positionComponent.colPos = movementComponent.targetCol;
		}
		if (positionComponent.colPos > movementComponent.targetCol) {
			positionComponent.colPos -= movementComponent.velocity * deltaTime;
			if (positionComponent.colPos < movementComponent.targetCol) positionComponent.colPos = movementComponent.targetCol;
		}
	}
	
	public void nextStep() {
		float deltaTime= Gdx.graphics.getDeltaTime();
		if (positionComponent.rowPos == movementComponent.targetRow && positionComponent.colPos == movementComponent.targetCol) {
			Vector2 next= movementComponent.pathFinder.nextStep();
			if (next!= null) {
				movementComponent.targetCol = (int) next.x;
				movementComponent.targetRow = (int) next.y;
				//Gdx.app.debug(tag, "target col: " + movementComponent.targetCol + ", row: " + movementComponent.targetRow);
			}else { // means arrived
				movementComponent.doStop();
				return;
			}
		}
		moveOnRow(deltaTime);
		moveOnCol(deltaTime);
		changeSide();
	}

}
