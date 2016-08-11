package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Node;
import com.github.adkorzen.InternalForcesCalculator.elements.Support;

public class NodeTest {
	private Node n;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		n = new Node(3, 8);
	    System.setOut(new PrintStream(outContent));
	}

	@Test
	public void NodeConstructor_NumbersProvided_CreatedAndSet() {

		assertEquals(3, n.getX());
		assertEquals(8, n.getY());
	}

	@Test
	public void SetSupport_HingedSupport_SupportSetsWithNoAngle() {

		n.setSupport(Support.HINGED);
		Support expected1 = Support.HINGED;
		Support actual1 = n.getSupport();
		
		double expected2 = 0.0;
		double actual2 = n.getSupportAngle();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, 0.001);
	}
	
	@Test
	public void SetSupport_FixedSupportWithAngle_SupportSetsWithAngle() {

		n.setSupport(Support.FIXED, 11.5);
		Support expected1 = Support.FIXED;
		Support actual1 = n.getSupport();
		
		double expected2 = 11.5;
		double actual2 = n.getSupportAngle();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, 0.001);
	}
	
	@Test
	public void SetSupport_FixedSupportWithNegativeAngle_SupportSetsWithPositiveAngle() {

		n.setSupport(Support.FIXED, - 111.5);
		Support expected1 = Support.FIXED;
		Support actual1 = n.getSupport();
		
		double expected2 = 248.5;
		double actual2 = n.getSupportAngle();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, 0.001);
	}
	
	@Test
	public void SetSupport_FixedSupportWithAbove360Angle_SupportSetsWithUnder360Angle() {

		n.setSupport(Support.FIXED, 1000);
		Support expected1 = Support.FIXED;
		Support actual1 = n.getSupport();
		
		double expected2 = 280;
		double actual2 = n.getSupportAngle();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, 0.001);
	}
	
	@Test
	public void SetSupportAngle_NoSupportSets_PrintsErrorMessage() {

		String expected = "There is no support in this node. Angle cannot be changed.";
		n.setSupportAngle(25);
		
		assertEquals(expected, outContent.toString().trim());
	}
	
	@Test
	public void SetSupportAngle_NegativeAngle_Adds360UntilPositive() {

		double expected = 247.4;
		n.setSupport(Support.FIXED);
		n.setSupportAngle(- 472.6);
		double actual = n.getSupportAngle();
		
		assertEquals(expected, actual, 0.001);
	}
	
	@Test
	public void SetSupportAngle_Over360_Subtracts360UntilUnder360() {

		double expected = 138.1;
		n.setSupport(Support.FIXED);
		n.setSupportAngle(1218.1);
		double actual = n.getSupportAngle();
		
		assertEquals(expected, actual, 0.001);
	}
	
	@After
	public void tearDown() {
	    System.setOut(null);
	}

}
