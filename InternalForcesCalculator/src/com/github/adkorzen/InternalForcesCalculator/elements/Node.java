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

	public double getX() {
		return point.getX();
	}

	public double getY() {
		return point.getY();
	}

	public void setSupport(Support support) {
		setSupport(support, 0);
	}

	public void setSupport(Support support, double supportAngle) {
		this.support = support;
		this.project.addSupport(this);
		setSupportAngle(supportAngle);
	}

	public void removeSupport() {
		support = null;
		project.removeSupport(this);
	}

	public Support getSupport() {
		return support;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
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
}
