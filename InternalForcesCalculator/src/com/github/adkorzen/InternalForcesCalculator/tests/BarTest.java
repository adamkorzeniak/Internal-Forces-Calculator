package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Bar;
import com.github.adkorzen.InternalForcesCalculator.elements.Node;
import com.github.adkorzen.InternalForcesCalculator.elements.Point;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;
import com.github.adkorzen.InternalForcesCalculator.elements.Support;

public class BarTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private Project project;
	private Bar bar;

	@Before
	public void setUp() {
		project = new Project("name");
		bar = new Bar(project, new Node(project, 2.3, 3.5), new Node(project, -1.2, 3));
		System.setOut(new PrintStream(outContent));
	}

	@Test
	public void BarConstructor_ContainingTwoPreCreatedNodes_CreatesBar() {
		double actualStartX = bar.getEndingNode().getX();
		double actualStartY = bar.getEndingNode().getY();
		double actualEndX = bar.getStartingNode().getX();
		double actualEndY = bar.getStartingNode().getY();

		assertEquals(2.3, actualStartX, 0.0001);
		assertEquals(3.5, actualStartY, 0.0001);
		assertEquals(-1.2, actualEndX, 0.0001);
		assertEquals(3, actualEndY, 0.0001);
	}

	@Test
	public void SetStartingNodeJoint_NodeNotJoint_NodeJoint() {
		bar.setStartingNodeJoint(true);
		boolean condition = bar.isStartingNodeJoint();

		assertTrue(condition);
	}

	@Test
	public void SetEndingNodeJoint_NodeNotJoint_NodeJoint() {
		bar.setEndingNodeJoint(true);
		boolean condition = bar.isEndingNodeJoint();

		assertTrue(condition);
	}

	@Test
	public void SetStartingNodeJoint_NodeJoint_NodeNotJoint() {
		bar.setStartingNodeJoint(true);
		bar.setStartingNodeJoint(false);
		boolean condition = bar.isStartingNodeJoint();

		assertFalse(condition);
	}

	@Test
	public void SetEndingNodeJoint_NodeJoint_NodeNotJoint() {
		bar.setEndingNodeJoint(true);
		bar.setEndingNodeJoint(false);
		boolean condition = bar.isEndingNodeJoint();

		assertFalse(condition);
	}

	@Test
	public void SetNodeJoint_FixedSupportedStartingNode_ChangeSupportToHinged() {
		bar.getStartingNode().setSupport(Support.FIXED);
		bar.setStartingNodeJoint(true);
		Support expected = Support.HINGED;
		Support actual = bar.getStartingNode().getSupport();

		assertEquals(expected, actual);
		assertFalse(bar.isStartingNodeJoint());
	}

	@Test
	public void SetNodeJoint_SliderSupportedStartingNode_ChangeSupportToRoller() {
		bar.getStartingNode().setSupport(Support.SLIDER);
		bar.setStartingNodeJoint(true);
		Support expected = Support.ROLLER;
		Support actual = bar.getStartingNode().getSupport();

		assertEquals(expected, actual);
		assertFalse(bar.isStartingNodeJoint());
	}

	@Test
	public void SetNodeJoint_RotationFreeStartingNode_RefuseToJointAndOutputError() {
		bar.getStartingNode().setSupport(Support.HINGED);
		bar.setStartingNodeJoint(true);
		String expected = "Cannot release node with not blocked rotation.";
		boolean condition = bar.isStartingNodeJoint();

		assertEquals(expected, outContent.toString().trim());
		assertFalse(condition);

	}

	@Test
	public void SetNodeJoint_FixedSupportedEndingNode_ChangeSupportToHinged() {
		bar.getEndingNode().setSupport(Support.FIXED);
		bar.setEndingNodeJoint(true);
		Support expected = Support.HINGED;
		Support actual = bar.getEndingNode().getSupport();

		assertEquals(expected, actual);
		assertFalse(bar.isEndingNodeJoint());
	}

	@Test
	public void SetNodeJoint_SliderSupportedEndingNode_ChangeSupportToRoller() {
		bar.getEndingNode().setSupport(Support.SLIDER);
		bar.setEndingNodeJoint(true);
		Support expected = Support.ROLLER;
		Support actual = bar.getEndingNode().getSupport();

		assertEquals(expected, actual);
		assertFalse(bar.isEndingNodeJoint());
	}

	@Test
	public void SetNodeJoint_RotationFreeEndingNode_RefuseToJointAndOutputError() {
		bar.getEndingNode().setSupport(Support.HINGED);
		bar.setEndingNodeJoint(true);
		String expected = "Cannot release node with not blocked rotation.";
		boolean condition = bar.isEndingNodeJoint();

		assertEquals(expected, outContent.toString().trim());
		assertFalse(condition);
	}

	@Test
	public void Contains_PointOnBar_ReturnsTrue() {
		double x = 1.3;
		double y = 2.5 / 7 + 3;
		Point p = new Point(x, y);
		boolean condition = bar.contains(p);

		assertTrue(condition);
	}

	@Test
	public void Contains_PointOnBarRightEnd_ReturnsTrue() {
		double x = 2.3;
		double y = 3.5;
		Point p = new Point(x, y);
		boolean condition = bar.contains(p);

		assertTrue(condition);
	}

	@Test
	public void Contains_PointOnBarLeftEnd_ReturnsTrue() {
		double x = -1.2;
		double y = 3;
		Point p = new Point(x, y);
		boolean condition = bar.contains(p);

		assertTrue(condition);
	}

	@Test
	public void Contains_PointOffBar_ReturnsFalse() {
		Point p = new Point(3, 7.5);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}

	@Test
	public void Contains_PointOnDirectionLeftFromBar_ReturnsFalse() {
		double x = -2;
		double y = -0.8 / 7 + 3;
		;
		Point p = new Point(x, y);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}

	@Test
	public void Contains_PointOnDirectionRightFromBar_ReturnsFalse() {
		double x = 4;
		double y = 1.7 / 7 + 3.5;
		Point p = new Point(x, y);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}

	@Test
	public void PairOfNodes_FromLeftDown_StartBecomeFirst() {
		Node start = new Node(project, -2.5, 3);
		Node end = new Node(project, 3, 4.3);
		bar = new Bar(project, start, end);

		assertEquals(start, bar.getStartingNode());
		assertEquals(end, bar.getEndingNode());
	}

	@Test
	public void PairOfNodes_FromLeftUp_StartBecomeFirst() {
		Node start = new Node(project, 3, 7);
		Node end = new Node(project, 4, 4.3);
		bar = new Bar(project, start, end);

		assertEquals(start, bar.getStartingNode());
		assertEquals(end, bar.getEndingNode());
	}

	@Test
	public void PairOfNodes_FromRightDown_EndBecomeFirst() {
		Node start = new Node(project, -2, 3);
		Node end = new Node(project, -4, 4.3);
		bar = new Bar(project, start, end);

		assertEquals(start, bar.getEndingNode());
		assertEquals(end, bar.getStartingNode());
	}

	@Test
	public void PairOfNodes_FromRightUp_EndBecomeFirst() {
		Node start = new Node(project, 3, 7);
		Node end = new Node(project, -4, 4.3);
		bar = new Bar(project, start, end);

		assertEquals(start, bar.getEndingNode());
		assertEquals(end, bar.getStartingNode());
	}

	@Test
	public void PairOfNodes_FromLeft_StartBecomeFirst() {
		Node start = new Node(project, 1, 7);
		Node end = new Node(project, 6, 7);
		bar = new Bar(project, start, end);

		assertEquals(start, bar.getStartingNode());
		assertEquals(end, bar.getEndingNode());
	}

	@Test
	public void PairOfNodes_FromRight_EndBecomeFirst() {
		Node start = new Node(project, 3, 4.3);
		Node end = new Node(project, -4, 4.3);
		bar = new Bar(project, start, end);

		assertEquals(start, bar.getEndingNode());
		assertEquals(end, bar.getStartingNode());
	}

	@Test
	public void PairOfNodes_FromDown_StartBecomeFirst() {
		Node start = new Node(project, 1, 3.5);
		Node end = new Node(project, 1, 3.6);
		bar = new Bar(project, start, end);

		assertEquals(start, bar.getStartingNode());
		assertEquals(end, bar.getEndingNode());
	}

	@Test
	public void PairOfNodes_FromUp_EndBecomeFirst() {
		Node start = new Node(project, -4, 5);
		Node end = new Node(project, -4, 4.3);
		bar = new Bar(project, start, end);

		assertEquals(start, bar.getEndingNode());
		assertEquals(end, bar.getStartingNode());
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}
}
