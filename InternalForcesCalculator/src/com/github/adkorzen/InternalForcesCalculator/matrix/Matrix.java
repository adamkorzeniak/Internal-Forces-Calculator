package com.github.adkorzen.InternalForcesCalculator.matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Matrix {
	private static final int ACCURACY = 30;
	private static final BigDecimal accuracy = new BigDecimal(1).divide(new BigDecimal(10).pow(ACCURACY), ACCURACY,
			RoundingMode.HALF_UP);

	public static double[] gaussianElimination(double[][] m) {
		BigDecimal[][] matrix = fromDoubleToBigDecimal(m);
		int size = matrix.length;

		for (int column = 0; column < size; column++) {
			matrix = setFirstLineWithBiggestAbsolute(matrix, column);
			if (matrix[column][column].abs().compareTo(accuracy) < 0) {
				return null;
			}
			for (int row = column + 1; row < size; row++) {
				matrix = eliminate(matrix, row, column);
			}
		}

		for (int column = 1; column < size; column++) {
			for (int row = 0; row < column; row++) {
				matrix = eliminate(matrix, row, column);
			}
		}

		for (int row = 0; row < size; row++) {
			BigDecimal divider = matrix[row][row];
			matrix[row][row] = matrix[row][row].divide(divider, ACCURACY, RoundingMode.HALF_UP);
			matrix[row][size] = matrix[row][size].divide(divider, ACCURACY, RoundingMode.HALF_UP);
		}

		double[] result = getResult(matrix);
		return result;
	}

	private static double[] getResult(BigDecimal[][] matrix) {
		int size = matrix.length;
		double[] results = new double[size];
		for (int i = 0; i < size; i++) {
			results[i] = matrix[i][size].doubleValue();
		}
		return results;
	}

	private static BigDecimal[][] fromDoubleToBigDecimal(double[][] m) {
		int size = m.length;
		BigDecimal[][] result = new BigDecimal[size][size + 1];
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size + 1; column++) {
				result[row][column] = new BigDecimal(m[row][column]);
			}
		}
		return result;
	}

	private static BigDecimal[][] setFirstLineWithBiggestAbsolute(BigDecimal[][] matrix, int column) {
		int size = matrix.length;
		BigDecimal biggestAbsolute = BigDecimal.ZERO;
		int rowIndex = -1;
		for (int row = column; row < size; row++) {
			BigDecimal check = matrix[row][column];
			if (check.abs().compareTo(biggestAbsolute) > 0) {
				biggestAbsolute = check.abs();
				rowIndex = row;
			}
		}
		// column == row
		if (rowIndex > -1) {
			BigDecimal[] temporary = matrix[column];
			matrix[column] = matrix[rowIndex];
			matrix[rowIndex] = temporary;
		}
		return matrix;
	}

	private static BigDecimal[][] eliminate(BigDecimal[][] matrix, int row, int column) {
		BigDecimal original = matrix[row][column];
		if (original.abs().compareTo(accuracy) < 0) {
			return matrix;
		}
		int size = matrix.length;
		BigDecimal divider = matrix[column][column];
		BigDecimal multiplier = original.divide(divider, ACCURACY, RoundingMode.HALF_UP);
		for (int i = column; i < size + 1; i++) {
			matrix[row][i] = matrix[row][i].subtract(matrix[column][i].multiply(multiplier));
		}
		return matrix;
	}
}