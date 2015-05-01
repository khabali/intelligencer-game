package com.game.component;

import com.artemis.Component;
import com.game.pathfinding.PathFinder;
import com.game.pathfinding.Terrain;

public class MovementComponent extends Component {

	public float velocity; // movement speed
	public int targetRow, targetCol;
	public PathFinder pathFinder;
	public boolean isMoving = false;
	public boolean isRunning = false;

	public MovementComponent(Terrain terrain) {
		pathFinder = new PathFinder(terrain);
	}

	public MovementComponent(Terrain terrain, float vel) {
		this.velocity = vel;
		pathFinder = new PathFinder(terrain);
	}
	
	
	public void setTarget(int row, int col) {
		targetRow = row;
		targetCol = col;
	}
	
	public void setTarget(float row, float col) {
		targetRow = (int)row;
		targetCol = (int)col;
	}
	
	public void setMoving(boolean b) {
		isMoving = b;
	}
	
	public void doMoveFromTo(int row, int col, int targetRow, int targetCol, boolean doRun) {
		if (isMoving) doStop();
		
		pathFinder.aStar(col, row, targetCol, targetRow);
		
		//init target to current position, astar need it this way
		setTarget(row, col);
		isMoving = true;
		isRunning = doRun;
	}
	
	public void doStop() {
		pathFinder.clearPath();
		isMoving = false;
		isRunning = false;
	}

}
