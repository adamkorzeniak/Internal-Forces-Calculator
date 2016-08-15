package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Line;
import com.github.adkorzen.InternalForcesCalculator.elements.Point;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class LineTest {
	Line line;

	@Before
	public void setUp() {
		line = new Line(2, 5, 5, 7);
	}
	
	@Test
	public void Constructor_HorizontalLine_Angle0(){
		line = new Line (3, -1, 7, -1);
		double actual = line.getAngle();
		
		assertEquals(0, actual, Project.ACCURACY);
	}
	
	@Test
	public void Constructor_VerticalLine_AngleInfinity(){
		line = new Line (-2, 7, -2, -1);
		double actual = line.getAngle();
		
		assertEquals(Double.POSITIVE_INFINITY, actual, Project.ACCURACY);
	}
	
	@Test
	public void Constructor_AscendingLine_PositiveAngle(){
		line = new Line (1, 3, 3.5, 6.75);
		double actual = line.getAngle();
		
		assertEquals(1.5, actual, Project.ACCURACY);
	}
	
	@Test
	public void Constructor_DescendingLine_PositiveAngle(){
		line = new Line (4, 7, -2, 3);
		double expected = 2.0 / 3.0;
		double actual = line.getAngle();
		
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
}
