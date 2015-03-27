package com.game.system;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputProcessor;
import com.game.input.GameInput;

public class InputSystem extends VoidEntitySystem implements
		InputProcessor, GestureListener {

	private GameInput gameInput;
	OrthographicCamera camera;

	public InputSystem(OrthographicCamera camera) {
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(multiplexer);
		this.camera = camera;
	}
	
	@Override
	protected void processSystem() {
		gameInput = new GameInput();
		gameInput.update(Gdx.graphics.getDeltaTime());
	}
	
	public Vector2 getTouchScreenPosition() {
		if (gameInput.touch.isDraged() || gameInput.touch.isTouched()) {
			return gameInput.touch.position;
		}

		return null;
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

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		System.out.println("Mousse draged " + deltaX + "   " + deltaY
				+ "      ");
		camera.position.add(-deltaX* camera.zoom, deltaY* camera.zoom, 0);
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
