package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.game.component.DirectionComponent;
import com.game.component.MapComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<PositionComponent> posc;
	@Mapper ComponentMapper<MovementComponent> movc;
	@Mapper ComponentMapper<DirectionComponent> dirc;

	public MovementSystem() {
		super(Aspect.getAspectForAll(PositionComponent.class, MovementComponent.class, DirectionComponent.class));
	}

	@Override
	protected void process(Entity e) {
		if (movc.has(e)) {
			PositionComponent position = posc.get(e);
			MovementComponent movement = movc.getSafe(e);
			DirectionComponent direction = dirc.get(e);
			float deltaTime= Gdx.graphics.getDeltaTime();
			// Moving
			moveOnRow(deltaTime, position, movement);
			moveOnCol(deltaTime, position, movement);
			// Change side
			changeSide(position, movement, direction);		
			// Check if arrived
			if (position.rowPos==movement.targetRow && position.colPos==movement.targetCol) movement.doStop();			
			// calculate x and y from row and column
			int spriteWidth = e.getComponent(SpriteComponent.class).getSpriteWidth();
			int spriteHeight = e.getComponent(SpriteComponent.class).getSpriteHeight();
			position.x = (position.rowPos-position.colPos) * MapComponent.TILEHEIGHT - (spriteWidth-MapComponent.TILEWIDTH)/2;
			position.y = (position.rowPos+position.colPos) * (MapComponent.TILEHEIGHT/2);			
		}		
	}
	
	private void changeSide(PositionComponent position, MovementComponent movement, DirectionComponent direction) {
		if (position.rowPos<movement.targetRow && position.colPos==movement.targetCol) direction.toBackRight();
		if (position.rowPos>movement.targetRow && position.colPos==movement.targetCol) direction.toFrontLeft();
		if (position.rowPos==movement.targetRow && position.colPos<movement.targetCol) direction.toBackLeft();
		if (position.rowPos==movement.targetCol && position.colPos>movement.targetCol) direction.toFrontRight();
		if (position.rowPos<movement.targetRow && position.colPos<movement.targetCol) direction.toBack();
		if (position.rowPos>movement.targetRow && position.colPos>movement.targetCol) direction.toFront();
		if (position.rowPos>movement.targetRow && position.colPos<movement.targetCol) direction.toLeft();
		if (position.rowPos<movement.targetRow && position.colPos>movement.targetCol) direction.toRight();
	}
	
	private void moveOnRow(float deltaTime, PositionComponent pos, MovementComponent mov) {
		if (pos.rowPos < mov.targetRow) {
			pos.rowPos += mov.velocity * deltaTime;
			if (pos.rowPos > mov.targetRow) pos.rowPos = mov.targetRow;
		}
		if (pos.rowPos > mov.targetRow) {
			pos.rowPos -= mov.velocity * deltaTime;
			if (pos.rowPos < mov.targetRow) pos.rowPos = mov.targetRow;
		}
	}
	
	private void moveOnCol(float deltaTime, PositionComponent pos, MovementComponent mov) {
		if (pos.colPos < mov.targetCol) {
			pos.colPos += mov.velocity * deltaTime;
			if (pos.colPos > mov.targetCol) pos.colPos = mov.targetCol;
		}
		if (pos.colPos > mov.targetCol) {
			pos.colPos -= mov.velocity * deltaTime;
			if (pos.colPos < mov.targetCol) pos.colPos = mov.targetCol;
		}
	}

}
