package com.game.weapon;


public abstract class Weapon {
	
	public String name;
	public int damage;
	public boolean isActive;
	
	public abstract void doShot(int row, int col);

}
