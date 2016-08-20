package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;
import com.github.adkorzen.InternalForcesCalculator.loads.NodeLoad;

public class NodeLoadTest {

	@Test
	public void Constructor_AllValuesProvided_NodeLoadCreated() {

		NodeLoad load = new NodeLoad.Builder().x(10).y(15).moment(-20).build();
		
		assertEquals(10, load.getX(), Project.ACCURACY);
		assertEquals(15, load.getY(), Project.ACCURACY);
		assertEquals(-20, load.getMoment(), Project.ACCURACY);
	}
	
	@Test
	public void Constructor_XDirection_NodeLoadCreated() {

		NodeLoad load = new NodeLoad.Builder().x(-40).build();
		
		assertEquals(-40, load.getX(), Project.ACCURACY);
		assertEquals(0, load.getY(), Project.ACCURACY);
		assertEquals(0, load.getMoment(), Project.ACCURACY);
	}
	
	@Test
	public void Constructor_YDirection_NodeLoadCreated() {

		NodeLoad load = new NodeLoad.Builder().y(1.6).build();
		
		assertEquals(0, load.getX(), Project.ACCURACY);
		assertEquals(1.6, load.getY(), Project.ACCURACY);
		assertEquals(0, load.getMoment(), Project.ACCURACY);
	}
	
	@Test
	public void Constructor_Moment_NodeLoadCreated() {

		NodeLoad load = new NodeLoad.Builder().moment(11).build();
		
		assertEquals(0, load.getX(), Project.ACCURACY);
		assertEquals(0, load.getY(), Project.ACCURACY);
		assertEquals(11, load.getMoment(), Project.ACCURACY);
	}
}
