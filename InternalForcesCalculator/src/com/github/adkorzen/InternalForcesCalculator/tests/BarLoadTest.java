package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;
import com.github.adkorzen.InternalForcesCalculator.loads.BarLoad;

public class BarLoadTest {

	@Test
	public void Constructor_AllValuesProvided_BarLoadCreated() {
		
		BarLoad load = new BarLoad.Builder().x1(-5).x2(10).y1(5).y2(2).moment1(11).moment2(1).build();
		
		assertEquals(-5, load.getX1(), Project.ACCURACY);
		assertEquals(10, load.getX2(), Project.ACCURACY);
		assertEquals(5, load.getY1(), Project.ACCURACY);
		assertEquals(2, load.getY2(), Project.ACCURACY);
		assertEquals(11, load.getMoment1(), Project.ACCURACY);
		assertEquals(1, load.getMoment2(), Project.ACCURACY);
		assertFalse(load.isProjected());
	}
	
	@Test
	public void Constructor_HalfValuesProvided_BarLoadCreated() {
		
		BarLoad load = new BarLoad.Builder().x1(-6).y1(1.5).moment2(23).projected().build();
		
		assertEquals(-6, load.getX1(), Project.ACCURACY);
		assertEquals(0, load.getX2(), Project.ACCURACY);
		assertEquals(1.5, load.getY1(), Project.ACCURACY);
		assertEquals(0, load.getY2(), Project.ACCURACY);
		assertEquals(0, load.getMoment1(), Project.ACCURACY);
		assertEquals(23, load.getMoment2(), Project.ACCURACY);
		assertTrue(load.isProjected());
	}
	
	@Test
	public void Constructor_SimpleValuesProvided_BarLoadCreated() {
		
		BarLoad load = new BarLoad.Builder().x(15).y(-20).moment(2).build();
		
		assertEquals(15, load.getX1(), Project.ACCURACY);
		assertEquals(15, load.getX2(), Project.ACCURACY);
		assertEquals(-20, load.getY1(), Project.ACCURACY);
		assertEquals(-20, load.getY2(), Project.ACCURACY);
		assertEquals(2, load.getMoment1(), Project.ACCURACY);
		assertEquals(2, load.getMoment2(), Project.ACCURACY);
		assertFalse(load.isProjected());
	}
	
	@Test
	public void Constructor_Projected_BarLoadCreated() {
		
		BarLoad load = new BarLoad.Builder().x(5).projected().build();
		
		assertTrue(load.isProjected());
		assertEquals(5, load.getX1(), Project.ACCURACY);
		assertEquals(5, load.getX2(), Project.ACCURACY);
		assertEquals(0, load.getY1(), Project.ACCURACY);
		assertEquals(0, load.getY2(), Project.ACCURACY);
		assertEquals(0, load.getMoment1(), Project.ACCURACY);
		assertEquals(0, load.getMoment2(), Project.ACCURACY);
	}

}
