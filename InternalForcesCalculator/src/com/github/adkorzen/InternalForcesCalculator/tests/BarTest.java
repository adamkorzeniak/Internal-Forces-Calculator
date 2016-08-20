package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import com.github.adkorzen.InternalForcesCalculator.loads.BarLoad;

public class BarTest {
	private Project project;
	private Bar bar;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		project = new Project("name");
		bar = new Bar(project, new Node(project, 2.3, 3.5), new Node(project, -1.2, 3));
		System.setOut(new PrintStream(outContent));
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
	public void Contains_NodeOnBar_ReturnsTrue() {
		double x = 1.3;
		double y = 2.5 / 7 + 3;
		Node n = new Node(project, x, y);
		boolean condition = bar.contains(n);

		assertTrue(condition);
	}

	@Test
	public void Contains_NodeOnBarRightEnd_ReturnsTrue() {
		double x = 2.3;
		double y = 3.5;
		Node n = new Node(project, x, y);
		boolean condition = bar.contains(n);

		assertTrue(condition);
	}

	@Test
	public void Contains_NodeOnBarLeftEnd_ReturnsTrue() {
		double x = -1.2;
		double y = 3;
		Node n = new Node(project, x, y);
		boolean condition = bar.contains(n);

		assertTrue(condition);
	}

