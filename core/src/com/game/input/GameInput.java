package com.game.input;


public class GameInput {

	private final String tag = getClass().getName();

	private static GameInput _instance;

	public static GameInput getInstance() {
		if (_instance == null) {
			_instance = new GameInput();
		}
		return _instance;
	}

	private InputButton enterButton;
	private InputButton escapeButton;
	private InputButton aButton;
	private TouchButton touchButton;

	private GameInput() {
		enterButton = new InputButton();
		escapeButton = new InputButton();
		aButton = new InputButton();
		touchButton = new TouchButton();
	}

	public void update(float deltaTime) {

		if (escapeButton.isPressed()
				&& escapeButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			escapeButton.release();
		}

		if (enterButton.isPressed()
				&& enterButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			enterButton.release();
		}
		
		if (aButton.isPressed()
				&& aButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			aButton.release();
		}

		if (touchButton.isTouched()
				|| touchButton.isPaned()
				|| touchButton.isDraged()
				&& touchButton.getTouchDuration(System.currentTimeMillis()) >= deltaTime * 2.0f) {
			//Gdx.app.debug(tag, "Touch released ...");
			touchButton.release();
		}
	}

	public TouchButton getTouchButton() {
		return touchButton;
	}

	public InputButton getEnterButton() {
		return enterButton;
	}

	public InputButton getEscapeButton() {
		return escapeButton;
	}
	
	public InputButton getAButton() {
		return aButton;
	}
}
