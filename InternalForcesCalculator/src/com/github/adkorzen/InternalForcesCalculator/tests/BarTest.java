package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Bar;
import com.github.adkorzen.InternalForcesCalculator.elements.Node;
import com.github.adkorzen.InternalForcesCalculator.elements.Point;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class BarTest {
	private Project project;
	private Bar bar;

	@Before
	public void setUp() {
		project = new Project("name");
		bar = new Bar(project, new Node(project, 2.3, 3.5), new Node(project, -1.2, 3));
	}

	@Test
	public void BarConstructor_NormalValues_CreatesBar() {
		double actualStartX = bar.getEndingNode().getX();
		double actualStartY = bar.getEndingNode().getY();
		double actualEndX = bar.getStartingNode().getX();
		double actualEndY = bar.getStartingNode().getY();

		assertEquals(2.3, actualStartX, Project.ACCURACY);
		assertEquals(3.5, actualStartY, Project.ACCURACY);
		assertEquals(-1.2, actualEndX, Project.ACCURACY);
		assertEquals(3, actualEndY, Project.ACCURACY);
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
	public void Contains_BarHorizontalPointAbove_ReturnsFalse() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Point p = new Point(5, 0);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}
	
	@Test
	public void Contains_BarHorizontalPointUnder_ReturnsFalse() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Point p = new Point(0, -3);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}
	
	@Test
	public void Contains_BarHorizontalPointToLeft_ReturnsFalse() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Point p = new Point(0, -2);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}
	
	@Test
	public void Contains_BarHorizontalPointToRight_ReturnsFalse() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Point p = new Point(7, -2);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}
	
	@Test
	public void Contains_BarHorizontalPointOnBar_ReturnsTrue() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Point p = new Point(3, -2);
		boolean condition = bar.contains(p);

		assertTrue(condition);
	}
	
	@Test
	public void Contains_BarVerticalPointToRight_ReturnsFalse() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Point p = new Point(5, 0);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}
	
	@Test
	public void Contains_BarVerticalPointToLeft_ReturnsFalse() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Point p = new Point(-5, 3);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}
	
	@Test
	public void Contains_BarVerticalPointAbove_ReturnsFalse() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Point p = new Point(-3, 5);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}
	
	@Test
	public void Contains_BarVerticalPointUnder_ReturnsFalse() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Point p = new Point(-3, -4);
		boolean condition = bar.contains(p);

		assertFalse(condition);
	}
	
	@Test
	public void Contains_BarVerticalPointOnBar_ReturnsTrue() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Point p = new Point(-3, 0);
		boolean condition = bar.contains(p);

		assertTrue(condition);
	}

	@Test
	public void SetStartingNodeReleased_NodeNotReleased_NodeReleased() {
		bar.setStartingNodeReleased(true);
		boolean condition = bar.isStartingNodeReleased();

		assertTrue(condition);
	}

	@Test
	public void SetEndingNodeReleased_NodeNotReleased_NodeReleased() {
		bar.setEndingNodeReleased(true);
		boolean condition = bar.isEndingNodeReleased();

		assertTrue(condition);
	}

	@Test
	public void SetStartingNodeReleased_NodeReleased_NodeNotReleased() {
		bar.setStartingNodeReleased(true);
		bar.setStartingNodeReleased(false);
		boolean condition = bar.isStartingNodeReleased();

		assertFalse(condition);
	}

	@Test
	public void SetEndingNodeReleased_NodeReleased_NodeNotReleased() {
		bar.setEndingNodeReleased(true);
		bar.setEndingNodeReleased(false);
		boolean condition = bar.isEndingNodeReleased();

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
}
