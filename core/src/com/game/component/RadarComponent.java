package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public class RadarComponent extends Component {

	public int radius = 100;
	
	public boolean draw = false;

	//
	ShapeRenderer shapeRender;

	public RadarComponent() {
		this.shapeRender = new ShapeRenderer();
	}
	
	public void draw(float x, float y, Matrix4 projectionMatrix, Color color){
		this.shapeRender.setProjectionMatrix(projectionMatrix);
		this.shapeRender.begin(ShapeType.Line);
		this.shapeRender.setColor(color);
		this.shapeRender.circle(x , y, radius);
		this.shapeRender.end();
	}

}
