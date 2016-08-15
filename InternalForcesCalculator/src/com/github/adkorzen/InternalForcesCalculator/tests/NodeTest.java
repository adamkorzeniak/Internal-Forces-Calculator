package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Node;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;
import com.github.adkorzen.InternalForcesCalculator.elements.Support;

public class NodeTest {
	private Project project;
	private Node node;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		project = new Project("test");
		node = new Node(project, 3, 8);
		System.setOut(new PrintStream(outContent));
	}

	@Test
	public void NodeConstructor_NumbersProvided_CreatedAndSet() {
		double expectedX = 3.0;
		double actualX = node.getX();
		double expectedY = 8.0;
		double actualY = node.getY();

		assertEquals(expectedX, actualX, Project.ACCURACY);
		assertEquals(expectedY, actualY, Project.ACCURACY);
	}

	@Test
	public void SetSupport_HingedSupport_SupportSetsWithNoAngle() {

		node.setSupport(Support.HINGED);
		Support expected1 = Support.HINGED;
		Support actual1 = node.getSupport();

		double expected2 = 0.0;
		double actual2 = node.getSupportAngle();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}

	@Test
	public void SetSupport_FixedSupportWithAngle_SupportSetsWithAngle() {

		node.setSupport(Support.FIXED, 11.5);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = 11.5;
		double actual2 = node.getSupportAngle();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}

	@Test
	public void SetSupport_FixedSupportWithNegativeAngle_SupportSetsWithPositiveAngle() {

		node.setSupport(Support.FIXED, -111.5);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = 248.5;
		double actual2 = node.getSupportAngle();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}

	@Test
	public void SetSupport_FixedSupportWithAbove360Angle_SupportSetsWithUnder360Angle() {

		node.setSupport(Support.FIXED, 1000);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = 280;
		double actual2 = node.getSupportAngle();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}

	@Test
	public void SetSupportAngle_NoSupportSet_PrintsErrorMessage() {

		String expected = "There is no support in this node. Angle cannot be changed.";
		node.setSupportAngle(25);

		assertEquals(expected, outContent.toString().trim());
	}

	@Test
	public void SetSupportAngle_NegativeAngle_Adds360UntilPositive() {

		double expected = 247.4;
		node.setSupport(Support.FIXED);
		node.setSupportAngle(-472.6);
		double actual = node.getSupportAngle();

		assertEquals(expected, actual, Project.ACCURACY);
	}

	@Test
	public void SetSupportAngle_Over360_Subtracts360UntilUnder360() {

		double expected = 138.1;
		node.setSupport(Support.FIXED);
		node.setSupportAngle(1218.1);
		double actual = node.getSupportAngle();

		assertEquals(expected, actual, Project.ACCURACY);
	}

	@Test
	public void RemoveSupport_NoPreviousSupport_NothingChanged() {
		node.removeSupport();

		assertNull(node.getSupport());
		assertNull(project.getSupport(3, 8));
	}

	@Test
	public void RemoveSupport_NodeSupported_SupportRemoved() {
		node.setSupport(Support.SLIDER);
		node.removeSupport();

		assertNull(node.getSupport());
		assertNull(project.getSupport(3, 8));
	}

	// @Test
	// public void AddBar_EmptyBarList_BarAdded() {
	// Bar b = new Bar(project, node, new Node(project, 0,0));
	// node.addBar(b);
	//
	// int actual = node.getBarList().size();
	//
	// assertEquals(1, actual);
	// assertEquals(b, node.getBarList().get(0));
	// }
	//
	// @Test
	// public void AddBar_ListWithOneBar_TwoBarsAdded() {
	// node.addBar(new Bar(project, node, new Node(project, 0,0)));
	// Bar b = new Bar(project, node, new Node(project, 5,0));
	// node.addBar(b);
	//
	// int actual = node.getBarList().size();
	//
	// assertEquals(2, actual);
	// assertEquals(b, node.getBarList().get(1));
	// }
	//
	// @Test
	// public void RemoveBar_RemoveBar_BarRemoved() {
	// node.addBar(new Bar(project, node, new Node(project, 0,0)));
	// Bar b = new Bar(project, node, new Node(project, 5,0));
	// node.addBar(b);
	// node.removeBar(b);
	// int actual = node.getBarList().size();
	//
	// assertEquals(1, actual);
	// }

	@After
	public void tearDown() {
		System.setOut(null);
	}

}
