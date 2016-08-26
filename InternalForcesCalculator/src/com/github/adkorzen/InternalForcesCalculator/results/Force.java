package com.github.adkorzen.InternalForcesCalculator.results;

import com.github.adkorzen.InternalForcesCalculator.elements.Point;

public interface Force {

	public abstract void setValue(double value);
	public abstract double getValue();
	public abstract double calculateXForce();
	public abstract double calculateYForce();
	public abstract double calculateMoment(Point point);
	public abstract Point getPoint();
}
