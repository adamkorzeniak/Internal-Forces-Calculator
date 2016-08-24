package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import static com.github.adkorzen.InternalForcesCalculator.matrix.Matrix.*;

import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class MatrixTest {

	@Test
	public void GausseElimination_3x3MatrixExample1_Eliminate() {
		double[][] matrix = { { 3, 1, 1, 6 }, { 2, -1, 3, 0 }, { 1, 3, -1, 6 } };

		double[] expecteds = { 2, 1, -1 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);

	}

	@Test
	public void GausseElimination_3x3MatrixExample2_Eliminate() {
		double[][] matrix = { { 1, 1, -2, 5 }, { 0, 2, 3, 6 }, { -1, 1, -5, -3 } };

		double[] expecteds = { 3.4, 2.4, 0.4 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_4x4MatrixExample3_Eliminate() {
		double[][] matrix = { { 1, 3, 3, 3, 1 }, { 3, 1, 3, 3, 1 }, { 3, 3, 1, 3, 1 }, { 3, 3, 3, 1, 1 } };

		double[] expecteds = { 0.1, 0.1, 0.1, 0.1 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_3x3MatrixExample4_Eliminate() {
		double[][] matrix = { { 1, 2, 3, 6 }, { 2, 3, -1, 4 }, { 3, 1, -4, 0 } };

		double[] expecteds = { 1, 1, 1 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_4x4MatrixExample5_NoSingleSolution() {
		double[][] matrix = { { 1, -1, 2, 2, 0 }, { 2, -2, 1, 0, 1 }, { -1, 2, 1, -2, 1 }, { 2, -1, 4, 0, 2 } };

		double[] expecteds = null;
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_4x4MatrixExample6_Eliminate() {
		double[][] matrix = { { 2, -1, 1, -1, 1 }, { 2, -1, 0, -3, 2 }, { 3, 0, -1, 1, -3 }, { 2, 2, -2, 5, -6 } };

		double[] expecteds = { 0, 2, 5.0 / 3, -4.0 / 3 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_3x3MatrixExample7_Eliminate() {
		double[][] matrix = { { 25, 5, 1, 106.8 }, { 64, 8, 1, 177.2 }, { 144, 12, 1, 279.2 } };

		double[] expecteds = { 61.0 / 210, 827.0 / 42, 38.0 / 35 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_3x3MatrixExample8_Eliminate() {
		double[][] matrix = { { 20, 15, 10, 45 }, { -3, -2.249, 7, 1.751 }, { 5, 1, 3, 9 } };

		double[] expecteds = { 1, 1, 1 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_4x4MatrixExample9_NoSingleSolution() {
		double[][] matrix = { { 1, -1, -2, 1, -1 }, { 2, -2, 1, -1, -2 }, { 3, -3, -1, 0, -3 }, { 5, -5, -1, 0, -5 } };

		double[] expecteds = null;
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_3x3MatrixExample10_Eliminate() {
		double[][] matrix = { { -1, 2, 1, -1 }, { 1, -3, -2, -1 }, { 3, -1, -1, 4 } };

		double[] expecteds = { 2, -1, 3 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_4x4MatrixExample11_Eliminate() {
		double[][] matrix = { { 4, -2, 4, -2, 8 }, { 3, 1, 4, 2, 7 }, { 2, 4, 2, 1, 10 }, { 2, -2, 4, 2, 2 } };

		double[] expecteds = { -1, 2, 3, -2 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_4x4MatrixExample12_Eliminate() {
		double[][] matrix = { { 2, 2, -1, 1, 7 }, { -1, 1, 2, 3, 3 }, { 3, -1, 4, -1, 31 }, { 1, 4, -2, 2, 2 } };

		double[] expecteds = { 4, 3, 5, -2 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_6x6MatrixExample13_Eliminate() {
		double[][] matrix = { { 1, 0, -1, 0, 1, 0, 0 }, { 0, -1, 0, 0, 0, -1, 0 }, { 0, 1, 0, -1, 1, 0, 0 },
				{ -1, 0, 0, 0, -1, -5, -21 }, { 0, -2, 0, 1, 0, 5, 10 }, { 0, 0, 1, 1, 1, 0, 17 } };

		double[] expecteds = { 37.0 / 3, -4.0 / 3, 43.0 / 3, 2.0 / 3, 2, 4.0 / 3 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}

	@Test
	public void GausseElimination_10x10MatrixExample14_Eliminate() {
		double[][] matrix = { { 2, -2, -4, 1, 1, -1, 0, 0, -2, -4, 4 }, { 4, -1, -3, -1, -3, 0, 2, -2, -2, 0, 3 },
				{ -2, -4, 3, 0, 2, 1, 0, 2, -4, -4, 2 }, { -4, 3, 1, -3, 2, -2, 4, -1, 0, -1, -1 },
				{ -2, -2, -3, 0, 0, -3, 0, -3, -3, -2, 4 }, { 1, 1, 4, 4, 2, 4, 4, -3, 3, 0, -3 },
				{ -4, -3, -3, -1, 3, 2, -1, 1, -2, -4, -1 }, { 0, 2, 0, 4, 2, -4, 0, 2, -3, -1, 3 },
				{ 1, 2, -1, 0, 0, -1, -4, -4, 4, 4, -4 }, { 4, 3, -2, 1, 1, 0, 4, 4, 1, -3, -3 } };

		double[] expecteds = { 1142840.0 / 203877, -6897647.0 / 203877, -1467959.0 / 203877, -507710.0 / 203877,
				1803823.0 / 67959, -1057429.0 / 67959, 4129048.0 / 203877, 738506.0 / 67959, 1680808.0 / 203877,
				2154311.0 / 67959 };
		double[] actuals = gaussianElimination(matrix);

		assertArrayEquals(expecteds, actuals, Project.ACCURACY);
	}
}
