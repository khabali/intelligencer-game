package com.game.system;

import javax.crypto.spec.PSource;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.component.Direction;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;
import com.game.component.State;
import com.game.component.StateComponent;

public class SpriteRenderSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<SpriteComponent> spriteComponentMapper;
	@Mapper ComponentMapper<PositionComponent> positionComponentMapper;
	@Mapper ComponentMapper<StateComponent> stateComponentMapper;
	@Mapper ComponentMapper<DirectionComponent> directionComponentMapper;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private  Animation animation;
	private float frameTime = 0;

	// private Bag<AtlasRegion> regionsByEntity; // collection of regions of
	// every entity to render

	public SpriteRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(SpriteComponent.class,
				PositionComponent.class, MovementComponent.class,
				DirectionComponent.class));
		this.camera = camera;
	}

	@Override
	protected void initialize() {
		batch = new SpriteBatch();
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
			// set default state and direction
			Direction dir = Direction.NONE;
			State stat = State.none;
			// get entity state and direction if any
			if (directionComponentMapper.has(e)) dir = directionComponentMapper.get(e).direction;
			if (stateComponentMapper.has(e)) stat = stateComponentMapper.get(e).state;
			// do we need to reload sprites?
			if (sprite.isAnimationChanged(dir, stat)) {
				frameTime = 0; // reinitialise frame counter
				// load new animation regions
				//animation = new Animation(0.090f, sprite.getSprites(dir, stat));
			}
			animation = new Animation(0.090f, sprite.getSprites(dir, stat));
			frameTime += Gdx.graphics.getDeltaTime();
			batch.draw(animation.getKeyFrame(frameTime, true), position.x, position.y);
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
