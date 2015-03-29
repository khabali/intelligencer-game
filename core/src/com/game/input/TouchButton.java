package com.game.input;

import com.badlogic.gdx.math.Vector2;

public class TouchButton {

	private boolean touch;
	private boolean drag;
	private boolean pan;
	private float lastTouchedTime;
	private float touchTime;
	public Vector2 position = new Vector2(0, 0);
	public Vector2 delta = new Vector2(0, 0);

	public void touch(float currentTime, float x, float y) {
		if (!touch) {
			touch = true;
			touchTime = currentTime;
		}
		lastTouchedTime = currentTime;
		position.x = x;
		position.y = y;
	}

	public void pan(float currentTime, float x, float y, float deltaX,
			float deltaY) {
		if (!pan) {
			pan = true;
			touchTime = currentTime;
		}
		lastTouchedTime = currentTime;
		position.x = x;
		position.y = y;
		delta.x = deltaX;
		delta.y = deltaY;
	}

	public void drag(float currentTime, int x, int y) {
		if (!drag) {
			drag = true;
			touchTime = currentTime;
		}
		lastTouchedTime = currentTime;
		position.x = x;
		position.y = y;
	}

	public void release() {
		drag = false;
		touch = false;
		pan = false;
		position.x = 0;
		position.y = 0;
	}

	public final boolean isTouched() {
		return touch;
	}

	public final boolean isDraged() {
		return drag;
	}

	public boolean isPaned() {
		return pan;
	}

	public final float getTouchDuration(float currentTime) {
		return currentTime - touchTime;
	}

	public final float getLastTouchTime() {
		return lastTouchedTime;
	}

	public final void reset() {
		drag = false;
		touch = false;
		pan = false;
		lastTouchedTime = 0.0f;
		touchTime = 0.0f;
		position.x = 0;
		position.y = 0;
	}
}
