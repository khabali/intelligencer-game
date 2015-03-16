package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.isomap.IsoMap;

public class MyGdxGame extends ApplicationAdapter {
	
	
	OrthographicCamera orthographicCamera;
	IsoMap isoMap;
	
	int defaultWeight = 480;
	int defaultHeight = 320;
	int scale = 1;
	
	@Override
	public void create () {
		orthographicCamera  = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, defaultWeight * scale, defaultHeight * scale);	
		isoMap = new IsoMap("iso_test_map.tmx");
		isoMap.setOrthographicCamera(orthographicCamera);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		isoMap.render();
		
	}
}
