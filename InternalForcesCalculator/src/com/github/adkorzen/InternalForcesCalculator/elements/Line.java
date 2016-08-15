package com.github.adkorzen.InternalForcesCalculator.elements;

//without limits in both directions
public class Line {
	private final double y;
	private final double angle;
	
	public Line(double x1, double y1, double x2, double y2) {
		
		//@Find a way to test those fields
		
		angle = (y2 - y1) / (x2 - x1);
		y = y1 - x1 * angle;
	}
	
	public boolean contains(Point p) {
		double difference = Math.abs(p.getX() * angle + y - p.getY());
		
		if (difference < 0.00001) {
			return true;
		}
		return false;
	}
	
	
	
}
