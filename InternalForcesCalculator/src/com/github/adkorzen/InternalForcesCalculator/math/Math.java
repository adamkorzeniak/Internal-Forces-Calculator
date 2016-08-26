package com.github.adkorzen.InternalForcesCalculator.math;

public class Math {

	public static double degreeToTangent(double degrees) {
		double result = 0;
		while (degrees < 0) {
			degrees += 180;
		}
		if (degrees % 180 == 90) {
			result = Double.POSITIVE_INFINITY;
		} else {
			double inRadius = degrees * java.lang.Math.PI / 180;
			result = java.lang.Math.tan(inRadius);
		}
		return result;
	}

	public static double tangentToSinus(double tangent) {
		if (tangent == Double.POSITIVE_INFINITY) {
			return 1;
		}
		double toRoot = (tangent * tangent) / (tangent * tangent + 1);
		double result = java.lang.Math.pow(toRoot, 0.5);
		if (tangent < 0) {
			result *= -1;
		}
		return result;
	}

	public static double tangentToCosinus(double tangent) {
		if (tangent == Double.POSITIVE_INFINITY) {
			return 0;
		}
		double toRoot = 1 / (1 + tangent * tangent);
		double result = java.lang.Math.pow(toRoot, 0.5);
		return result;
	}
}
