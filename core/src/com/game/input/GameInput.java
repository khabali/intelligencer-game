package com.game.input;

import com.badlogic.gdx.math.Vector2;
import com.game.utils.IsoMaths;

public class GameInput {

	public InputButton leftButton;
	public InputButton rightButton;
	public InputButton upButton;
	public InputButton downButton;

	//
	public InputButton touch;

	// Buttons keys
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	public static final int TOUCH = 5;

	public GameInput() {
		leftButton = new InputButton();
		rightButton = new InputButton();
		upButton = new InputButton();
		downButton = new InputButton();
		touch = new InputButton();
	}

	public boolean isPressed(int key) {

		boolean pressed = false;
		switch (key) {
		case LEFT:
			pressed = leftButton.isPressed();
			break;
		case RIGHT:
			pressed = rightButton.isPressed();
			break;
		case UP:
			pressed = upButton.isPressed();
			break;
		case DOWN:
			pressed = downButton.isPressed();
			break;

		case TOUCH:
			pressed = touch.isTouched();
			break;

		default:
			break;
		}

		return pressed;
	}

	public boolean isDragged() {
		return touch.isDraged();
	}

	public void update(float deltaTime) {

		if (leftButton.isPressed()
				&& leftButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			leftButton.release();
		}

		if (touch.isTouched()
				&& touch.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			System.out.println("Touch button  " + touch.position.x + "      "
					+ touch.position.y);

			System.out.println(IsoMaths.translateScreenToIso(new Vector2(
					touch.position.x, touch.position.y)));

			touch.release();

		}
	}
}
