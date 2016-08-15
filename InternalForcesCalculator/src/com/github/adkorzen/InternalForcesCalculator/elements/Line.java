package com.github.adkorzen.InternalForcesCalculator.elements;

//without limits in both directions
public class Line {
	private final double x, y;
	private final double angle;

	public Line(double x1, double y1, double x2, double y2) {

		// @Find a way to test those fields
		if (Math.abs(x1 - x2) < Project.ACCURACY) {
			x = x1;
			y = 0;
			angle = Double.POSITIVE_INFINITY;
		} else if (Math.abs(y1 - y2) < Project.ACCURACY){
			x = 0;
			y = y1;
			angle = 0;
		} else {
			x = 0;
			angle = (y2 - y1) / (x2 - x1);
			y = y1 - x1 * angle;
		}
	}

	public boolean contains(Point p) {
		if (angle == Double.POSITIVE_INFINITY && Math.abs(x - p.getX()) < Project.ACCURACY) {
			return true;
		}
		
		double difference = Math.abs(p.getX() * angle + y - p.getY());

		if (difference < Project.ACCURACY) {
			return true;
		}
		return false;
	}
	
	public double getAngle() {
		return angle;
	}
	
	@Override
	public boolean equals(Object o) {
		Line other = (Line) o;
		if (x != other.x) {
			return false;
		} else if (y != other.y) {
			return false;
		} else if (angle != other.angle) {
			return false;
		}
		return true;
	}
}
