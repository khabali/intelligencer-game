package com.game.component;

import com.artemis.Component;

public class PositionComponent extends Component {

	/**
	 * the default position (start position)
	 */
	public float defaultRow, defaultCol;
	
	/**
	 * Actual position
	 */
	public float rowPos, colPos;

	public PositionComponent(int row, int col) {
		this.rowPos = this.defaultRow =  row;
		this.colPos = this.defaultCol = -col;
	}
	
	public void setDefaultPos(float defaultRow, float defaultCol) {
		this.defaultRow = defaultRow;
		this.defaultCol = defaultCol;
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
