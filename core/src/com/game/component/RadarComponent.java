package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class RadarComponent extends Component {

	private static final String tag = "RadarComponent";

	private Vector2 originePosition = new Vector2();
	private int visionFieldLenght = 250;
	private int visionFieldDegree = 80;
	public boolean draw = false;

	//
	public ShapeRenderer shapeRender;

	// Colision shape
	private Array<Vector2> vertices;

	public RadarComponent() {
		this.shapeRender = new ShapeRenderer();
	}

	public void update(Vector2 v, Direction direction) {
		
		originePosition = v;
		// update shape for collision
		// this algorith is the same for drawing the radar zone
		// #check this.shapeRender.arc(...)
		float theta = (2 * MathUtils.PI * (visionFieldDegree / 360.0f)) / 1;
		float cos = MathUtils.cos(theta);
		float sin = MathUtils.sin(theta);
		float cx = visionFieldLenght * MathUtils.cos(getStart(direction) * MathUtils.degreesToRadians);
		float cy = visionFieldLenght * MathUtils.sin(getStart(direction) * MathUtils.degreesToRadians);

		vertices = new Array<Vector2>();
		vertices.add(v);
		vertices.add(new Vector2(v.x+cx, v.y+cy));
		//
		float temp = cx;
		cx = cos * cx - sin * cy;
		cy = sin * temp + cos * cy;
		vertices.add(new Vector2(v.x+cx, v.y+cy));
	}

	public void draw(Matrix4 projectionMatrix, Color color, Direction direction) {

		// Gdx.app.debug(tag, "Direction : " + direction.name());
		// activate transparency
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		this.shapeRender.setProjectionMatrix(projectionMatrix);
		this.shapeRender.begin(ShapeType.Filled);
		this.shapeRender.setColor(color);
		this.shapeRender.getColor().a = 0.2f;
		this.shapeRender.arc(originePosition.x, originePosition.y, visionFieldLenght, getStart(direction), visionFieldDegree, 15);
		this.shapeRender.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	/**
	 * Check if a Vector2 is in the radar zone
	 * 
	 * @param pos
	 * @return true if the Vector2 (position) is in the radar zone
	 */
	public boolean isCollided(Vector2 pos) {

		return Intersector.isPointInPolygon(vertices, pos);
	}


	private float getStart(Direction dir) {

		float start = 0;
		if (Direction.FRONT == dir) {
			start = -135;
		}

		if (Direction.FRONTLEFT == dir) {
			start = 180;
		}

		if (Direction.FRONTRIGHT == dir) {
			start = -90;
		}

		if (Direction.RIGHT == dir) {
			start = -45;
		}

		if (Direction.BACKRIGHT == dir) {
			start = 0;
		}

		if (Direction.BACK == dir) {
			start = 45;
		}

		if (Direction.BACKLEFT == dir) {
			start = 90;
		}

		if (Direction.LEFT == dir) {
			start = -135;
		}

		return start + (visionFieldDegree / 2);
	}

}
