package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.World;
import com.game.component.AttackComponent;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PlayerComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;
import com.game.component.State;
import com.game.component.StateComponent;
import com.game.input.GameInput;
import com.game.input.TouchButton;
import com.game.map.Map;

public class PlayerAISystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<PlayerComponent> playerComponentMapper;
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
		super(Aspect.getAspectForAll(PlayerComponent.class, MovementComponent.class));
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
		}	
	}
	
	private void processInput() {
		TouchButton touch = GameInput.getInstance().getTouchButton();
		if (touch.isTouched()) {
			Vector2 pos = touch.position;
			Vector3 v = camera.unproject(new Vector3(pos.x, pos.y, 0));
			Vector2 v2 = map.screenToMap(v.x, v.y);
			int row = (int)v2.x;
			int col = (int)v2.y;
			attack.targetToKill = ((World)this.world).getEntityAt(row, col);
			movement.doMoveFromTo((int)position.rowPos, (int)position.colPos, row, col, touch.count > 1);
		}
	}


}
