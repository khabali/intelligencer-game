package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.World;
import com.game.component.AttackComponent;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;
import com.game.component.StateComponent;
import com.game.component.tag.PlayerComponentTag;
import com.game.entity.state.PlayerEntityState;
import com.game.input.GameInput;
import com.game.input.TouchButton;
import com.game.map.Map;

public class PlayerAISystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<PlayerComponentTag> playerComponentMapper;
	@Mapper ComponentMapper<StateComponent> stateComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
	@Mapper ComponentMapper<PositionComponent> positionComponentMapper;
	@Mapper ComponentMapper<DirectionComponent> directionComponentMapper;
	@Mapper ComponentMapper<SpriteComponent> spriteComponentMapper;
	@Mapper ComponentMapper<AttackComponent> attackComponentMapper;
	
	private StateComponent state;
	private MovementComponent movement;
	private PositionComponent position;
	private DirectionComponent direction;
	private SpriteComponent sprite;
	private AttackComponent attack;
	
	private OrthographicCamera camera;
	private Map map;
	
	private Entity targetToKill = null;

	public PlayerAISystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(PlayerComponentTag.class));
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		this.map = ((Map)((World)this.world).getTerrain());
	}

	@Override
	// process entities having player component
	protected void process(Entity e) {
		if (playerComponentMapper.has(e)) {
			state = stateComponentMapper.get(e);
			movement = movementComponentMapper.get(e);
			position = positionComponentMapper.get(e);
			//direction = directionComponentMapper.get(e);
			//sprite = spriteComponentMapper.get(e);
			attack = attackComponentMapper.get(e);

			processInput();
			//stateControl();
		}	
	}
	
	private void processInput() {
		TouchButton touch = GameInput.getInstance().getTouchButton();
		if (touch.isTouched()) {
			Vector2 pos = touch.position;
			Vector3 v = camera.unproject(new Vector3(pos.x, pos.y, 0));
			Vector2 v2 = map.screenToMap(v.x, v.y);

			if (v2 == null) return; // out of map
			
			int row = (int)v2.x;
			int col = (int)v2.y;
			attack.targetToKill = ((World)this.world).getEntityAt(row, col);
			movement.doMoveFromTo((int)position.rowPos, (int)position.colPos, row, col, touch.count > 1);
		}
	}

}
