package com.github.adkorzen.InternalForcesCalculator.results;

import com.github.adkorzen.InternalForcesCalculator.elements.Point;

public class RotationForce implements Force {
	private final Point point;
	private double value;

	public RotationForce(Point point) {
		this.point = point;
	}

	public RotationForce(Point point, double value) {
		this.point = point;
		this.value = value;
	}

	@Override
	public void setValue(double value) {
		if (this.value == 0) {
			this.value = value;
		}
	}
	
	@Override
	 public Point getPoint() {
	 return point;
	 }
	
	@Override
	public double getValue() {
		return value;
	}
	
	@Override
	public double calculateXForce() {
		return 0;
	}
	
	@Override
	public double calculateYForce() {
		return 0;
	}
	
	@Override
	public double calculateMoment(Point p) {
		if (value == 0) {
			return 1;
		} else {
			return value;
		}
	}
}
