package com.github.adkorzen.InternalForcesCalculator.elements;

public class Point {
	private final double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// Point in infinity, y is used to store angle
	public Point(double a) {
		x = Double.POSITIVE_INFINITY;
		y = a;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	//Probably to change
	@Override
	public boolean equals(Object o) {
		Point other = (Point) o;
		if (Math.abs(x - other.x) > Project.ACCURACY) {
			return false;
		} else if (Math.abs(y - other.y) > Project.ACCURACY) {
			return false;
		}
		return true;
	}
}
