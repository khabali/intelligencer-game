package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class CameraComponent extends Component {

	private OrthographicCamera cam;

	public static final int VIEWPORT_WIDTH = 480;
	public static final int VIEWPORT_HEIGHT = 320;
	public static final int SCALE = 2;
	public static final float Z_INDEX = 0 * SCALE;

	public CameraComponent() {
		this.cam = new OrthographicCamera(SCALE * VIEWPORT_WIDTH, SCALE
				* VIEWPORT_HEIGHT);
		this.cam.setToOrtho(false);
	}

	public void updatePosition(float x, float y) {
		this.cam.position.set(x, y, Z_INDEX);
		this.cam.update();
	}

	public void lookAt(float x, float y) {
		System.out.println("Camera look at : " + x + "   " + y);
		this.cam.lookAt(x, y, Z_INDEX);
		cam.update();
	}

	public void translate(float x, float y) {
		System.out.println("Camera translate to : " + x + "   " + y);
		this.cam.translate(x, y);
	}

	public void translate(Vector3 vec) {
		this.cam.translate(vec);
	}

	public OrthographicCamera getCam() {
		return this.cam;
	}

}
