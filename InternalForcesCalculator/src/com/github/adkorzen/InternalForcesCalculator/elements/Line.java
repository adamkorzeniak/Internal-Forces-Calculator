package com.github.adkorzen.InternalForcesCalculator.elements;

public class Line {
	private final Point point;
	private final double slope;

	public Line(double x1, double y1, double x2, double y2) {

		if (Math.abs(x1 - x2) < Project.ACCURACY) {
			slope = Double.POSITIVE_INFINITY;
		} else if (Math.abs(y1 - y2) < Project.ACCURACY) {
			slope = 0;
		} else {
			slope = (y2 - y1) / (x2 - x1);
		}
		point = new Point(x1, y1);
	}

	public Line(Point point, double slope) {
		this.slope = slope;
		this.point = point;
	}

	public boolean contains(Point p) {
		if (slope == Double.POSITIVE_INFINITY && Math.abs(this.point.getX() - p.getX()) < Project.ACCURACY) {
			return true;
		}

		if (p.equals(this.point)) {
			return true;
		}

		double difference = Math.abs((p.getY() - this.point.getY()) / (p.getX() - this.point.getX()) - slope);
		if (difference < Project.ACCURACY) {
			return true;
		}
		return false;
	}

	public Point getCrossPoint(Line other) {
		if (Math.abs(this.slope - other.slope) < Project.ACCURACY) {
			return new Point(slope);
		}
		if (this.slope == Double.POSITIVE_INFINITY && other.slope == Double.POSITIVE_INFINITY) {
			return new Point(Double.POSITIVE_INFINITY);
		}
		double xAxis = 0;
		double yAxis = 0;
		
		if (this.slope == Double.POSITIVE_INFINITY) {
			xAxis = this.point.getX();
			yAxis = other.point.getY() + (xAxis - other.point.getX()) * other.slope;
		} else if (other.slope == Double.POSITIVE_INFINITY) {
			xAxis = other.point.getX();
			yAxis = this.point.getY() + (xAxis - this.point.getX()) * this.slope;
		} else {
			xAxis = (this.point.getY() - other.point.getY() + other.slope * other.point.getX() - this.slope * this.point.getX()) / (other.slope - this.slope);
			yAxis = (xAxis - this.point.getX()) * this.slope + this.point.getY();
		}
		return new Point(xAxis, yAxis);
	}
	
	public double distanceTo(Point point) {
		if (this.contains(point)) {
			return 0;
		} else if (slope == Double.POSITIVE_INFINITY) {
			return point.getX() - this.point.getX();
		} else if (slope == 0) {
			return this.point.getY() - point.getY();
		}
		double otherSlope = - 1.0 / slope;
		Line other = new Line(point, otherSlope);
		Point crossPoint = this.getCrossPoint(other);
		
		double distance = point.distanceTo(crossPoint);
		if (slope > 0 && point.getX() < crossPoint.getX() || slope < 0 && point.getX() > crossPoint.getX()) {
			distance *= -1;
		}
		
		return distance;
	}

	public double getSlope() {
		return slope;
	}
	
	public Point getPoint() {
		return point;
	}

	//poprawiæ uwzglêdniaj¹c czy punkt jest na linii
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(slope);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Line other = (Line) obj;
		if (Double.doubleToLongBits(slope) != Double.doubleToLongBits(other.slope))
			return false;
		if (!this.contains(other.point)) {
			return false;
		}
		if (!other.contains(this.point)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String result = String.format("Line: %.1f, %.1f + %.1f", point.getX(), point.getY(), slope);
		return result;
	}
}
