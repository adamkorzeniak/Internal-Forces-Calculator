package com.github.adkorzen.InternalForcesCalculator.elements;

import java.util.ArrayList;
import java.util.List;

import com.github.adkorzen.InternalForcesCalculator.loads.BarLoad;

public class Bar implements Element {
	private final Project project;
	private final PairOfNodes<Node> nodes;
	private final Line line;
	private boolean startReleased;
	private boolean endReleased;
	private List<BarLoad> loads;

	public Bar(Project project, Node first, Node second) {
		this.project = project;
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

		if (line.getSlope() == Double.POSITIVE_INFINITY
				&& (Math.abs(y - Project.ACCURACY) > yHigh || Math.abs(y + Project.ACCURACY) < yLow)) {
			return false;
		}
		return true;
	}

	public boolean contains(Node n) {
		return contains(n.getPoint());
	}

	public void divide(double relativeDistance) {
		if (relativeDistance < Project.MINIMAL_DISTANCE_LEAP || relativeDistance > 1 - Project.MINIMAL_DISTANCE_LEAP) {
			System.out.println("Invalid value");
			return;
		}
		Point divide = getPoint(relativeDistance);
		double xStart = getStartingNode().getX();
		double yStart = getStartingNode().getY();
		double xEnd = getEndingNode().getX();
		double yEnd = getEndingNode().getY();

		double xDivide = divide.getX();
		double yDivide = divide.getY();

		project.addBar(xStart, yStart, xDivide, yDivide);
		project.addBar(xDivide, yDivide, xEnd, yEnd);

		project.removeBar(this);

		if (isStartingNodeReleased()) {
			Bar start = project.getBar((xDivide + xStart) / 2, (yDivide + yStart) / 2);
			start.setStartingNodeReleased(true);
		}
		if (isEndingNodeReleased()) {
			Bar end = project.getBar((xDivide + xEnd) / 2, (yDivide + yEnd) / 2);
			end.setEndingNodeReleased(true);
		}
		project.addNode(xDivide, yDivide);
	}

	public Point getPoint(double relativeDistance) {
		if (relativeDistance < Project.MINIMAL_DISTANCE_LEAP || relativeDistance > 1 - Project.MINIMAL_DISTANCE_LEAP) {
			System.out.println("It needs to be between 0 and 1");
		}
		double xStart = getStartingNode().getX();
		double yStart = getStartingNode().getY();
		double xEnd = getEndingNode().getX();
		double yEnd = getEndingNode().getY();

		double xDivide = (xEnd - xStart) * relativeDistance + xStart;
		double yDivide = (yEnd - yStart) * relativeDistance + yStart;
		Point result = new Point(xDivide, yDivide);
		return result;
	}
	
	public Point getCommonPoint(Bar other) {
		Point cross = this.line.getCrossPoint(other.line);
		if (cross.getX() == Double.POSITIVE_INFINITY) {
			if(this.getStartingNode().equals(other.getStartingNode()) || this.getStartingNode().equals(other.getEndingNode())) {
				return this.getStartingNode().getPoint();
			} else if(this.getEndingNode().equals(other.getStartingNode()) || this.getEndingNode().equals(other.getEndingNode())) {
				return this.getEndingNode().getPoint();
			}
		} 
		if (this.contains(cross) && other.contains(cross)) {
			return cross;
		}
		return null;
	}

	public void addLoad(BarLoad load) {
		if (loads == null) {
			loads = new ArrayList<BarLoad>();
		}
		loads.add(load);
	}

	public List<BarLoad> getLoads() {
		return loads;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((line == null) ? 0 : line.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bar other = (Bar) obj;
		if (line == null) {
			if (other.line != null)
				return false;
		} else if (!line.equals(other.line))
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!getStartingNode().equals(other.getStartingNode()))
			return false;
		else if (!getEndingNode().equals(other.getEndingNode()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String result = String.format("Bar: P1(%.2f, %.2f), P2(%.2f, %.2f)", getStartingNode().getX(),
				getStartingNode().getY(), getEndingNode().getX(), getEndingNode().getY());
		return result;
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
