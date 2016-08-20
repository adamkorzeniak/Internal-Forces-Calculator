package com.github.adkorzen.InternalForcesCalculator.elements;

public class Point {
	private final double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// Point in infinity, y used to store slope
	public Point(double slope) {
		x = Double.POSITIVE_INFINITY;
		y = slope;
	}

	public Line getCommonLine(Point other) {
		Line common = null;
		if (this.x == Double.POSITIVE_INFINITY && other.x == Double.POSITIVE_INFINITY) {
			if (Math.abs(this.y - other.y) < Project.ACCURACY) {
				common = new Line(new Point(0, 0), this.y);
			} else if (this.y == Double.POSITIVE_INFINITY && other.y == Double.POSITIVE_INFINITY) {
				common = new Line(new Point(0, 0), Double.POSITIVE_INFINITY);
			}
		} else if (this.x == Double.POSITIVE_INFINITY) {
			common = new Line(other, this.y);
		} else if (other.x == Double.POSITIVE_INFINITY) {
			common = new Line(this, other.y);
		} else {
			double slope = 0;
			slope = (other.y - this.y) / (other.x - this.x);
			common = new Line(this, slope);
		}
		return common;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
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
		Point other = (Point) obj;
		if (Math.abs(x - other.x) > Project.ACCURACY)
			return false;
		if (Math.abs(y - other.y) > Project.ACCURACY)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String result = String.format("Point: x = %.2f, y = %.2f", x, y);
		return result;
	}

}
