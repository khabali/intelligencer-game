package com.game.component;

import com.artemis.Component;
import com.game.entity.state.IEntityState;

public class StateComponent extends Component {
	
	public IEntityState currentState;
	
	public StateComponent(IEntityState cur) {
		currentState = cur;
	}

}
