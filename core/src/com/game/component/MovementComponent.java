package com.game.component;

import com.artemis.Component;
import com.game.pathfinding.PathFinder;
import com.game.pathfinding.Terrain;

public class MovementComponent extends Component {

	public static int IDLE = 0, MOVING = 1;

	public int state;
	public float velocity; // movement speed
	public int targetRow, targetCol;
	public PathFinder pathFinder;

	public MovementComponent(Terrain terrain, int state) {
		this.state = state;
		pathFinder = new PathFinder(terrain);
	}

	public MovementComponent(Terrain terrain, int state, float vel) {
		this.state = state;
		this.velocity = vel;
		pathFinder = new PathFinder(terrain);
	}

	public void setMoving() {
		this.state = MOVING;
	}

	public void setIdle() {
		this.state = IDLE;
	}
	
	public boolean isMoving() {
		return this.state == MOVING;
	}

}
