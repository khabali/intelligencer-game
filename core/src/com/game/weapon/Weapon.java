package com.game.weapon;

import com.artemis.Component;

public abstract class Weapon {
	
	public String name;
	public int damage;
	public boolean isActive;
	
	public abstract void doShot(int row, int col);

}
