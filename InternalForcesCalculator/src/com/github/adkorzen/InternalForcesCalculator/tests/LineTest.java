package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Line;
import com.github.adkorzen.InternalForcesCalculator.elements.Point;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class LineTest {
	Line line;
	Point p;

	@Before
	public void setUp() {
		line = new Line(2, 5, 5, 7);
		p = new Point(3, 6);
	}

	@Test
	public void Constructor_HorizontalLine_Slope0() {
		line = new Line(3, -1, 7, -1);
		double actual = line.getSlope();

		assertEquals(0, actual, Project.ACCURACY);
	}

	@Test
	public void Constructor_VerticalLine_SlopeInfinity() {
		line = new Line(-2, 7, -2, -1);
		double actual = line.getSlope();

		assertEquals(Double.POSITIVE_INFINITY, actual, Project.ACCURACY);
	}

	@Test
	public void Constructor_AscendingLine_PositiveSlope() {
		line = new Line(1, 3, 3.5, 6.75);
		double actual = line.getSlope();

		assertEquals(1.5, actual, Project.ACCURACY);
	}

	@Test
	public void Constructor_DescendingLine_PositiveSlope() {
		line = new Line(-2, 7, 4, 3);
		double expected = -2.0 / 3.0;
		double actual = line.getSlope();

		assertEquals(expected, actual, Project.ACCURACY);
	}

	@Test
	public void SecondConstructor_HorizontalLine_Slope0() {
		line = new Line(p, 0);
		double actual = line.getSlope();

		assertEquals(0, actual, Project.ACCURACY);
	}

	@Test
	public void SecondConstructor_VerticalLine_SlopeInfinity() {
		line = new Line(p, Double.POSITIVE_INFINITY);
		double actual = line.getSlope();

		assertEquals(Double.POSITIVE_INFINITY, actual, Project.ACCURACY);
	}

	@Test
	public void SecondConstructor_AscendingLine_PositiveSlope() {
		line = new Line(p, 1.5);
		double actual = line.getSlope();

		assertEquals(1.5, actual, Project.ACCURACY);
	}

	@Test
	public void SecondConstructor_DescendingLine_PositiveSlope() {
		line = new Line(p, -2.0 / 3.0);
		double expected = -2.0 / 3.0;
		double actual = line.getSlope();

		assertEquals(expected, actual, Project.ACCURACY);
	}

	@Test
	public void Contains_PointOnLineInBeetween_ReturnTrue() {
		double y = 5 + 2.0 / 3.0;
		Point p = new Point(3, y);

		assertTrue(line.contains(p));
	}

	@Test
	public void Contains_PointOnLineOutside_ReturnTrue() {
		double y = 5 - 8.0 / 3.0;
		Point p = new Point(-2, y);

		assertTrue(line.contains(p));
	}

	@Test
	public void Contains_PointAboveLine_ReturnFalse() {
		double y = 5 + 4.0 / 3.0;
		Point p = new Point(3, y);

		assertFalse(line.contains(p));
	}

	@Test
	public void Contains_PointUnderLine_ReturnFalse() {
		double y = 5 - 4.0 / 3.0;
		Point p = new Point(3, y);

		assertFalse(line.contains(p));
	}

	@Test
	public void Contains_HorizontalLinePointAbove_ReturnFalse() {
		line = new Line(1, 3.5, 7, 3.5);
		Point p = new Point(0, 4);

		assertFalse(line.contains(p));
	}

	@Test
	public void Contains_HorizontalLinePointUnder_ReturnFalse() {
		line = new Line(1, 3.5, 7, 3.5);
		Point p = new Point(5, 0);

		assertFalse(line.contains(p));
	}

	@Test
	public void Contains_HorizontalLinePointBetween_ReturnTrue() {
		line = new Line(1, 3.5, 7, 3.5);
		Point p = new Point(2, 3.5);

		assertTrue(line.contains(p));
	}

	@Test
	public void Contains_HorizontalLinePointToLeft_ReturnTrue() {
		line = new Line(1, 3.5, 7, 3.5);
		Point p = new Point(0, 3.5);

		assertTrue(line.contains(p));
	}

	@Test
	public void Contains_HorizontalLinePointToRight_ReturnTrue() {
		line = new Line(1, 3.5, 7, 3.5);
		Point p = new Point(10, 3.5);

		assertTrue(line.contains(p));
	}

	@Test
	public void Contains_VerticalLinePointToRight_ReturnFalse() {
		line = new Line(3, 9.5, 3, 2);
		Point p = new Point(4, 7);

		assertFalse(line.contains(p));
	}

	@Test
	public void Contains_VerticalLinePointToLeft_ReturnFalse() {
		line = new Line(3, 9.5, 3, 2);
		Point p = new Point(-2, 1);

		assertFalse(line.contains(p));
	}

	@Test
	public void Contains_VerticalLinePointBetween_ReturnTrue() {
		line = new Line(3, 9.5, 3, 2);
		Point p = new Point(3, 6);

		assertTrue(line.contains(p));
	}

	@Test
	public void Contains_VerticalLinePointUp_ReturnTrue() {
		line = new Line(3, 9.5, 3, 2);
		Point p = new Point(3, 11);

		assertTrue(line.contains(p));
	}

	@Test
	public void Contains_VerticalLinePointDown_ReturnTrue() {
		line = new Line(3, 9.5, 3, 2);
		Point p = new Point(3, 0);

		assertTrue(line.contains(p));
	}

	@Test
	public void GetCrossPoint_TwoHorizontalParallelLines_PointInInfinity() {
		line = new Line(0, 5, 3, 5);
		Line other = new Line(2, 0, 3, 0);

		double expected = Double.POSITIVE_INFINITY;
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p.getX(), Project.ACCURACY);
		assertEquals(0, p.getY(), Project.ACCURACY);
	}

	@Test
	public void GetCrossPoint_TwoVerticalParallelLines_PointInInfinity() {
		line = new Line(3, 5, 3, 0);
		Line other = new Line(2, 0, 2, 7);

		double expected = Double.POSITIVE_INFINITY;
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p.getX(), Project.ACCURACY);
		assertEquals(expected, p.getY(), Project.ACCURACY);
	}
	
	@Test
	public void GetCrossPoint_TwoAscendindParallelLines_PointInInfinity() {
		line = new Line(new Point(3, 7), 10);
		Line other = new Line(new Point(11, 12), 10);

		double expected = Double.POSITIVE_INFINITY;
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p.getX(), Project.ACCURACY);
		assertEquals(10, p.getY(), Project.ACCURACY);
	}
	
	@Test
	public void GetCrossPoint_TwoDescendindParallelLines_PointInInfinity() {
		line = new Line(new Point(6, 9), -1);
		Line other = new Line(new Point(1, 2), -1);

		double expected = Double.POSITIVE_INFINITY;
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p.getX(), Project.ACCURACY);
		assertEquals(-1, p.getY(), Project.ACCURACY);
	}

	@Test
	public void GetCrossPoint_VerticalAndHorizontalLine_PointReturned() {
		line = new Line(3, 5, 3, 0);
		Line other = new Line(2, 2, 7, 2);

		Point expected = new Point(3, 2);
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_HorizontalAndVerticalLine_PointReturned() {
		line = new Line(3, 5, 3, 0);
		Line other = new Line(2, 2, 7, 2);

		Point expected = new Point(3, 2);
		Point p = other.getCrossPoint(line);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_HorizontalAndAscendingLine_PointReturned() {
		line = new Line(0, 5, 7, 12);
		Line other = new Line(3, 0, 17, 0);
 
		Point expected = new Point(-5, 0);
		Point p = other.getCrossPoint(line);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_AscendingAndHorizontalLine_PointReturned() {
		line = new Line(0, 5, 7, 12);
		Line other = new Line(3, 0, 17, 0);
 
		Point expected = new Point(-5, 0);
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_HorizontalAndDescendingLine_PointReturned() {
		line = new Line(0, 5, 7, -9);
		Line other = new Line(3, 1, 17, 1);
 
		Point expected = new Point(2, 1);
		Point p = other.getCrossPoint(line);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_DescendingAndHorizontalLine_PointReturned() {
		line = new Line(0, 5, 7, -9);
		Line other = new Line(3, 1, 17, 1);
 
		Point expected = new Point(2, 1);
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p);
	}
	
	
	
	@Test
	public void GetCrossPoint_VerticalAndAscendingLine_PointReturned() {
		line = new Line(0, 5, 7, 12);
		Line other = new Line(3, 0, 3, 10);
 
		Point expected = new Point(3, 8);
		Point p = other.getCrossPoint(line);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_AscendingAndVerticalLine_PointReturned() {
		line = new Line(0, 5, 7, 12);
		Line other = new Line(3, 0, 3, 10);
 
		Point expected = new Point(3, 8);
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_VerticalAndDescendingLine_PointReturned() {
		line = new Line(0, 5, 7, -9);
		Line other = new Line(3, 0, 3, 10);
 
		Point expected = new Point(3, -1);
		Point p = other.getCrossPoint(line);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_DescendingAndVerticalLine_PointReturned() {
		line = new Line(0, 5, 7, -9);
		Line other = new Line(3, 0, 3, 10);
 
		Point expected = new Point(3, -1);
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_TwoAscendingLines_PointReturned() {
		line = new Line(2, 3, 7, 13);
		Line other = new Line(3, 0, 5, 6);
 
		Point expected = new Point(8, 15);
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_TwoDescendingLines_PointReturned() {
		line = new Line(4, 1.2, 7, -4.8);
		Line other = new Line(2, 7.6, 5, -4.4);
 
		Point expected = new Point(3.2, 2.8);
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_AscendingAndDescendingLine_PointReturned() {
		line = new Line(3, 18.1, 0, 9.1);
		Line other = new Line(1.5, -14.4, 0, -6.9);
 
		Point expected = new Point(-2, 3.1);
		Point p = line.getCrossPoint(other);

		assertEquals(expected, p);
	}
	
	@Test
	public void GetCrossPoint_DescendingAndAscendingLine_PointReturned() {
		line = new Line(3, 18.1, 0, 9.1);
		Line other = new Line(1.5, -14.4, 0, -6.9);
 
		Point expected = new Point(-2, 3.1);
		Point p = other.getCrossPoint(line);

		assertEquals(expected, p);
	}
}
