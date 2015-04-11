package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class SpriteComponent extends Component {

	public String name;
	private TextureAtlas textureAtlas;

	public SpriteComponent(TextureAtlas textureAtlas, String name) {
		this.textureAtlas = textureAtlas;
		this.name = name;
	}

	public Array<Sprite> getSprites(Direction dir, Action action) {
		String regionName = dir.name() + "/" + name + "_" + action.name();
		return textureAtlas.createSprites(regionName);
	}

	public int spriteWidth(Direction dir, Action action) {
		String regionName = dir.name() + "/" + name + "_" + action.name();
		return textureAtlas.findRegion(regionName).getRegionWidth();
	}

	public int spriteHeight(Direction dir, Action action) {
		String regionName = dir.name() + "/" + name + "_" + action.name();
		return textureAtlas.findRegion(regionName).getRegionHeight();
	}

}
