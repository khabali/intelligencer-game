package com.game.input;

import com.badlogic.gdx.math.Vector2;

public class InputButton {

	private boolean mDown;
	private float mLastPressedTime;
	private float mDownTime;
	public Vector2 position = new Vector2(0, 0);

	public void press(float currentTime) {
		if (!mDown) {
			mDown = true;
			mDownTime = currentTime;
		}
		mLastPressedTime = currentTime;
	}

	public void release() {
		mDown = false;
		position.x = 0;
		position.y = 0;
	}

	public final boolean isPressed() {
		return mDown;
	}

	public final float getPressedDuration(float currentTime) {
		return currentTime - mDownTime;
	}

	public final float getLastPressedTime() {
		return mLastPressedTime;
	}

	public final void reset() {
		mDown = false;
		mLastPressedTime = 0.0f;
		mDownTime = 0.0f;
		position.x = 0;
		position.y = 0;
	}
}
