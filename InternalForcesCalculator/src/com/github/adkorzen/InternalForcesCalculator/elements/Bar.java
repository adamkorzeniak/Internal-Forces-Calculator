package com.github.adkorzen.InternalForcesCalculator.elements;

public class Bar implements Element {
	private final PairOfNodes<Node> nodes;
	private final Line line;
	private boolean startReleased;
	private boolean endReleased;

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
		double y = point.getY();
		double yLow = Math.min(getStartingNode().getY(), getEndingNode().getY());
		double yHigh = Math.max(getStartingNode().getY(), getEndingNode().getY());
		
		if (line.getAngle() == Double.POSITIVE_INFINITY && (Math.abs(y - Project.ACCURACY) > yHigh || Math.abs(y + Project.ACCURACY) < yLow)) {
			return false;
		}
		return true;
	}
	
	public boolean contains(Node n) {
		double x = n.getX();
		double y = n.getY();
		
		boolean result = contains(new Point(x, y));
		return result;
	}

	public Node getStartingNode() {
		return nodes.getStart();
	}

	public Node getEndingNode() {
		return nodes.getEnd();
	}

	public void setStartingNodeReleased(boolean released) {
		startReleased = released;
	}

	public void setEndingNodeReleased(boolean released) {
		endReleased = released;
	}

	public boolean isStartingNodeReleased() {
		return startReleased;
	}

	public boolean isEndingNodeReleased() {
		return endReleased;
	}
	
	@Override
	public boolean equals(Object o) {
		Bar other = (Bar) o;
		if (!getStartingNode().equals(other.getStartingNode())) {
			return false;
		} else if (!getEndingNode().equals(other.getEndingNode())) {
			return false;
		} else if (!line.equals(other.line)) {
			return false;
		}
		return true;
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
