package com.game.component;

import com.artemis.Component;

public abstract class WeaponComponent extends Component {
	
	public int animationCode;
	public int damage;
	public boolean isActive;
	
	public abstract void doShot(int row, int col);

}
