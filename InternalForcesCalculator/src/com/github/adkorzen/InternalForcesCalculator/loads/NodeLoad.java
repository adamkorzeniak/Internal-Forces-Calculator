package com.github.adkorzen.InternalForcesCalculator.loads;

import static com.github.adkorzen.InternalForcesCalculator.math.Math.*;

public class NodeLoad{
	private final double xValue;
	private final double yValue;
	private final double momentValue;
	private final double slope;
	
	public static class Builder {
		private double xValue = 0;
		private double yValue = 0;
		private double momentValue = 0;
		private double slope = 0;

		public Builder x(double value) {
			xValue = value;
			return this;
		}
		
		public Builder y(double value) {
			yValue = value;
			return this;
		}
		
		public Builder moment(double value) {
			momentValue = value;
			return this;
		}
		
		public Builder angle (double angle) {
			slope = degreeToTangent(angle);
			return this;
		}
		
		public Builder slope (double slope) {
			this.slope = slope;
			return this;
		}
		
		public NodeLoad build() {
			return new NodeLoad(this);
		}
	}
	
	private NodeLoad(Builder builder) {
		xValue = builder.xValue;
		yValue = builder.yValue;
		momentValue = builder.momentValue;
		slope = builder.slope;
	}

	public double getX() {
		return xValue;
	}

	public double getY() {
		return yValue;
	}

	public double getMoment() {
		return momentValue;
	}
	
	public double getSlope() {
		return slope;
	}
}
