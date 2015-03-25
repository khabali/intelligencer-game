package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.game.input.GameInput;

public class InputComponent extends Component {

	public GameInput gameInput;

	public InputComponent() {
		gameInput = new GameInput();
	}

	public boolean isPressed(int key) {

		boolean pressed = false;
		switch (key) {
		case GameInput.LEFT:
			pressed = gameInput.leftButton.isPressed();
			break;
		case GameInput.RIGHT:
			pressed = gameInput.rightButton.isPressed();
			break;
		case GameInput.UP:
			pressed = gameInput.upButton.isPressed();
			break;
		case GameInput.DOWN:
			pressed = gameInput.downButton.isPressed();
			break;

		case GameInput.TOUCH:
			pressed = gameInput.touch.isTouched();
			break;

		default:
			break;
		}

		return pressed;
	}

	public Vector2 getTouchScreenPosition() {
		if (gameInput.touch.isDraged() || gameInput.touch.isTouched()) {
			return gameInput.touch.position;
		}

		return null;
	}

	public void update(float deltaTime) {
		this.gameInput.update(deltaTime);
	}
}
