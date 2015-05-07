package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.game.World;
import com.game.component.DirectionComponent;
import com.game.component.PositionComponent;
import com.game.component.RadarComponent;
import com.game.component.tag.PlayerComponentTag;
import com.game.map.Map;

public class RadarSystem extends EntityProcessingSystem {

	//
	private static final String tag = "RadarSystem";

	// Component Mapper
	private @Mapper
	ComponentMapper<RadarComponent> cmRadar;
	private @Mapper
	ComponentMapper<PositionComponent> cmPosition;
	private @Mapper
	ComponentMapper<DirectionComponent> cmDirection;
	private @Mapper
	ComponentMapper<PlayerComponentTag> cmPlayerTag;

	//
	private OrthographicCamera camera;
	private Map map;

	public RadarSystem(OrthographicCamera cam) {
		super(Aspect.getAspectForAll(RadarComponent.class, PositionComponent.class));
		camera = cam;
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.map = ((Map) ((World) this.world).getTerrain());
		cmPlayerTag = world.getMapper(PlayerComponentTag.class);
	}

	@Override
	protected void process(Entity e) {

		// grab entity info
		RadarComponent cRadar = cmRadar.get(e);
		PositionComponent cPosition = cmPosition.get(e);
		DirectionComponent cDirection = cmDirection.get(e);
		
		// calculate center position to draw radar
		Vector2 v = map.mapToScreen(cPosition.rowPos, cPosition.colPos);
		v.x = v.x + map.getTileWidth() / 2;
		
		cRadar.update(v, cDirection.direction);
		Color radarColor = Color.GREEN;
		if (checkColision(e)) {
			radarColor = Color.RED;
		}

		

		// draw radar circle
		cRadar.draw(camera.combined, radarColor, cDirection.direction);
	}

	private boolean checkColision(Entity e) {
		
		if(cmRadar.has(e)){
			ImmutableBag<Entity> etities = world.getSystem(PlayerAISystem.class).getActives();
			for (int i = 0; i < etities.size(); i++) {
				Entity ePlayer = etities.get(i);
				PositionComponent cPlayerPosition = ePlayer.getComponent(PositionComponent.class);
				Vector2 playerPosition = map.mapToScreen(cPlayerPosition.rowPos, cPlayerPosition.colPos);
				RadarComponent cRadar = cmRadar.get(e);
				if(cRadar.isCollided(playerPosition)){
					Gdx.app.debug(tag, "Player spoted at position : "+ cPlayerPosition.colPos + "," + cPlayerPosition.rowPos);
					return true;
				}
			}
		}
		return false;
	}

}
