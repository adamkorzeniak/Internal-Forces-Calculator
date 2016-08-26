package com.github.adkorzen.InternalForcesCalculator.elements;

import static com.github.adkorzen.InternalForcesCalculator.math.Math.degreeToTangent;

import java.util.ArrayList;
import java.util.List;

import com.github.adkorzen.InternalForcesCalculator.loads.NodeLoad;
import com.github.adkorzen.InternalForcesCalculator.results.Force;

public class Node implements Element {
	private final Project project;
	private final Point point;
	private Support support;
	private double slope;
	private List<NodeLoad> loads;
	private NodeLoad reactions;
	private List<Direction> directionBlocked;

	public Node(Project project, double x, double y) {
		this.project = project;
		this.point = new Point(x, y);
	}

	public void setSupport(Support support) {
		setSupport(support, 0);
	}

	public void setSupport(Support support, double supportAngle) {
		this.support = support;
		project.addSupport(this);
		setSupportAngle(supportAngle);
	}

	public void setSupportAngle(double supportAngle) {
		if (support != null) {
			slope = degreeToTangent(supportAngle);
		} else {
			System.out.println("There is no support in this node. Angle cannot be changed.");
		}
	}

	public double getSlope() {
		return slope;
	}

	public void removeSupport() {
		support = null;
		project.removeSupport(this);
	}

	public void addLoad(NodeLoad load) {
		if (loads == null) {
			loads = new ArrayList<NodeLoad>();
		}
		loads.add(load);
	}

	public List<NodeLoad> getLoads() {
		return loads;
	}

	public Support getSupport() {
		return support;
	}

	public void setReaction(Force reaction) {
		if (getSupport() == null) {
			System.out.println("No support. Cannot set reaction");
			return;
		}
		if (directionBlocked == null) {
			directionBlocked = new ArrayList<Direction>();
			if (getSupport().isXMoveBlocked()) {
				directionBlocked.add(Direction.HORIZONTAL);
			}
			if (getSupport().isYMoveBlocked()) {
				directionBlocked.add(Direction.VERTICAL);
			}
			if (getSupport().isRotationBlocked()) {
				directionBlocked.add(Direction.ROTATION);
			}
		} else if (directionBlocked.isEmpty()) {
			System.out.println("All directions alrady set");
		}
		if (reactions == null) {
			NodeLoad r = new NodeLoad.Builder().slope(slope).build();
			reactions = r;
		}
		NodeLoad prev = reactions;
		NodeLoad.Builder builder = new NodeLoad.Builder().x(prev.getX()).y(prev.getY()).moment(prev.getMoment())
				.slope(prev.getSlope());
		NodeLoad actualizedReaction = null;

		if (directionBlocked.get(0) == Direction.HORIZONTAL) {
			actualizedReaction = builder.x(reaction.getValue()).build();
		} else if (directionBlocked.get(0) == Direction.VERTICAL) {
			actualizedReaction = builder.y(reaction.getValue()).build();
		} else if (directionBlocked.get(0) == Direction.ROTATION) {
			actualizedReaction = builder.moment(reaction.getValue()).build();
		}
		directionBlocked.remove(0);
		reactions = actualizedReaction;
	}

	public double getHorizontalReaction() {
		return reactions.getX();
	}

	public double getVerticalReaction() {
		return reactions.getY();
	}

	public double getMomentReaction() {
		return reactions.getMoment();
	}

	public double getX() {
		return point.getX();
	}

	public double getY() {
		return point.getY();
	}
	public Point getPoint() {
		return point;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
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
		Node other = (Node) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String result = "Node: x = ";
		result += point.getX() + ", y = ";
		result += point.getY();
		return result;
	}

	private enum Direction {
		HORIZONTAL, VERTICAL, ROTATION
	};
}
