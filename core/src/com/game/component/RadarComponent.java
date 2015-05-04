package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public class RadarComponent extends Component {
	
	private static final String tag = "RadarComponent";

	private int visionFieldLenght = 250;
	private int visionFieldDegree = 120;

	public boolean draw = false;

	//
	public ShapeRenderer shapeRender;

	public RadarComponent() {
		this.shapeRender = new ShapeRenderer();
	}

	public void draw(float x, float y, Matrix4 projectionMatrix, Color color,
			Direction direction) {
		
		//Gdx.app.debug(tag, "Direction : " + direction.name());
		//activate transparency
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		this.shapeRender.setProjectionMatrix(projectionMatrix);
		this.shapeRender.begin(ShapeType.Filled);
		this.shapeRender.setColor(color);
		this.shapeRender.getColor().a = 0.2f;
		this.shapeRender.arc(x, y, visionFieldLenght, getStart(direction), visionFieldDegree, visionFieldLenght);
		this.shapeRender.circle(x, y, 2);
		this.shapeRender.end();
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
		/*
		this.shapeRender.begin(ShapeType.Line);
		this.shapeRender.setColor(color);
		this.shapeRender.arc(x, y, visionFieldLenght, getStart(direction), visionFieldDegree, visionFieldLenght);
		this.shapeRender.circle(x, y, 2);
		this.shapeRender.end();*/
		
		
	}
	
	private float getStart(Direction dir){
		
		if(Direction.FRONT == dir){
			return -135;
		}
		
		if(Direction.FRONTLEFT == dir){
			return 180;
		}
		
		if(Direction.FRONTRIGHT == dir){
			return -90;
		}
		
		if(Direction.RIGHT == dir){
			return -45;
		}
	
		
		if(Direction.BACKRIGHT == dir){
			return 0;
		}
		
		if(Direction.BACK == dir){
			return 45;
		}
		
		if(Direction.BACKLEFT == dir){
			return 90;
		}
		
		if(Direction.LEFT == dir){
			return -135;
		}
		
		return 0;
	}

}
