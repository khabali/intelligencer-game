package com.game.component;

import java.util.ArrayList;

import com.artemis.Component;
import com.artemis.Entity;
import com.game.weapon.Weapon;

public class AttackComponent extends Component {
	
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	public Entity targetToKill = null;
	public boolean isAttacking = false;
	
	public void registerWeapon(Weapon w) {
		weapons.add(w);
	}
	
	public void registerActivateWeapon(Weapon w) {
		weapons.add(w);
		activateWeapon(w);
	}
	
	public void unregisterWeapon(Weapon w) {
		weapons.remove(w);
	}
	
	public void activateWeapon(Weapon w) {
		for (int i=0; i<weapons.size(); i++) {
			weapons.get(i).isActive = false;
		}
		w.isActive = true;
	}
	
	public Weapon getWeaponByName(String wName) {
		for (int i=0; i<weapons.size(); i++) {
			if (weapons.get(i).name.equals(wName)) {
				return weapons.get(i);
			}
		}
		return null;
	}
	
	public Weapon getActiveWeapon() {
		for (int i=0; i<weapons.size(); i++) {
			if (weapons.get(i).isActive) return weapons.get(i);
		}
		System.out.println("No weapon is active");
		return null;
	}
	
	public boolean gotWeapon() {
		return weapons.size() >= 1;
	}

}
