package com.game.component;

import com.artemis.Component;

public class StateComponent extends Component {
	
	public State state;
	
	public StateComponent(State st) {
		this.state = st;
	}
	
	public void setState(State st) {
		this.state = st;
	}
	

}
