package com.game.component;

import com.artemis.Component;
import com.game.pathfinding.PathFinder;
import com.game.pathfinding.Terrain;

public class MovementComponent extends Component {

	public static int IDLE = 0, MOVING = 1;

	public int state;
	public float velocity; // movement speed
	public int targetRow, targetCol;

	public MovementComponent(int state) {
		this.state = state;
	}

	public MovementComponent(int state, float vel) {
		this.state = state;
		this.velocity = vel;
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
