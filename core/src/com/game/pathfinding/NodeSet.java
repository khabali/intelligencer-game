package com.game.pathfinding;

import java.util.ArrayList;
import java.util.Collections;

public class NodeSet extends ArrayList<Node> {

	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean contains(Object o) {
		if (o!= null && o.getClass()== Node.class) {
			Node n;
			for (int i= 0; i< this.size(); i++) {
				n= this.get(i);
				if (n.sameAs((Node)o)) return true;
			}
			return false;
		}else return false;
	}
	
	@Override
	public int indexOf(Object o) {
		if (o!= null && o.getClass()== Node.class) {
			Node n;
			for (int i= 0; i< this.size(); i++) {
				n= this.get(i);
				if (n.sameAs((Node)o)) return i;
			}
			return -1;
		}else return -1;
	}
	
	public void sort() {
		Collections.sort(this);
	}
	
	
}
