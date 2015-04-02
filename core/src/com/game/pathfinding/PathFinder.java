package com.game.pathfinding;

import com.badlogic.gdx.math.Vector2;

public class PathFinder {
	
	private Terrain terrain;
	private NodeSet solutionPath= new NodeSet();
	
	public PathFinder(Terrain terrain) {
		this.terrain= terrain;
	}
	
	public void aStar(int sx, int sy, int tx, int ty) {
		// create open and closed list
		NodeSet openSet= new NodeSet();
		NodeSet closedSet= new NodeSet();
		// creating source node
		Node source= new Node(null, sx, sy);
		source.gCost= terrain.cost(sy, sx);
		source.gCost= 0;
		// creating target node
		Node target= new Node(null, tx, ty);
		target.gCost= terrain.cost(ty, tx);
		// if the target is not passable, don't move the object
		if (!terrain.passable(target.y, target.x)) {
			return;
		}
		// put source node in the open list
		openSet.add(source);
		// while the open list is not empty
		while(openSet.size()> 0) {
			// get the node having the lowest fCost off the open list (already sorted)
			Node current= openSet.get(0);
			// if this current node is the same as the target, then we're done
			if (current.sameAs(target)) {
				target.parent= current.parent;
				reconstructPath(target);
				break;
			}
			// generate neighboors of the current node
			NodeSet neighboors= current.getNeighboors();
			// for each neighboor
			for (Node n : neighboors) {
				// if the neighboor is passable, if not, ignore it
				if (terrain.passable(n.y, n.x)) {
					// set the gcost
					n.gCost= terrain.cost(n.y, n.x);
					n.gCost= current.gCost+ n.gCost;
					// find the neighboor on the open list
					int openFound= openSet.indexOf(n);
					//if node_successor is on the OPEN list but the existing one is as good
					//or better then discard this successor and continue
					if (openFound> -1) {
						Node existing= openSet.get(openFound);
						if (existing.fCost< n.fCost) continue;
					}
					//find node_successor on the CLOSED list
					int closedFound= closedSet.indexOf(n);
					//if node_successor is on the CLOSED list but the existing one is as good
					//or better then discard this successor and continue;
					if (closedFound> -1) {
						Node existing= closedSet.get(closedFound);
						if (existing.fCost< n.fCost) continue;
					}
					//Remove occurences of node_successor from OPEN and CLOSED
					if (openFound!= -1) openSet.remove(openFound);
					if (closedFound!= -1) closedSet.remove(closedFound);
					//Set the parent of node_successor to node_current;
					//--> already set while we are getting successors
					// set the hCost
					n.hCost= n.calculateManhattanDistance(target);
					// set the fCost
					n.fCost= n.gCost+ n.hCost;
					// add the neighboor to the openSet
					openSet.add(n);
					// sort the openSet according to the fCost
					openSet.sort();
				}
			}
			// add the current node to the closed list, marking it as processed
			closedSet.add(current);
			openSet.remove(current);
		}		
	}
	
	private void reconstructPath(Node target) {
		Node curr= target;
		while (curr!= null) {
			solutionPath.add(0, curr);
			curr= curr.parent;
		}
	}
	
	public Vector2 nextStep() {
		if (solutionPath.size()> 0) {
			Node node= solutionPath.get(0);
			solutionPath.remove(0);
			return new Vector2(node.x, node.y);
		}else return null;
	}
	
	public void clearPath() {
		solutionPath.clear();
	}
	
}
