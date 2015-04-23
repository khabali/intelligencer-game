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
import com.game.input.GameInput;
import com.game.input.TouchButton;
import com.game.map.Map;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<PositionComponent> positionComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
	@Mapper ComponentMapper<DirectionComponent> directionComponentMapper;
	
	private OrthographicCamera camera;
	private Map map;
	
	private MovementComponent movementComponent;
	private PositionComponent positionComponent;
	private DirectionComponent directionComponent;

	public MovementSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(PositionComponent.class, MovementComponent.class, DirectionComponent.class));
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		this.map = ((Map)((World)this.world).getTerrain());
	}

	@Override
	protected void process(Entity e) {
		if (movementComponentMapper.has(e)) {
			positionComponent = positionComponentMapper.get(e);
			movementComponent = movementComponentMapper.getSafe(e);
			directionComponent = directionComponentMapper.get(e);
			float deltaTime= Gdx.graphics.getDeltaTime();
			// input processing
			processInput();
			// Moving the object
			if (movementComponent.isMoving()) {
				// next move
				nextStep();
			}	
			// calculate x and y from row and column
			int spriteWidth = e.getComponent(SpriteComponent.class).spriteWidth(directionComponent.direction, movementComponent.state);
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
	
	private void processInput() {
		TouchButton touch = GameInput.getInstance().getTouchButton();
		if (touch.isTouched()) {
			Vector2 pos = touch.position;
			Vector3 v = camera.unproject(new Vector3(pos.x, pos.y, 0));
			Vector2 v2 = map.screenToMap(v.x, v.y);
			int row = (int)v2.x;
			int col = (int)v2.y;
			System.out.println("do move " + row + "," + col);
			//clickMovPos = new Vector2(row, col);
			doMove(row, col);
		}
	}
	
	private void doMove(int row, int col) {
		// if it is moving stop it
		if (movementComponent.isMoving()) doStop();
		// calling A*, the map is reversed thus, we convert coordinates
		System.out.println("do move from " + (int)positionComponent.rowPos + "," + (int)positionComponent.colPos);
		System.out.println("do move to " + (int)row + "," + (int)col);
		movementComponent.pathFinder.aStar((int)positionComponent.colPos, (int)positionComponent.rowPos, (int)col, (int)row);
		// after the A* is performed the target row and col are set to current row and col
		// each step updates targets
		// mark as moving
		movementComponent.setMoving();
		movementComponent.setTarget(positionComponent.rowPos, positionComponent.colPos);
	}
	
	private void doStop() {
		movementComponent.pathFinder.clearPath();
		movementComponent.setIdle();
	}
	
	public void nextStep() {
		float deltaTime= Gdx.graphics.getDeltaTime();
		System.out.println("target rowpos: " + positionComponent.rowPos + ", colpos: " + positionComponent.colPos);
		System.out.println("target targetrow: " + movementComponent.targetRow + ", targetcol: " + movementComponent.targetCol);
		if (positionComponent.rowPos == movementComponent.targetRow && positionComponent.colPos == movementComponent.targetCol) {
			Vector2 next= movementComponent.pathFinder.nextStep();
			if (next!= null) {
				movementComponent.targetCol = (int) next.x;
				movementComponent.targetRow = (int) next.y;
				System.out.println("target col: " + movementComponent.targetCol + ", row: " + movementComponent.targetRow);
			}else { // means arrived
				doStop();
				return;
			}
		}
		moveOnRow(deltaTime);
		moveOnCol(deltaTime);
		changeSide();
	}

}