	@Test
	public void Contains_NodeOffBar_ReturnsFalse() {
		Node n = new Node(project, 3, 7.5);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_NodeOnDirectionLeftFromBar_ReturnsFalse() {
		double x = -2;
		double y = -0.8 / 7 + 3;
		Node n = new Node(project, x, y);

		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_NodeOnDirectionRightFromBar_ReturnsFalse() {
		double x = 4;
		double y = 1.7 / 7 + 3.5;
		Node n = new Node(project, x, y);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarHorizontalNodeAbove_ReturnsFalse() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Node n = new Node(project, 5, 0);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarHorizontalNodeUnder_ReturnsFalse() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Node n = new Node(project, 0, -3);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarHorizontalNodeToLeft_ReturnsFalse() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Node n = new Node(project, 0, -2);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarHorizontalNodeToRight_ReturnsFalse() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Node n = new Node(project, 7, -2);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarHorizontalNodeOnBar_ReturnsTrue() {
		bar = new Bar(project, new Node(project, 1.5, -2), new Node(project, 5, -2));
		Node n = new Node(project, 3, -2);
		boolean condition = bar.contains(n);

		assertTrue(condition);
	}

	@Test
	public void Contains_BarVerticalNodeToRight_ReturnsFalse() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Node n = new Node(project, 5, 0);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarVerticalNodeToLeft_ReturnsFalse() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Node n = new Node(project, -5, 3);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarVerticalNodeAbove_ReturnsFalse() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Node n = new Node(project, -3, 5);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarVerticalNodeUnder_ReturnsFalse() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Node n = new Node(project, -3, -4);
		boolean condition = bar.contains(n);

		assertFalse(condition);
	}

	@Test
	public void Contains_BarVerticalNodeOnBar_ReturnsTrue() {
		bar = new Bar(project, new Node(project, -3, -2), new Node(project, -3, 2));
		Node n = new Node(project, -3, 0);
		boolean condition = bar.contains(n);

		assertTrue(condition);
	}
	
	@Test
	public void Divide_NegativeValue_PrintsErrorMessage() {
		project.addBar(-3, -2, 4, 12);
		project.getBar(1, 6).divide(-0.5);
		
		assertEquals("Invalid value" , outContent.toString().trim());
		
	}
	public void Divide_ValueAbove1_PrintsErrorMessage() {
		project.addBar(-3, -2, 4, 12);
		project.getBar(1, 6).divide(1.5);
		
		assertEquals("Invalid value" , outContent.toString().trim());
	}
	
	@Test
	public void Divide_TooSmallValue_PrintsErrorMessage() {
		project.addBar(-3, -2, 4, 12);
		project.getBar(1, 6).divide(0.008);
		
		assertEquals("Invalid value" , outContent.toString().trim());
	}
	
	@Test
	public void Divide_ValueAboveTooCloseTo1_PrintsErrorMessage() {
		project.addBar(-3, -2, 4, 12);
		project.getBar(1, 6).divide(0.998);
		
		assertEquals("Invalid value" , outContent.toString().trim());
	}

	@Test
	public void Divide_InHalf_DivideAndCreateNode() {
		project.addBar(-3, -2, 4, 12);
		project.getBar(1, 6).divide(0.5);

		assertNotNull(project.getNode(0.5, 5));
		assertEquals(project.getNode(0.5, 5), project.getBar(0, 4).getEndingNode());
		assertEquals(project.getNode(0.5, 5), project.getBar(1, 6).getStartingNode());
	}
	
	@Test
	public void Divide_CloserToStart_DivideAndCreateNode() {
		project.addBar(-3, -2, 2, 13);
		project.getBar(1, 10).divide(0.3);

		assertNotNull(project.getNode(-1.5, 2.5));
		assertEquals(project.getNode(-1.5, 2.5), project.getBar(-2, 1).getEndingNode());
		assertEquals(project.getNode(-1.5, 2.5), project.getBar(1, 10).getStartingNode());
	}
	
	@Test
	public void Divide_CloserToEnd_DivideAndCreateNode() {
		project.addBar(-3, -2, 2, 13);
		project.getBar(1, 10).divide(0.9);

		assertNotNull(project.getNode(1.5, 11.5));
		assertEquals(project.getNode(1.5, 11.5), project.getBar(-2, 1).getEndingNode());
		assertEquals(project.getNode(1.5, 11.5), project.getBar(1.6, 11.8).getStartingNode());
	}
	
	@Test
	public void Divide_HorizontalInHalf_DivideAndCreateNode()  {
		project.addBar(-7, 2, 3, 2);
		project.getBar(0, 2).divide(0.5);

		assertNotNull(project.getNode(-2, 2));
		assertEquals(project.getNode(-2, 2), project.getBar(-3, 2).getEndingNode());
		assertEquals(project.getNode(-2, 2), project.getBar(2, 2).getStartingNode());
	}
	
	@Test
	public void Divide_HorizontalCloserToStart_DivideAndCreateNode() {
		project.addBar(-7, 2, 3, 2);
		project.getBar(0, 2).divide(0.2);

		assertNotNull(project.getNode(-5, 2));
		assertEquals(project.getNode(-5, 2), project.getBar(-6, 2).getEndingNode());
		assertEquals(project.getNode(-5, 2), project.getBar(2, 2).getStartingNode());
	}

	@Test
	public void Divide_HorizontalCloserToEnd_DivideAndCreateNode() {
		project.addBar(-7, 2, 3, 2);
		project.getBar(0, 2).divide(0.8);

		assertNotNull(project.getNode(1, 2));
		assertEquals(project.getNode(1, 2), project.getBar(-6, 2).getEndingNode());
		assertEquals(project.getNode(1, 2), project.getBar(2, 2).getStartingNode());
	}
	
	@Test
	public void Divide_VerticalInHalf_DivideAndCreateNode() {
		project.addBar(1, 2, 1, 8);
		project.getBar(1, 6).divide(0.5);

		assertNotNull(project.getNode(1, 5));
		assertEquals(project.getNode(1, 5), project.getBar(1, 3).getEndingNode());
		assertEquals(project.getNode(1, 5), project.getBar(1, 6).getStartingNode());
	}
	
	@Test
	public void Divide_VerticalCloserToStart_DivideAndCreateNode() {
		project.addBar(1, 2, 1, 8);
		project.getBar(1, 6).divide(0.4);

		assertNotNull(project.getNode(1, 4.4));
		assertEquals(project.getNode(1, 4.4), project.getBar(1, 2).getEndingNode());
		assertEquals(project.getNode(1, 4.4), project.getBar(1, 5).getStartingNode());
	}

	@Test
	public void Divide_VerticalCloserToEnd_DivideAndCreateNode() {
		project.addBar(1, 2, 1, 8);
		project.getBar(1, 6).divide(0.7);

		assertNotNull(project.getNode(1, 6.2));
		assertEquals(project.getNode(1, 6.2), project.getBar(1, 2).getEndingNode());
		assertEquals(project.getNode(1, 6.2), project.getBar(1, 7).getStartingNode());
	}
	
	@Test
	public void Divide_BarWithStartReleased_SetStartReleased() {
		project.addBar(-3, -2, 4, 12);
		project.getBar(1, 6).setStartingNodeReleased(true);
		project.getBar(1, 6).divide(0.5);

		assertTrue(project.getBar(0, 4).isStartingNodeReleased());
		assertFalse(project.getBar(0, 4).isEndingNodeReleased());
		assertFalse(project.getBar(1, 6).isStartingNodeReleased());
		assertFalse(project.getBar(1, 6).isEndingNodeReleased());
	}
	
	@Test
	public void Divide_BarWithEndReleased_SetEndReleased() {
		project.addBar(-3, -2, 4, 12);
		project.getBar(1, 6).setEndingNodeReleased(true);
		project.getBar(1, 6).divide(0.5);

		assertFalse(project.getBar(0, 4).isStartingNodeReleased());
		assertFalse(project.getBar(0, 4).isEndingNodeReleased());
		assertFalse(project.getBar(1, 6).isStartingNodeReleased());
		assertTrue(project.getBar(1, 6).isEndingNodeReleased());
	}
	
	@Test
	public void Divide_BarWithBothEndsReleased_SetBothEndsReleased() {
		project.addBar(-3, -2, 4, 12);
		project.getBar(1, 6).setStartingNodeReleased(true);
		project.getBar(1, 6).setEndingNodeReleased(true);
		project.getBar(1, 6).divide(0.5);

		assertTrue(project.getBar(0, 4).isStartingNodeReleased());
		assertFalse(project.getBar(0, 4).isEndingNodeReleased());
		assertFalse(project.getBar(1, 6).isStartingNodeReleased());
		assertTrue(project.getBar(1, 6).isEndingNodeReleased());
	}
	
	@Test
	public void AddLoad_FirstLoad_LoadListCreatedAndLoadAdded() {
		bar.addLoad(new BarLoad.Builder().y(5).build());
		
		assertNotNull(bar.getLoads());
		assertEquals(5, bar.getLoads().get(0).getY1(), Project.ACCURACY);
	}
	
	@Test
	public void AddLoad_NextLoad_LoadAdded() {
		bar.addLoad(new BarLoad.Builder().y(5).build());
		bar.addLoad(new BarLoad.Builder().x(15).build());
		
		assertNotNull(bar.getLoads());
		assertEquals(5, bar.getLoads().get(0).getY1(), Project.ACCURACY);
		assertEquals(15, bar.getLoads().get(1).getX1(), Project.ACCURACY);
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
	
	@After
	public void tearDown() {
		System.setOut(null);
	}
}
