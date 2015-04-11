package com.game.component;

import com.artemis.Component;
import com.game.pathfinding.PathFinder;
import com.game.pathfinding.Terrain;

public class MovementComponent extends Component {

	public Action state;
	public float velocity; // movement speed
	public int targetRow, targetCol;
	public PathFinder pathFinder;

	public MovementComponent(Terrain terrain, Action state) {
		this.state = state;
		pathFinder = new PathFinder(terrain);
	}

	public MovementComponent(Terrain terrain, Action state, float vel) {
		this.state = state;
		this.velocity = vel;
		pathFinder = new PathFinder(terrain);
	}

	public void setMoving() {
		this.state = Action.walk;
	}

	public void setIdle() {
		this.state = Action.idle;
	}

	public boolean isMoving() {
		return this.state == Action.walk;
	}

}
