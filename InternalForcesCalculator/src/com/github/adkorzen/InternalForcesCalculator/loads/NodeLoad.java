package com.github.adkorzen.InternalForcesCalculator.loads;

public class NodeLoad{
	private final double xValue;
	private final double yValue;
	private final double momentValue;
	
	public static class Builder {
		private double xValue = 0;
		private double yValue = 0;
		private double momentValue = 0;

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
		
		public NodeLoad build() {
			return new NodeLoad(this);
		}
	}
	
	private NodeLoad(Builder builder) {
		xValue = builder.xValue;
		yValue = builder.yValue;
		momentValue = builder.momentValue;
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
}
