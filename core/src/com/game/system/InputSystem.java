package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.game.component.InputComponent;
import com.game.input.GameInput;

public class InputSystem extends EntityProcessingSystem implements
		InputProcessor {

	@Mapper
	private ComponentMapper<InputComponent> inputCMapper;

	private GameInput gameInput;

	public InputSystem() {
		super(Aspect.getAspectForAll(InputComponent.class));
		Gdx.input.setInputProcessor(this);
	}

	@Override
	protected void process(Entity e) {

		InputComponent intputComponent = inputCMapper.get(e);
		gameInput = intputComponent.gameInput;
		intputComponent.update(Gdx.graphics.getDeltaTime());

	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.LEFT) {
			gameInput.leftButton.press(System.currentTimeMillis());
			System.out.println("Key down Pressed from input processor");
		}
		if (keycode == Keys.RIGHT) {

		}
		if (keycode == Keys.UP) {

		}
		if (keycode == Keys.DOWN) {

		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		gameInput.touch.touch(System.currentTimeMillis(), screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		System.out.println("Mousse draged " + screenX + "   " + screenY
				+ "      " + pointer);

		gameInput.touch.drag(System.currentTimeMillis(), screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
