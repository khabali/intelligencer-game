package com.game.component;

import com.artemis.Component;

public class DirectionComponent extends Component {

	public Direction direction;

	public DirectionComponent(Direction dir) {
		direction = dir;
	}

	public void change(Direction dir) {
		direction = dir;
	}
}
