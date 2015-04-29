package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class SpriteComponent extends Component {

	public String name;
	private TextureAtlas textureAtlas;
	public boolean animationFinished;
	
	private State curState = null;
	private Direction curDirection = null;

	public SpriteComponent(TextureAtlas textureAtlas, String name) {
		this.textureAtlas = textureAtlas;
		this.name = name;
	}

	public Array<Sprite> getSprites(Direction dir, State st) {
		curDirection = dir;
		curState = st;
		String regionName = dir.name() + "/" + name + "_" + st.name();
		return textureAtlas.createSprites(regionName);
	}
	
	public boolean isAnimationChanged(Direction d, State s) {
		if (s != curState || d != curDirection) {
			return true;
		}
		return false;
	}

	public int spriteWidth(Direction dir, State action) {
		String regionName = dir.name() + "/" + name + "_" + action.name();
		return textureAtlas.findRegion(regionName).getRegionWidth();
	}

	public int spriteHeight(Direction dir, State action) {
		String regionName = dir.name() + "/" + name + "_" + action.name();
		return textureAtlas.findRegion(regionName).getRegionHeight();
	}

}
