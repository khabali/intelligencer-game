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
import com.game.component.CameraComponent;
import com.game.component.DirectionComponent;
import com.game.component.MovementComponent;
import com.game.component.PositionComponent;
import com.game.component.SpriteComponent;

public class SpriteRenderSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<SpriteComponent> sprazeazetc;
	@Mapper
	ComponentMapper<PositionComponent> posc;
	@Mapper
	ComponentMapper<MovementComponent> movc;
	@Mapper
	ComponentMapper<DirectionComponent> dirc;

	@Mapper
	ComponentMapper<CameraComponent> cameraMapper;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Animation animation;
	private float frameTime = 0;

	// private Bag<AtlasRegion> regionsByEntity; // collection of regions of
	// every entity to render

	public SpriteRenderSystem() {
		super(Aspect.getAspectForAll(SpriteComponent.class,
				PositionComponent.class, MovementComponent.class,
				DirectionComponent.class, CameraComponent.class));

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
		this.camera = cameraMapper.get(e).getCam();
		batch.setProjectionMatrix(camera.combined);
		if (sprazeazetc.has(e)) {
			SpriteComponent sprite = sprazeazetc.getSafe(e);
			PositionComponent position = posc.get(e);
			MovementComponent movement = movc.get(e);
			DirectionComponent direction = dirc.get(e);

			// Animation regions
			if (movement.state == MovementComponent.IDLE) {
				animation = new Animation(0.090f,
						sprite.regions[direction.curdir][0]);
			} else if (movement.state == MovementComponent.MOVING) {
				animation = new Animation(0.090f,
						sprite.getRegions(direction.curdir));
			}
			frameTime += Gdx.graphics.getDeltaTime();
			// Render
			batch.draw(animation.getKeyFrame(frameTime, true), position.x,
					position.y);
		}
	}

	@Override
	protected void end() {
		batch.end();
	}

}
