package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class CameraComponent extends Component {

	private OrthographicCamera cam;

	public CameraComponent(float screenW, float screenH) {
		this.cam = new OrthographicCamera(screenW / 50,
				50 * (screenH / screenW));
		this.cam.setToOrtho(false);
	}

	public void resize(float screenW, float screenH) {
		this.cam.viewportWidth = screenW / 50;
		this.cam.viewportHeight = 50 * (screenH / screenW);
		this.cam.update();
	}

	public void updatePosition(float x, float y) {
		this.cam.position.set(x, y, 0);
		this.cam.update();
	}

	public void lookAt(float x, float y) {
		System.out.println("Camera look at : " + x + "   " + y);
		this.cam.lookAt(x, y, 0);
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
