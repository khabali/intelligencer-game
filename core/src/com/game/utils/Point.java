package com.game.utils;

public class Point {
	static final double SIN_MIN_45= -0.707106 ;
	static final double COS_MIN_45=  0.707106 ;
	
	private float x, y;
	
	public Point(float x, float y) {
		this.x= x;
		this.y= y;
	}
	
	public Point() {
		this.x= 0;
		this.y= 0;
	}

	public void setX(float f) {this.x= f; }	
	public void setY(float y) { this.y= y; }
	
	public float getX() { return this.x; }	
	public float getY() { return this.y; }
	
	public Point screentoIsometric(Point p) {
		Point iso= new Point();

		//initial translation
		p.x -= 32;
		p.y -= 0;

		//back to 45�
		p.y*= 2;

		//back to orthogonal
		iso.x= (float) (p.x* COS_MIN_45- p.y* SIN_MIN_45);
		iso.y= (float) (p.x* SIN_MIN_45+ p.y* COS_MIN_45);

		return iso;
	}
}
