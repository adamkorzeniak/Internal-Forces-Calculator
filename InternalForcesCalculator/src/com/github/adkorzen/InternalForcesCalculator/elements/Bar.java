package com.github.adkorzen.InternalForcesCalculator.elements;

public class Bar {
	private PairOfNodes<Node> nodes;
	private boolean startJoint;
	private boolean endJoint;

	public Bar(int xStart, int yStart, int xEnd, int yEnd) {
		nodes = new PairOfNodes<Node>(new Node(xStart, yStart), new Node(xEnd, yEnd));
	}

	public Bar(int x, int y, Node node) {
		nodes = new PairOfNodes<Node>(new Node(x, y), node);
	}

	public Bar(Node first, Node second) {
		nodes = new PairOfNodes<Node>(first, second);
	}

	public Node getStartingNode() {
		return nodes.getStart();
	}

	public Node getEndingNode() {
		return nodes.getEnd();
	}
	public void setStartingNodeJoint(boolean joint) {
		startJoint = joint;
	}
	
	public void setEndingNodeJoint(boolean joint) {
		endJoint = joint;
	}

	public boolean isStartingNodeJoint() {
		return startJoint;
	}

	public boolean isEndingNodeJoint() {
		return endJoint;
	}
	
	
	private class PairOfNodes<T> {
		private T firstNode, secondNode;
		
		private PairOfNodes (T first, T second){
			firstNode = first;
			secondNode = second;
		}
		
		private T getStart() {
			return firstNode;
		}
		
		private T getEnd() {
			return secondNode;
		}
	}
}
