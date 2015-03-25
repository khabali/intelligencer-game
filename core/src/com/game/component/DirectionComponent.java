package com.game.component;

import com.artemis.Component;

public class DirectionComponent extends Component {

	public static int FRONT = 0, FRONTRIGHT = 7, FRONTLEFT = 1, BACK = 4,
			BACKRIGHT = 5, BACKLEFT = 3, RIGHT = 6, LEFT = 2;

	public int curdir;

	public DirectionComponent(int dir) {
		curdir = dir;
	}

	public void change(int dir) {
		curdir = dir;
	}

	public void toFront() {
		curdir = FRONT;
	}

	public void toFrontRight() {
		curdir = FRONTRIGHT;
	}

	public void toFrontLeft() {
		curdir = FRONTLEFT;
	}

	public void toBack() {
		curdir = BACK;
	}

	public void toBackRight() {
		curdir = BACKRIGHT;
	}

	public void toBackLeft() {
		curdir = BACKLEFT;
	}

	public void toRight() {
		curdir = RIGHT;
	}

	public void toLeft() {
		curdir = LEFT;
	}

}
