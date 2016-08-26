package com.github.adkorzen.InternalForcesCalculator.results;

import com.github.adkorzen.InternalForcesCalculator.elements.Line;
import com.github.adkorzen.InternalForcesCalculator.elements.Point;

import static com.github.adkorzen.InternalForcesCalculator.math.Math.*;

public class LinearForce implements Force {
	private final Line line;
	private double value;

	// constructor for reactions, value 0 means it needs to be calculated
	public LinearForce(Point point, double slope) {
		line = new Line(point, slope);
	}

	public LinearForce(Point point, double slope, double value) {
		line = new Line(point, slope);
		this.value = value;
	}

	@Override
	public Point getPoint() {
		return line.getPoint();
	}

	@Override
	public void setValue(double value) {
		if (this.value == 0) {
			this.value = value;
		}
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public double calculateXForce() {
		if (value == 0) {
			return tangentToCosinus(line.getSlope());
		} else {
			return value * tangentToCosinus(line.getSlope());
		}
	}

	@Override
	public double calculateYForce() {
		if (value == 0) {
			return tangentToSinus(line.getSlope());
		} else {
			return value * tangentToSinus(line.getSlope());
		}
	}

	@Override
	public double calculateMoment(Point p) {
		double distance = line.distanceTo(p);
		if (value == 0) {
			return distance;
		} else {
			return distance * value;
		}
	}
}
