package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.World;
import com.game.component.Direction;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;
import com.game.component.State;
import com.game.component.StateComponent;
import com.game.input.GameInput;
import com.game.input.InputButton;
import com.game.input.TouchButton;
import com.game.map.Map;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<PositionComponent> positionComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
	@Mapper ComponentMapper<DirectionComponent> directionComponentMapper;
	@Mapper ComponentMapper<SpriteComponent> spriteComponentMapper;
	@Mapper ComponentMapper<StateComponent> stateComponentMapper;
	
	private OrthographicCamera camera;
	private Map map;
	
	private MovementComponent movementComponent;
	private PositionComponent positionComponent;
	private DirectionComponent directionComponent;
	private SpriteComponent spriteComponent;
	private StateComponent stateComponent;
	
	private float walkingVelocity = 2.1f;
	private float runningVelocity = 5.5f;

	public MovementSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(PositionComponent.class, MovementComponent.class, DirectionComponent.class, SpriteComponent.class, StateComponent.class));
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		this.map = ((Map)((World)this.world).getTerrain());
	}

	@Override
	// process only entities that have a movement a position a direction a sprite and a state
	protected void process(Entity e) {
		if (movementComponentMapper.has(e) && 
				positionComponentMapper.has(e) && 
				directionComponentMapper.has(e) &&
				spriteComponentMapper.has(e) &&
				stateComponentMapper.has(e)) {
			positionComponent = positionComponentMapper.getSafe(e);
			movementComponent = movementComponentMapper.getSafe(e);		
			directionComponent = directionComponentMapper.getSafe(e);
			spriteComponent = spriteComponentMapper.getSafe(e);
			stateComponent = stateComponentMapper.getSafe(e);
			
			// Moving the object
			if (movementComponent.isMoving) {
				// next move
				nextStep();
				if (stateComponent.state == State.Run) {
					movementComponent.velocity = runningVelocity;
				}else {
					movementComponent.velocity = walkingVelocity;
				}
			}	
			// calculate x and y from row and column
			int spriteWidth = spriteComponent.spriteWidth(directionComponent.direction, stateComponent.state);
			Vector2 v = map.mapToScreen(positionComponent.rowPos, positionComponent.colPos, spriteWidth);
			positionComponent.x = v.x;
			positionComponent.y = v.y;
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
				System.out.println("target col: " + movementComponent.targetCol + ", row: " + movementComponent.targetRow);
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
