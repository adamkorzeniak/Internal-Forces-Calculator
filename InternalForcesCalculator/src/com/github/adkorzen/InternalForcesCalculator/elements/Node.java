package com.github.adkorzen.InternalForcesCalculator.elements;

import java.util.ArrayList;
import java.util.List;

import com.github.adkorzen.InternalForcesCalculator.loads.NodeLoad;

public class Node implements Element {
	private final Project project;
	private final Point point;
	private Support support;
	private double slope;
	private List<NodeLoad> loads;

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
			while(supportAngle < 0) {
				supportAngle += 180;
			}
			if (supportAngle % 180 == 90) {
				slope = Double.POSITIVE_INFINITY;
			} else {
				double inRadius = supportAngle * Math.PI / 180;
				slope = Math.tan(inRadius);
			}
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

	public double getX() {
		return point.getX();
	}

	public double getY() {
		return point.getY();
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
}
