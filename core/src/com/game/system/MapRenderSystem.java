package com.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.component.CameraComponent;
import com.game.component.InputComponent;
import com.game.component.MapComponent;
import com.game.input.GameInput;

public class MapRenderSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<MapComponent> mapc;
	@Mapper
	ComponentMapper<CameraComponent> camMapper;

	@Mapper
	ComponentMapper<InputComponent> inputCMapper;

	private IsometricTiledMapRenderer isometricTiledMapRenderer;
	private Matrix4 isoTransform;
	private Matrix4 invIsotransform;
	private Vector3 screenPos = new Vector3();

	public MapRenderSystem() {
		super(Aspect.getAspectForAll(MapComponent.class, CameraComponent.class,
				InputComponent.class));
		this.isometricTiledMapRenderer = new IsometricTiledMapRenderer(null);
		init();
	}

	private void init() {
		// create the isometric transform
		isoTransform = new Matrix4();
		isoTransform.idt();

		// isoTransform.translate(0, 32, 0);
		isoTransform.scale((float) (Math.sqrt(2.0) / 2.0),
				(float) (Math.sqrt(2.0) / 4.0), 1.0f);
		isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

		// ... and the inverse matrix
		invIsotransform = new Matrix4(isoTransform);
		invIsotransform.inv();
	}

	protected void process(Entity e) {

		// Get the components from the entity using component mappers.
		MapComponent map = mapc.get(e);
		CameraComponent cameraComponent = camMapper.get(e);
		InputComponent inputComponent = inputCMapper.get(e);

		if (inputComponent.isPressed(GameInput.TOUCH)) {
			Vector3 position = translateScreenToIso(inputComponent
					.getTouchScreenPosition());
			// FIXME ça marche pas encore
			// cameraComponent.translate(position.x, position.y);
			// cameraComponent.lookAt(position.x, position.y);
		}

		// Set the tiled map to render (because it can render different maps on
		// the fly)
		isometricTiledMapRenderer.setMap(map.getTiled());
		isometricTiledMapRenderer.setView(cameraComponent.getCam());

		// Render on screen
		isometricTiledMapRenderer.render();
	}

	private Vector3 translateScreenToIso(Vector2 vec) {
		screenPos.set(vec.x, vec.y, 0);
		screenPos.mul(invIsotransform);

		return screenPos;
	}

}
