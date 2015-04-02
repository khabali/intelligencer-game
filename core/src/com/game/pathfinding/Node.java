package com.game.pathfinding;

public class Node implements Comparable<Node> {
	
	public int x, y;
	public float gCost, hCost, fCost;
	public Node parent;
	
	public Node(Node parent, int x, int y) {
		this.parent= parent;
		this.x= x;
		this.y= y;
	}
	
	public double calculateEuclideanDistance(Node tnode) {
		int dx= tnode.x- this.x;
	    int dy= tnode.y- this.y;
	    return java.lang.Math.sqrt((dx* dx)+ (dy* dy));
	}
	
	public float calculateManhattanDistance(Node tnode) {
		int dx= java.lang.Math.abs(tnode.x- this.x);
	    int dy= java.lang.Math.abs(tnode.y- this.y);
	    return dx+ dy;
	}
	
	public NodeSet getNeighboors() {
		NodeSet neighboors= new NodeSet();
		for (int nx= -1; nx<= 1; nx++) {
			for (int ny= -1; ny<= 1; ny++) {
				Node n= new Node(this, nx+ x, ny+ y);
				if (!n.sameAs(this.parent) && !n.sameAs(this))
					neighboors.add(n);
			}
		}
		return neighboors;
	}
	
	@Override
	public int compareTo(Node node) {
		return (fCost < node.fCost ? -1 :
               (fCost == node.fCost ? 0 : 1));
	}
	
	public boolean sameAs(Node n) {
		if (n== null) return false;
		else return (this.x== n.x && this.y== n.y);
	}		
}
