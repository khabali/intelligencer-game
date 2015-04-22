package com.game.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsManager {

	public static final AssetManager manager = new AssetManager();

	// PRICIPAL MENU RESSOURCES
	/** Menu background */
	public static final String MENU_BG = "menu_principal/virtual_menu.png";

	// les sprites ressources des personnages utilisés dans le jeux
	/** joueur principal sprite sheet */
	public static final String SPRITE_SHEET_WOMEN = "sprites/woman.png";
	/** villager sprites sheet */
	public static final String SPRITE_SHEET_VILLAGER = "sprites/villager.txt";
	public static final String SPRITE_SHEET_ARCHER = "sprites/archer.txt";

	public static void load() {
		// Menu
		manager.load(MENU_BG, Texture.class);

		// sprites
		manager.load(SPRITE_SHEET_WOMEN, Texture.class);
		manager.load(SPRITE_SHEET_VILLAGER, TextureAtlas.class);
		manager.load(SPRITE_SHEET_ARCHER, TextureAtlas.class);
	}

	public static void dispose() {
		manager.dispose();
	}

}
