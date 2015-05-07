package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class RadarComponent extends Component {

	private static final String tag = "RadarComponent";

	private int visionFieldLenght = 250;
	private int visionFieldDegree = 60;
	public boolean draw = false;

	//
	public ShapeRenderer shapeRender;
	
	//Colision shape
	public Polygon polygone;
	public PolygonSprite polygonSprite;
	public Ellipse ellipse = new Ellipse();

	public RadarComponent() {
		this.shapeRender = new ShapeRenderer();
		this.polygone  =  new Polygon();
	}

	public void draw(float x, float y, Matrix4 projectionMatrix, Color color,
			Direction direction) {

		Gdx.app.debug(tag, "Direction : " + direction.name());
		// activate transparency
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		this.shapeRender.setProjectionMatrix(projectionMatrix);
		this.shapeRender.begin(ShapeType.Filled);
		this.shapeRender.setColor(color);
		this.shapeRender.getColor().a = 0.2f;
		this.shapeRender.arc(x, y, visionFieldLenght, getStart(direction),
				visionFieldDegree, visionFieldLenght);
		this.shapeRender.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		this.shapeRender.begin(ShapeType.Line);
		this.shapeRender.setColor(Color.MAROON);
		updateCollisionShape(x, y, getStart(direction));
		this.shapeRender.end();
		
		

	}

	private void updateCollisionShape(float x, float y, float start) {
		
		//
		int segment = Math.max(1, (int)(6 * (float)Math.cbrt(visionFieldLenght) * (visionFieldDegree / 360.0f)));
		float theta = (2 * MathUtils.PI * (visionFieldDegree / 360.0f)) / segment;
		float cos = MathUtils.cos(theta);
		float sin = MathUtils.sin(theta);
		float cx = visionFieldLenght * MathUtils.cos(start * MathUtils.degreesToRadians);
		float cy = visionFieldLenght * MathUtils.sin(start * MathUtils.degreesToRadians);
		
		float [] vertices = new float[6];
		vertices[0] = x;
		vertices[1] = y;
		vertices[2] = x + cx;
		vertices[3] = y + cy;
		//
		float temp = cx;
		cx = cos * cx - sin * cy;
		cy = sin * temp + cos * cy;
		vertices[4] = x + cx;
		vertices[5] = y + cy;
		polygone.setVertices(vertices);
		
		this.shapeRender.polygon(vertices);
		
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
