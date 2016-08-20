package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Line;
import com.github.adkorzen.InternalForcesCalculator.elements.Point;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class PointTest {

	@Test
	public void Constructor_NormalCoordinates_FieldsSetCorrectly() {
		Point p = new Point(-3.6, 17.0);

		double x = p.getX();
		double y = p.getY();

		assertEquals(-3.6, x, Project.ACCURACY);
		assertEquals(17, y, Project.ACCURACY);
	}

	@Test
	public void Constructor_PointInInfinity_PointInInfinitySet() {
		Point p = new Point(17);

		double x = p.getX();
		double y = p.getY();

		assertEquals(Double.POSITIVE_INFINITY, x, Project.ACCURACY);
		assertEquals(17, y, Project.ACCURACY);
	}

	@Test
	public void GetCommonLine_HorizontallyLocatedPoints_HorizontalLine() {
		Point p1 = new Point(3, 6);
		Point p2 = new Point(7, 6);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(p1, 0);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_VerticallyLocatedPoints_VerticalLine() {
		Point p1 = new Point(3, 1);
		Point p2 = new Point(3, 6);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(p1, Double.POSITIVE_INFINITY);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_AscendingPoints_AscendingLine() {
		Point p1 = new Point(3, 6);
		Point p2 = new Point(7, 12);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(p1, 1.5);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_DescendingPoints_DescendingLine() {
		Point p1 = new Point(0, 5);
		Point p2 = new Point(2, 0);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(p1, -2.5);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_OnePointInInfinity_LineThroughPointWithSlope() {
		Point p1 = new Point(Double.POSITIVE_INFINITY, 1.7);
		Point p2 = new Point(2, 0);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(p2, 1.7);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_OnePointInInfinity_VerticalLineThroughPoint() {
		Point p1 = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		Point p2 = new Point(2, 0);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(p2, Double.POSITIVE_INFINITY);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_OnePointInInfinity_HorizontalLineThroughPoint() {
		Point p1 = new Point(Double.POSITIVE_INFINITY, 0);
		Point p2 = new Point(2, 0);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(p2, 0);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_TwoPointsInParallelInfinity_ParallelLine() {
		Point p1 = new Point(Double.POSITIVE_INFINITY, 30);
		Point p2 = new Point(Double.POSITIVE_INFINITY, 30);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(new Point(0, 0), 30);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_TwoPointsInHorizontalInfinity_ParallelLine() {
		Point p1 = new Point(Double.POSITIVE_INFINITY, 0);
		Point p2 = new Point(Double.POSITIVE_INFINITY, 0);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(new Point(0, 0), 0);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_TwoPointsInVerticalInfinity_ParallelLine() {
		Point p1 = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		Point p2 = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = new Line(new Point(0, 0), Double.POSITIVE_INFINITY);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetCommonLine_TwoPointsInNotParallelInfinity_NullLine() {
		Point p1 = new Point(Double.POSITIVE_INFINITY, 40);
		Point p2 = new Point(Double.POSITIVE_INFINITY, 30);
		
		Line actual = p1.getCommonLine(p2);
		Line expected = null;
		
		assertEquals(expected, actual);
	}
}
