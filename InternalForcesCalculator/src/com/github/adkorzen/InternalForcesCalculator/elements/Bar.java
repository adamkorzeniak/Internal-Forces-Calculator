package com.github.adkorzen.InternalForcesCalculator.elements;

public class Bar implements Element {
	private final PairOfNodes<Node> nodes;
	private final Line line;
	private boolean startJoint;
	private boolean endJoint;

	public Bar(Project project, Node first, Node second) {
		nodes = new PairOfNodes<Node>(first, second);
		line = new Line(first.getX(), first.getY(), second.getX(), second.getY());
		
	}

	public boolean contains(Point point) {
		if (!line.contains(point)) {
			return false;
		}
		if (point.getX() + Project.ACCURACY < getStartingNode().getX()
				|| point.getX() - Project.ACCURACY > getEndingNode().getX()) {
			return false;
		}
		return true;
	}

	public Node getStartingNode() {
		return nodes.getStart();
	}

	public Node getEndingNode() {
		return nodes.getEnd();
	}

	public void setStartingNodeJoint(boolean joint) {
		Node node = getStartingNode();
		setNodeJoint(node, joint);
	}

	public void setEndingNodeJoint(boolean joint) {
		Node node = getEndingNode();
		setNodeJoint(node, joint);
	}

	private void setNodeJoint(Node node, boolean joint) {
		if (node == getStartingNode()) {
			if (startJoint || node.getSupport() == null) {
				startJoint = joint;
				return;
			}
		} else if (node == getEndingNode()) {
			if (endJoint || node.getSupport() == null) {
				endJoint = joint;
				return;
			}
		}
		if (node.getSupport().equals(Support.FIXED)) {
			node.setSupport(Support.HINGED);
		} else if (node.getSupport().equals(Support.SLIDER)) {
			node.setSupport(Support.ROLLER);
		} else {
			System.out.println("Cannot release node with not blocked rotation.");
		}
	}

	public boolean isStartingNodeJoint() {
		return startJoint;
	}

	public boolean isEndingNodeJoint() {
		return endJoint;
	}

	private class PairOfNodes<T> {
		private T firstNode, secondNode;

		private PairOfNodes(T first, T second) {
			double x1 = ((Node) first).getX();
			double x2 = ((Node) second).getX();
			double y1 = ((Node) first).getY();
			double y2 = ((Node) second).getY();
			if (Math.abs(x2 - x1) < Project.ACCURACY) {
				if (y1 < y2) {
					firstNode = first;
					secondNode = second;
				} else {
					firstNode = second;
					secondNode = first;
				}
			} else if (x1 < x2) {
				firstNode = first;
				secondNode = second;
			} else {
				firstNode = second;
				secondNode = first;
			}
		}

		private T getStart() {
			return firstNode;
		}

		private T getEnd() {
			return secondNode;
		}
	}
}
