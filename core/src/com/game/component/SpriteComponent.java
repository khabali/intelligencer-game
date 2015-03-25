package com.game.component;

import com.artemis.Component;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteComponent extends Component {
	
	//public String name;
	public TextureRegion[][] regions;
	
	public SpriteComponent(String filename, int rows, int cols) {
		//this.name = name;
		Texture sheetTexture = new Texture(filename);
		regions = TextureRegion.split(sheetTexture, sheetTexture.getWidth()/cols, sheetTexture.getHeight()/rows);
	}
	
	public TextureRegion getSprite(int x, int y) {
		return regions[x][y];
	}
	
	public TextureRegion[] getRegions(int x) {
		TextureRegion[] tmp= new TextureRegion[regions[x].length-1];
		for (int i= 0; i< tmp.length; i++) 
			tmp[i]= regions[x][i+1];
		return tmp;
	}
	
	public int getSpriteWidth() {
		return regions[0][0].getRegionWidth();
	}
	
	public int getSpriteHeight() {
		return regions[0][0].getRegionHeight();
	}

}
