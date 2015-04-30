package com.game.component;

import com.artemis.Component;

public class PositionComponent extends Component {
	public float x, y;
	public float rowPos, colPos;

	public PositionComponent(int row, int col) {
		this.rowPos = row;
		this.colPos = -col;
	}

	public void setRowPos(float row) {
		this.rowPos = row;
	}

	public void setColPos(float col) {
		this.colPos = col;
	}
	
	public boolean equals(PositionComponent pos) {
		return rowPos == pos.rowPos && colPos == pos.colPos;
	}
}
