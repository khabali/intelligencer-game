package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.game.World;
import com.game.component.DirectionComponent;
import com.game.component.PositionComponent;
import com.game.component.RadarComponent;
import com.game.component.SpriteComponent;
import com.game.component.StateComponent;
import com.game.map.Map;

public class RadarSystem extends EntityProcessingSystem {
	
	//
	private static final String tag = "RadarSystem";

	// Component Mapper
	private @Mapper ComponentMapper<RadarComponent> cmRadar;
	private @Mapper ComponentMapper<PositionComponent> cmPosition;
	private @Mapper ComponentMapper<SpriteComponent> cmSprite;
	private @Mapper ComponentMapper<StateComponent> cmState;
	private @Mapper ComponentMapper<DirectionComponent> cmDirection;
	
	//
	private OrthographicCamera camera;
	private Map map;
	
	public RadarSystem(OrthographicCamera cam) {
		super(Aspect.getAspectForAll(RadarComponent.class, PositionComponent.class, SpriteComponent.class, StateComponent.class, DirectionComponent.class));
		camera = cam;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.map = ((Map)((World)this.world).getTerrain());
	}
	
	

	@Override
	protected void process(Entity e) {
		if(cmRadar.has(e)){
			
			// grab entity info
			RadarComponent cRadar = cmRadar.get(e);
			PositionComponent cPosition = cmPosition.get(e);
			SpriteComponent cSprite = cmSprite.get(e);
			DirectionComponent cDirection = cmDirection.get(e);
			StateComponent cState = cmState.get(e);
			
			
			//TODO check if there is an entity in the radius
			//TODO change the color to red
			Color radarColor = Color.RED;
			
			//ELSE 
			radarColor = Color.CYAN;
			
			//calculate center position to draw radar
			int spriteWidth = cSprite.spriteWidth(cDirection.direction, cState.state);
			Vector2 v = map.mapToScreen(cPosition.rowPos, cPosition.colPos, cRadar.radius);
			
			// draw radar circle
			cRadar.draw(v.x +(spriteWidth / 2), v.y + (spriteWidth /2), camera.combined, radarColor);
		}
		
	}

}
