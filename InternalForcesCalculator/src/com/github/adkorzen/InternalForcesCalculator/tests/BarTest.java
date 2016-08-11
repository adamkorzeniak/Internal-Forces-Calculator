package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Bar;
import com.github.adkorzen.InternalForcesCalculator.elements.Node;

public class BarTest {

	@Test
	public void BarConstructor_ContainingNoPreCreatedNodes_CreatesTwoNodesAndBar() {
		Bar bar = new Bar(-2, 2, 4, 3);
		int actualStartX = bar.getStartingNode().getX();
		int actualStartY = bar.getStartingNode().getY();
		int actualEndX = bar.getEndingNode().getX();
		int actualEndY = bar.getEndingNode().getY();
		
		assertEquals(-2, actualStartX);
		assertEquals(2, actualStartY);
		assertEquals(4, actualEndX);
		assertEquals(3, actualEndY);
	}
	
	@Test
	public void BarConstructor_ContainingOnePreCreatedNode_CreatesOneNodeAndBar() {
		Bar bar = new Bar(-4, 9, new Node(5, 7));
		int actualStartX = bar.getStartingNode().getX();
		int actualStartY = bar.getStartingNode().getY();
		int actualEndX = bar.getEndingNode().getX();
		int actualEndY = bar.getEndingNode().getY();
		
		assertEquals(-4, actualStartX);
		assertEquals(9, actualStartY);
		assertEquals(5, actualEndX);
		assertEquals(7, actualEndY);
	}
	
	@Test
	public void BarConstructor_ContainingTwoPreCreatedNodes_CreatesBar() {
		Bar bar = new Bar(new Node(2, 3), new Node(-2, -3));
		int actualStartX = bar.getStartingNode().getX();
		int actualStartY = bar.getStartingNode().getY();
		int actualEndX = bar.getEndingNode().getX();
		int actualEndY = bar.getEndingNode().getY();
		
		assertEquals(2, actualStartX);
		assertEquals(3, actualStartY);
		assertEquals(-2, actualEndX);
		assertEquals(-3, actualEndY);
	}
	
	@Test
	public void SetAndGetStartingNodeJoint_SetNodeJoint_NodeJoint() {
		Bar bar = new Bar(-2, 2, 4, 3);
		bar.setStartingNodeJoint(true);
		boolean expected = true;
		boolean actual = bar.isStartingNodeJoint();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void SetAndGetEndingNodeJoint_SetNodeJoint_NodeJoint() {
		Bar bar = new Bar(-2, 2, 4, 3);
		bar.setEndingNodeJoint(true);
		boolean expected = true;
		boolean actual = bar.isEndingNodeJoint();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void SetAndGetStartingNodeJoint_SetNodeNotJoint_NodeNotJoint() {
		Bar bar = new Bar(-2, 2, 4, 3);
		bar.setStartingNodeJoint(true);
		bar.setStartingNodeJoint(false);
		boolean expected = false;
		boolean actual = bar.isStartingNodeJoint();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void SetAndGetEndingNodeJoint_SetNodeNotJoint_NodeNotJoint() {
		Bar bar = new Bar(-2, 2, 4, 3);
		bar.setEndingNodeJoint(true);
		bar.setEndingNodeJoint(false);
		boolean expected = false;
		boolean actual = bar.isEndingNodeJoint();
		
		assertEquals(expected, actual);
	}

}
