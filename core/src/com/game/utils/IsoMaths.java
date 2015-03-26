package com.game.utils;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public final class IsoMaths {

	private static Matrix4 isoTransform;
	private static Matrix4 invIsotransform;
	private static Vector3 screenPos = new Vector3();

	// Initialisation
	static {
		// create the isometric transform
		isoTransform = new Matrix4();
		isoTransform.idt();

		// isoTransform.translate(0, 32, 0);
		isoTransform.scale((float) (Math.sqrt(2.0) / 2.0),
				(float) (Math.sqrt(2.0) / 4.0), 1.0f);
		isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

		// ... and the inverse matrix
		invIsotransform = new Matrix4(isoTransform);
		invIsotransform.inv();
	}

	public static Vector3 translateScreenToIso(Vector2 vec) {
		screenPos.set(vec.x, vec.y, 0);
		screenPos.mul(invIsotransform);

		return screenPos;
	}

	private IsoMaths() {

	}

}
