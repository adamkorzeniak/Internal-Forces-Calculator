package com.github.adkorzen.InternalForcesCalculator.elements;

public class Node implements Element {
	private final Project project;
	private final Point point;
	private Support support;
	private double supportAngle;

	public Node(Project project, double x, double y) {
		this.project = project;
		this.point = new Point(x, y);
	}

	public void setSupport(Support support) {
		setSupport(support, 0);
	}

	public void setSupport(Support support, double supportAngle) {
		this.support = support;
		this.project.addSupport(this);
		setSupportAngle(supportAngle);
	}

	// @Probably is going to be changed
	public void setSupportAngle(double supportAngle) {
		if (support != null) {
			while (supportAngle < 0.0) {
				supportAngle += 360.0;
			}
			while (supportAngle > 360.0) {
				supportAngle -= 360.0;
			}

			this.supportAngle = supportAngle;
		} else {
			System.out.println("There is no support in this node. Angle cannot be changed.");
		}
	}

	public double getSupportAngle() {
		return supportAngle;
	}

	public void removeSupport() {
		support = null;
		project.removeSupport(this);
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
	
	// probably to change
	@Override
	public boolean equals(Object o) {
		Node other = (Node) o;
		if (Math.abs(getX() - other.getX()) > Project.ACCURACY) {
			return false;
		} else if (Math.abs(getY() - other.getY()) > Project.ACCURACY) {
			return false;
		}
		return true;
	}
}
