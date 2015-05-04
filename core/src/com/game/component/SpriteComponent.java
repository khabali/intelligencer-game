package com.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.game.entity.state.IEntityState;
import com.game.resources.AssetsManager;

public class SpriteComponent extends Component {

	public String name;
	private TextureAtlas textureAtlas;
	public boolean animationFinished;
	
	private IEntityState curState = null;
	private Direction curDirection = null;

	public SpriteComponent(String texture, String name) {
		this.textureAtlas = AssetsManager.manager.get(texture, TextureAtlas.class);
		this.name = name;
	}

	public Array<Sprite> getSprites(Direction dir, IEntityState st) {
		curDirection = dir;
		curState = st;
		String regionName = dir.name() + "/" + name + "_" + st.name();
		return textureAtlas.createSprites(regionName);
	}
	
	public boolean isAnimationChanged(Direction d, IEntityState s) {
		if (s != curState || d != curDirection) {
			return true;
		}
		return false;
	}

	public int spriteWidth(Direction dir, IEntityState s) {
		String regionName = dir.name() + "/" + name + "_" + s.name();
		return textureAtlas.findRegion(regionName).getRegionWidth();
	}

	public int spriteHeight(Direction dir, IEntityState s) {
		String regionName = dir.name() + "/" + name + "_" + s.name();
		return textureAtlas.findRegion(regionName).getRegionHeight();
	}

}
