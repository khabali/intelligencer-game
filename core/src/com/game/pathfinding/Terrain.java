package com.game.pathfinding;

public interface Terrain {
	
	public int getWidth();
	public int getHeight();
	
	public boolean passable(int x, int y);
	public int cost(int x, int y);

}
