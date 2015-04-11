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
import com.game.component.Action;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;

public class SpriteRenderSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<SpriteComponent> spriteComponentMapper;
	@Mapper ComponentMapper<PositionComponent> positionComponentMapper;
	@Mapper ComponentMapper<MovementComponent> movementComponentMapper;
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
	protected void process(Entity e) {
		batch.setProjectionMatrix(camera.combined);
		if (spriteComponentMapper.has(e)) {
			SpriteComponent sprite = spriteComponentMapper.getSafe(e);
			PositionComponent position = positionComponentMapper.get(e);
			MovementComponent movement = movementComponentMapper.get(e);
			DirectionComponent direction = directionComponentMapper.get(e);

			// Animation regions
			if (movement.state == Action.idle) {
				animation = new Animation(0.060f,
						sprite.getSprites(direction.direction, movement.state));
			} else if (movement.state == Action.walk) {
				animation = new Animation(0.050f,
						sprite.getSprites(direction.direction, movement.state));
			}
			frameTime += Gdx.graphics.getDeltaTime();
			batch.draw(animation.getKeyFrame(frameTime, true), position.x, position.y);
		}
	}

	@Override
	protected void end() {
		batch.end();
	}

}
