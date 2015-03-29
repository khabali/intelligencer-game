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
	
	public static Vector2 screenToMap(float x, float y, int tileWidth, int tileHeight) {
		float tileWidthHalf = tileWidth/2;
		float tileHeightHalf = tileHeight/2;
		int mapx = (int)((x / tileWidthHalf + y / tileHeightHalf) / 2);
		int mapy = (int)((y / tileHeightHalf - (x / tileWidthHalf)) / 2);
		return new Vector2(mapx, mapy);
	}
	
	// screen to isometric
	public static Vector2 coorToTab(float x, float y, int tileHeight) {
		Point iso= new Point();
		iso= iso.screentoIsometric(new Point(x, y));
		float isoSide= (float)(tileHeight* 1.414213); // 1.414213 --> SQRT(2)
		int row= (int)(iso.getX()/ isoSide);
		int col= (int)(iso.getY()/ isoSide);
		return new Vector2(col, row);
	}

	private IsoMaths() {

	}

}
