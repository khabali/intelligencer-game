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
			if (movement.state == MovementComponent.MOVING) { // request to move on rows
				// Move on row
				if (position.rowPos != movement.targetRow) {
					if (position.rowPos < movement.targetRow) {
						position.setRowPos(position.rowPos+movement.velocity*deltaTime);
						System.out.println(position.rowPos);
						if (movement.targetRow-position.rowPos <= movement.velocity*deltaTime) position.rowPos = movement.targetRow;
					}
					if (position.rowPos > movement.targetRow) {
						position.setRowPos(position.rowPos-movement.velocity*deltaTime);
						if (position.rowPos-movement.targetRow <= movement.velocity*deltaTime) position.rowPos = movement.targetRow;
					}
				}
				// Move on column
				if (position.colPos != movement.targetCol) { // request to move on columns
					if (position.colPos < movement.targetCol) { // move up in columns
						position.setColPos(position.colPos+movement.velocity*deltaTime);
						if (movement.targetCol-position.colPos <= movement.velocity*deltaTime) position.colPos = movement.targetCol; // this in case if it exceeds target
					}
					if (position.colPos > movement.targetCol) { // move down in columns
						position.setColPos(position.colPos-movement.velocity*deltaTime);
						if (position.colPos-movement.targetCol <= movement.velocity*deltaTime) position.colPos = movement.targetCol;
					}
				}
			}
			// Change side
			if (position.rowPos<movement.targetRow && position.colPos==movement.targetCol) direction.toBackRight();
			if (position.rowPos>movement.targetRow && position.colPos==movement.targetCol) direction.toFrontLeft();
			if (position.rowPos==movement.targetRow && position.colPos<movement.targetCol) direction.toBackLeft();
			if (position.rowPos==movement.targetCol && position.colPos>movement.targetCol) direction.toFrontRight();
			if (position.rowPos<movement.targetRow && position.colPos<movement.targetCol) direction.toBack();
			if (position.rowPos>movement.targetRow && position.colPos>movement.targetCol) direction.toFront();
			if (position.rowPos>movement.targetRow && position.colPos<movement.targetCol) direction.toLeft();
			if (position.rowPos<movement.targetRow && position.colPos>movement.targetCol) direction.toRight();
			
			// Check if arrived
			if (position.rowPos==movement.targetRow && position.colPos==movement.targetCol) movement.doStop();
			
			// calculate x and y from row and column
			int spriteWidth = e.getComponent(SpriteComponent.class).getSpriteWidth();
			int spriteHeight = e.getComponent(SpriteComponent.class).getSpriteHeight();
			position.x = (position.rowPos-position.colPos) * MapComponent.TILEHEIGHT - (spriteWidth-MapComponent.TILEWIDTH)/2;
			position.y = (position.rowPos+position.colPos) * (MapComponent.TILEHEIGHT/2);
			
		}
		
	}
	

}
