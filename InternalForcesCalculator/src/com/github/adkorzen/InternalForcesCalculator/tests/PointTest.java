package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Test;

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
	public void Constructor_PointInInfinity_FieldsSetCorrectly() {
		Point p = new Point(90);
		
		double x = p.getX();
		double y = p.getY();
		
		assertEquals(Double.POSITIVE_INFINITY, x, Project.ACCURACY);
		assertEquals(90, y, Project.ACCURACY);
	}

}
