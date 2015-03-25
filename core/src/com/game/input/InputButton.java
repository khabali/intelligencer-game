package com.game.input;

import com.badlogic.gdx.math.Vector2;

public class InputButton {

	private boolean mDown;
	private boolean mTouch;
	private boolean mDrag;
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

	public void touch(float currentTime, int x, int y) {
		if (!mTouch) {
			mTouch = true;
			mDownTime = currentTime;
		}
		mLastPressedTime = currentTime;
		position.x = x;
		position.y = y;
	}

	public void drag(float currentTime, int x, int y) {
		if (!mDrag) {
			mDrag = true;
			mDownTime = currentTime;
		}
		mLastPressedTime = currentTime;
		position.x = x;
		position.y = y;
	}

	public void release() {
		mDown = false;
		mDrag = false;
		mTouch = false;
		position.x = 0;
		position.y = 0;
	}

	public final boolean isPressed() {
		return mDown;
	}

	public final boolean isTouched() {
		return mTouch;
	}

	public final boolean isDraged() {
		return mDrag;
	}

	public final float getPressedDuration(float currentTime) {
		return currentTime - mDownTime;
	}

	public final float getLastPressedTime() {
		return mLastPressedTime;
	}

	public final void reset() {
		mDown = false;
		mDrag = false;
		mTouch = false;
		mLastPressedTime = 0.0f;
		mDownTime = 0.0f;
		position.x = 0;
		position.y = 0;
	}
}
