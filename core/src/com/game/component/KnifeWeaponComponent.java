package com.game.component;

public class KnifeWeaponComponent extends WeaponComponent {
	
	public KnifeWeaponComponent(int acode, boolean active) {
		this.animationCode = acode;
		this.isActive = active;
	}

	@Override
	public void doShot(int row, int col) {
		
	}

}
