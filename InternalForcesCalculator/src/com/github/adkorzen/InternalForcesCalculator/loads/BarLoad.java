package com.github.adkorzen.InternalForcesCalculator.loads;

public class BarLoad{
	private final double x1Value;
	private final double x2Value;
	private final double y1Value;
	private final double y2Value;
	private final double moment1Value;
	private final double moment2Value;
	private final boolean projected;
	
	public static class Builder {
		private double x1Value = 0;
		private double x2Value = 0;
		private double y1Value = 0;
		private double y2Value = 0;
		private double moment1Value = 0;
		private double moment2Value = 0;
		private boolean projected = false;

		public Builder x(double value) {
			x1Value = value;
			x2Value = value;
			return this;
		}
		
		public Builder y(double value) {
			y1Value = value;
			y2Value = value;
			return this;
		}
		
		public Builder moment(double value) {
			moment1Value = value;
			moment2Value = value;
			return this;
		}
		
		public Builder x1(double value) {
			x1Value = value;
			return this;
		}
		
		public Builder x2(double value) {
			x2Value = value;
			return this;
		}
		
		public Builder y1(double value) {
			y1Value = value;
			return this;
		}
		
		public Builder y2(double value) {
			y2Value = value;
			return this;
		}
		
		public Builder moment1(double value) {
			moment1Value = value;
			return this;
		}
		
		public Builder moment2(double value) {
			moment2Value = value;
			return this;
		}
		
		public Builder projected() {
			projected = true;
			return this;
		}
		
		public BarLoad build() {
			return new BarLoad(this);
		}
	}
	
	private BarLoad(Builder builder) {
		x1Value = builder.x1Value;
		x2Value = builder.x2Value;
		y1Value = builder.y1Value;
		y2Value = builder.y2Value;
		moment1Value = builder.moment1Value;
		moment2Value = builder.moment2Value;
		projected = builder.projected;
	}

	public double getX1() {
		return x1Value;
	}

	public double getX2() {
		return x2Value;
	}

	public double getY1() {
		return y1Value;
	}

	public double getY2() {
		return y2Value;
	}

	public double getMoment1() {
		return moment1Value;
	}

	public double getMoment2() {
		return moment2Value;
	}
	
	public boolean isProjected() {
		return projected;
	}
	
}
