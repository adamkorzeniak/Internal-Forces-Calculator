package com.github.adkorzen.InternalForcesCalculator.elements;

public class Line {
	private final double x, y;
	private final double slope;

	public Line(double x1, double y1, double x2, double y2) {

		if (Math.abs(x1 - x2) < Project.ACCURACY) {
			x = x1;
			y = 0;
			slope = Double.POSITIVE_INFINITY;
		} else if (Math.abs(y1 - y2) < Project.ACCURACY) {
			x = 0;
			y = y1;
			slope = 0;
		} else {
			x = 0;
			slope = (y2 - y1) / (x2 - x1);
			y = y1 - x1 * slope;
		}
	}

	public Line(Point point, double slope) {
		this.slope = slope;
		if (slope == Double.POSITIVE_INFINITY) {
			x = point.getX();
			y = 0;
		} else {
			x = 0;
			y = point.getY() - point.getX() * slope;
		}
	}

	public boolean contains(Point p) {
		if (slope == Double.POSITIVE_INFINITY && Math.abs(x - p.getX()) < Project.ACCURACY) {
			return true;
		}

		double difference = Math.abs(p.getX() * slope + y - p.getY());

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
			xAxis = this.x;
			yAxis = other.y + xAxis * other.slope;
		} else if (other.slope == Double.POSITIVE_INFINITY) {
			xAxis = other.x;
			yAxis = this.y + xAxis * this.slope;
		} else {
			xAxis = (other.y - this.y) / (this.slope - other.slope);
			yAxis = xAxis * this.slope + this.y;
		}
		return new Point(xAxis, yAxis);
	}

	public double getSlope() {
		return slope;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(slope);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
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
		if (Math.abs(slope - other.slope) > Project.ACCURACY)
			return false;
		if (Math.abs(x - other.x) > Project.ACCURACY)
			return false;
		if (Math.abs(y - other.y) > Project.ACCURACY)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String result = String.format("Line: %.1f, %.1f + %.1f", x, y, slope);
		return result;
	}
}
