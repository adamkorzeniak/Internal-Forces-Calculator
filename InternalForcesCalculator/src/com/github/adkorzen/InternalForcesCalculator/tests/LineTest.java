package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Line;
import com.github.adkorzen.InternalForcesCalculator.elements.Point;

public class LineTest {
	Line line;
	
	@Before
	public void setUp() {
		line = new Line(2, 5, 5, 7);
	}
	
	@Test
	public void Contains_PointOnLineInBeetween_ReturnTrue() {
		double y = 5 + 2.0 / 3.0;
		Point p = new Point (3, y);
		
		assertTrue(line.contains(p));
	}
	
	@Test
	public void Contains_PointOnLineOutside_ReturnTrue() {
		double y = 5 - 8.0 / 3.0;
		Point p = new Point (-2, y);
		
		assertTrue(line.contains(p));
	}
	
	@Test
	public void Contains_PointAboveLine_ReturnFalse() {
		double y = 5 + 4.0 / 3.0;
		Point p = new Point (3, y);
		
		assertFalse(line.contains(p));
	}
	@Test
	public void Contains_PointUnderLine_ReturnFalse() {
		double y = 5 - 4.0 / 3.0;
		Point p = new Point (3, y);
		
		assertFalse(line.contains(p));
	}
	
	

}
