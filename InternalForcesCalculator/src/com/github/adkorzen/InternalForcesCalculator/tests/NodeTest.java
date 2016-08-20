package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Node;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;
import com.github.adkorzen.InternalForcesCalculator.elements.Support;
import com.github.adkorzen.InternalForcesCalculator.loads.NodeLoad;

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
		double actual2 = node.getSlope();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}

	@Test
	public void SetSupport_FixedSupportWithAngle_SupportSetsWithAngle() {

		node.setSupport(Support.FIXED, 30);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = Math.sqrt(3) / 3.0;
		double actual2 = node.getSlope();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}

	@Test
	public void SetSupport_FixedSupportWithDescendingAngle_NegativeSlope() {

		node.setSupport(Support.FIXED, 315);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = -1;
		double actual2 = node.getSlope();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}
	
	@Test
	public void SetSupport_FixedSupportWithAscendingAngle_PositiveSlope() {

		node.setSupport(Support.FIXED, 60);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = Math.sqrt(3);
		double actual2 = node.getSlope();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}
	
	@Test
	public void SetSupport_FixedSupportWithDescendingAngleAbove360_NegativeSlope() {

		node.setSupport(Support.FIXED, 1020);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = - Math.sqrt(3);
		double actual2 = node.getSlope();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}
	
	@Test
	public void SetSupport_FixedSupportWithAscendingAngleAbove360_PositiveSlope() {

		node.setSupport(Support.FIXED, 750);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = Math.sqrt(3) / 3;
		double actual2 = node.getSlope();

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2, Project.ACCURACY);
		assertNotNull(project.getSupport(3, 8));
	}
	
	@Test
	public void SetSupport_FixedSupportWithVerticalAngle_InfiniteSlope() {

		node.setSupport(Support.FIXED, -90);
		Support expected1 = Support.FIXED;
		Support actual1 = node.getSupport();

		double expected2 = Double.POSITIVE_INFINITY;
		double actual2 = node.getSlope();

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
	public void SetSupportAngle_AscendingAngle_PositiveSlope() {
		node.setSupport(Support.FIXED, -90);
		node.setSupportAngle(25);
		
		double actual = node.getSlope();
		double expected = 0.46630765815499859283000619479956;

		assertEquals(expected, actual, Project.ACCURACY);
	}
	
	@Test
	public void SetSupportAngle_DescendingAngle_NegativeSlope() {
		node.setSupport(Support.FIXED, -90);
		node.setSupportAngle(130);
		
		double actual = node.getSlope();
		double expected = -1.1917535925942099587053080718604;

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
	
	@Test
	public void AddLoad_FirstLoad_LoadListCreatedAndLoadAdded() {
		node.addLoad(new NodeLoad.Builder().y(5).build());
		
		assertNotNull(node.getLoads());
		assertEquals(5, node.getLoads().get(0).getY(), Project.ACCURACY);
	}
	
	@Test
	public void AddLoad_NextLoad_LoadAdded() {
		node.addLoad(new NodeLoad.Builder().y(5).build());
		node.addLoad(new NodeLoad.Builder().x(15).build());
		
		assertNotNull(node.getLoads());
		assertEquals(5, node.getLoads().get(0).getY(), Project.ACCURACY);
		assertEquals(15, node.getLoads().get(1).getX(), Project.ACCURACY);
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}

}
