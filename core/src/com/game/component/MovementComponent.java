package com.game.component;

import com.artemis.Component;

public class MovementComponent extends Component {
	
	public static int IDLE= 0, MOVING= 1;
	
	public int state;
	public float velocity; //movement speed	
	public int targetRow, targetCol;
	
	public MovementComponent(int state) {
		this.state = state;
	}
	
	public MovementComponent(int state, float vel) {
		this.state = state;
		this.velocity = vel;
	}
	
	public void doMove(int row, int col) {
		this.state = MOVING;
		targetRow = row;
		targetCol = col;
	}
	
	public void doStop() {
		this.state = IDLE;
	}

}
