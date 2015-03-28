package com.game.input;

import com.badlogic.gdx.Gdx;

public class GameInput {

	private final String tag = getClass().getName();

	private static GameInput _instance;

	public static GameInput getInstance() {
		if (_instance == null) {
			_instance = new GameInput();
		}
		return _instance;
	}

	private InputButton leftButton;
	private InputButton rightButton;
	private InputButton upButton;
	private InputButton downButton;
	private TouchButton touchButton = new TouchButton();

	private GameInput() {
		leftButton = new InputButton();
		rightButton = new InputButton();
		upButton = new InputButton();
		downButton = new InputButton();
		touchButton = new TouchButton();
	}

	public void update(float deltaTime) {

		if (leftButton.isPressed()
				&& leftButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			leftButton.release();
		}

		if (touchButton.isTouched()
				|| touchButton.isPaned()
				|| touchButton.isDraged()
				&& touchButton.getTouchDuration(System.currentTimeMillis()) >= deltaTime * 2.0f) {
			Gdx.app.debug(tag, "Touch released ...");
			touchButton.release();
		}
	}

	public TouchButton getTouchButton() {
		return touchButton;
	}

	public InputButton getLeftButton() {
		return leftButton;
	}

	public InputButton getRightButton() {
		return rightButton;
	}

	public InputButton getDownButton() {
		return downButton;
	}

	public InputButton getUpButton() {
		return upButton;
	}
}
