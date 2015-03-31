package com.game.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameAssetsManager {

	private AssetManager manager;

	// Ressource Type
	public static final int TEXTURE = 1;
	public static final int BIMAP_FONT = 2;
	public static final int MUSIC = 3;

	public GameAssetsManager() {
		manager = new AssetManager();
	}

	public void loadRessource(String fileName, int ressourceType) {

		switch (ressourceType) {
		case TEXTURE:
			this.manager.load(fileName, Texture.class);
			break;

		case BIMAP_FONT:
			this.manager.load(fileName, BitmapFont.class);
			break;

		case MUSIC:
			this.manager.load(fileName, Music.class);
			break;

		default:
			break;
		}
	}

	/**
	 * Recuperer une ressource Texture <br>
	 * Si la ressource n'est chargé cette methode retourne NULL
	 * 
	 * @param fileName
	 * @return
	 */
	public Texture getTextureRessource(String fileName) {
		if (manager.isLoaded(fileName)) {
			return this.manager.get(fileName, Texture.class);
		}
		return null;
	}

	/**
	 * Recuperer une ressource BitmapFont <br>
	 * Si la ressource n'est chargé cette methode retourne NULL
	 * 
	 * @param fileName
	 * @return
	 */
	public BitmapFont getBitmapFontRessource(String fileName) {
		if (manager.isLoaded(fileName)) {
			return this.manager.get(fileName, BitmapFont.class);
		}
		return null;
	}

	/**
	 * Recuperer une ressource Music <br>
	 * Si la ressource n'est chargé cette methode retourne NULL
	 * 
	 * @param fileName
	 * @return
	 */
	public Music getMusicRessource(String fileName) {
		if (manager.isLoaded(fileName)) {
			return this.manager.get(fileName, Music.class);
		}
		return null;
	}

	/**
	 * 
	 * Continue le chargement des ressources
	 * 
	 * @return true quand le chargement est terminé
	 */
	public boolean update() {
		return this.manager.update();
	}

	/**
	 * Retourne un numéro entre 0 et 1 indiquant l'état d'avancement du
	 * chargement des ressources
	 * 
	 * @return
	 */
	public float getProgress() {
		return manager.getProgress();
	}

	/**
	 * Bloquer le traitement en cours jusqu'a la fin du chargement des
	 * ressources
	 */
	public void finishLoading() {
		this.manager.finishLoading();
	}

	/**
	 * Liberer toutes les ressources chargées par ce manager
	 */
	public void clearAllResources() {
		this.manager.clear();
	}

	/**
	 * Liberer toutes les ressources chargées par ce manager et detruire cette
	 * instance du GameAssetsManager
	 */
	public void dispose() {
		this.manager.dispose();
	}

}
