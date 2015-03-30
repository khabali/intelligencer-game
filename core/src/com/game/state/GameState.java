package com.game.state;

public abstract class GameState {

	protected GameStateManager gsm;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	/**
	 * Cette methode permet d'initialiser l'état
	 */
	public abstract void init();

	/**
	 * Cette methode permet de mettre à jour les données traitées par cet etat
	 */
	public abstract void update();

	/**
	 * Cette methode permet de dessiner l'etat
	 */
	public abstract void draw();

	/**
	 * cette methode permet de gerer les entrées du jeux pour cette état
	 */
	public abstract void handleInput();

	/**
	 * Cette methode permet de gerer la taille de cet etat
	 * 
	 * @param x
	 * @param y
	 */
	public abstract void resize(int width, int height);

	/**
	 * Cette methode permet de gerer le pause de cet etat
	 */
	public abstract void pause();

	/**
	 * cette methode permet de gerer la relance de l'etat
	 */
	public abstract void resume();

	/**
	 * cette methode permet de liberer les ressources gerées par cet etat
	 */
	public abstract void dispose();

}
