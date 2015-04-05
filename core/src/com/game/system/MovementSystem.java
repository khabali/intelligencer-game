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
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;
import com.game.input.GameInput;
import com.game.input.TouchButton;
import com.game.map.Map;
import com.game.pathfinding.PathFinder;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<PositionComponent> posc;
	@Mapper ComponentMapper<MovementComponent> movc;
	@Mapper ComponentMapper<DirectionComponent> dirc;
	
	private OrthographicCamera camera;
	private Map map;
	
	private MovementComponent movement;
	private PositionComponent position;
	private DirectionComponent direction;

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
		if (movc.has(e)) {
			position = posc.get(e);
			movement = movc.getSafe(e);
			direction = dirc.get(e);
			float deltaTime= Gdx.graphics.getDeltaTime();
			// input processing
			processInput();
			// Moving the object
			if (movement.isMoving()) {
				// next move
				nextStep(deltaTime);
			}	
			// calculate x and y from row and column
			int spriteWidth = e.getComponent(SpriteComponent.class).getSpriteWidth();
			Vector2 v = map.mapToScreen(position.rowPos, position.colPos, spriteWidth);
			position.x = v.x;
			position.y = v.y;
		}		
	}
	
	private void changeSide() {
		if (position.rowPos<movement.targetRow && position.colPos==movement.targetCol) direction.toBackRight();
		if (position.rowPos>movement.targetRow && position.colPos==movement.targetCol) direction.toFrontLeft();
		if (position.rowPos==movement.targetRow && position.colPos<movement.targetCol) direction.toBackLeft();
		if (position.rowPos==movement.targetRow && position.colPos>movement.targetCol) direction.toFrontRight();
		if (position.rowPos<movement.targetRow && position.colPos<movement.targetCol) direction.toBack();
		if (position.rowPos>movement.targetRow && position.colPos>movement.targetCol) direction.toFront();
		if (position.rowPos>movement.targetRow && position.colPos<movement.targetCol) direction.toLeft();
		if (position.rowPos<movement.targetRow && position.colPos>movement.targetCol) direction.toRight();
	}
	
	private void moveOnRow(float deltaTime) {
		if (position.rowPos < movement.targetRow) {
			position.rowPos += movement.velocity * deltaTime;
			if (position.rowPos > movement.targetRow) position.rowPos = movement.targetRow;
		}
		if (position.rowPos > movement.targetRow) {
			position.rowPos -= movement.velocity * deltaTime;
			if (position.rowPos < movement.targetRow) position.rowPos = movement.targetRow;
		}
	}
	
	private void moveOnCol(float deltaTime) {
		if (position.colPos < movement.targetCol) {
			position.colPos += movement.velocity * deltaTime;
			if (position.colPos > movement.targetCol) position.colPos = movement.targetCol;
		}
		if (position.colPos > movement.targetCol) {
			position.colPos -= movement.velocity * deltaTime;
			if (position.colPos < movement.targetCol) position.colPos = movement.targetCol;
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
		if (movement.isMoving()) doStop();
		// mark as moving
		movement.setMoving();
		// calling A*, the map is reversed thus, we convert coordinates
		System.out.println("do move from " + (int)position.rowPos + "," + (int)position.colPos);
		System.out.println("do move to " + (int)row + "," + (int)col);
		movement.pathFinder.aStar((int)position.colPos, (int)position.rowPos, (int)col, (int)row);
		// after the A* is performed the target row and col are set to current row and col
		// each step updates targets
	}
	
	private void doStop() {
		movement.pathFinder.clearPath();
		movement.setIdle();
	}
	
	public void nextStep(float deltaTime) {
		if (position.rowPos == movement.targetRow && position.colPos == movement.targetCol) {
			Vector2 next= movement.pathFinder.nextStep();
			if (next!= null) {
				movement.targetCol = (int) next.x;
				movement.targetRow = (int) next.y;
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
