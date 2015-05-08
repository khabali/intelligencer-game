package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.game.World;
import com.game.component.Direction;
import com.game.component.DirectionComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;
import com.game.component.StateComponent;
import com.game.map.Map;

public class SpriteRenderSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<SpriteComponent> spriteComponentMapper;
	@Mapper ComponentMapper<PositionComponent> positionComponentMapper;
	@Mapper ComponentMapper<StateComponent> stateComponentMapper;
	@Mapper ComponentMapper<DirectionComponent> directionComponentMapper;
	
	
	private Map map;
	

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private  Animation animation;
	private float frameTime = 0;

	//Debug
	private ShapeRenderer shapeRender = new ShapeRenderer();

	public SpriteRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(SpriteComponent.class,
				PositionComponent.class, StateComponent.class));
		this.camera = camera;
	}

	@Override
	protected void initialize() {
		batch = new SpriteBatch();
		this.map = ((Map)((World)this.world).getTerrain());
	}
	

	@Override
	protected void begin() {
		batch.begin();
	}

	@Override
	// process only entities that have a sprite and a position
	protected void process(Entity e) {
		if (spriteComponentMapper.has(e) && positionComponentMapper.has(e)) {
			// set camera
			batch.setProjectionMatrix(camera.combined);
			// get obligatory components
			SpriteComponent sprite = spriteComponentMapper.getSafe(e);
			PositionComponent position = positionComponentMapper.getSafe(e);
			StateComponent state = stateComponentMapper.get(e);
			// set default direction
			Direction dir = Direction.NONE;
			// get entity state and direction if any
			if (directionComponentMapper.has(e)) dir = directionComponentMapper.get(e).direction;
			// do we need to reload sprites?
			if (sprite.isAnimationChanged(dir, state.currentState)) {
				frameTime = 0; // reinitialise frame counter
				// load new animation regions
				//animation = new Animation(0.090f, sprite.getSprites(dir, stat));
			}
			animation = new Animation(0.090f, sprite.getSprites(dir, state.currentState));
			frameTime += Gdx.graphics.getDeltaTime();
			
				
			
			// calculate x and y from row and column
			int spriteWidth = sprite.spriteWidth(dir, state.currentState);
			Vector2 v = map.mapToScreen(position.rowPos, position.colPos);
			batch.draw(animation.getKeyFrame(frameTime, true), v.x,  v.y);
			
			//debug centred position 
//			shapeRender.setProjectionMatrix(camera.combined);
//			shapeRender.identity();
//			shapeRender.begin(ShapeType.Line);
//			shapeRender.circle(v.x + map.getTileWidth() / 2, v.y, 5);
//			shapeRender.end();

			
			// check if animation cycle is finished
			if (animation.isAnimationFinished(frameTime)) {
				sprite.animationFinished = true;
			}else {
				sprite.animationFinished = false;
			}
		}
	}

	@Override
	protected void end() {
		batch.end();
	}

}
