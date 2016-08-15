package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Node;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;
import com.github.adkorzen.InternalForcesCalculator.elements.Support;

public class ProjectTest {
	Project project;

	@Before
	public void setUp() {
		project = new Project("test");
	}

	@Test
	public void AddNode_SimpleValues_NodeCreatedAndAdded() {
		project.addNode(11.3, 2.22);

		Node node = project.getNode(11.3, 2.22);

		double expectedX = 11.3;
		double actualX = node.getX();
		double expectedY = 2.22;
		double actualY = node.getY();

		assertEquals(expectedX, actualX, 0.0001);
		assertEquals(expectedY, actualY, 0.0001);
	}

	@Test
	public void RemoveNode_TwoNodesExists_OneNodeRemoved() {
		project.addNode(11.3, 2.22);
		project.addNode(22.3, -1.23);

		project.removeNode(project.getNode(11.3, 2.22));

		Node node = project.getNode(22.3, -1.23);

		double expectedX = 22.3;
		double actualX = node.getX();
		double expectedY = -1.23;
		double actualY = node.getY();

		assertEquals(expectedX, actualX, 0.0001);
		assertEquals(expectedY, actualY, 0.0001);
		assertNull(project.getNode(11.3, 2.22));
	}
	
	@Test
	public void RemoveNode_NodeIsStartOfBar_NodeNotRemoved() {
		project.addBar(3, 6, 8, 9);
		
		project.removeNode(project.getNode(3, 6));

		assertNotNull(project.getNode(3, 6));
		assertNotNull(project.getNode(8, 9));
	}
	
	@Test
	public void RemoveNode_NodeIsEndOfBar_NodeNotRemoved() {
		project.addBar(3, 6, 8, 9);
		
		project.removeNode(project.getNode(8, 9));

		assertNotNull(project.getNode(3, 6));
		assertNotNull(project.getNode(8, 9));
	}
	
	@Test
	public void RemoveNode_StartLooseNode_NodeRemoved() {
		project.addBar(3, 6, 8, 9);
		project.removeBar(project.getBar(4, 6.6));
		
		assertNull(project.getBar(4, 6.6));
		
		project.removeNode(project.getNode(3, 6));
		
		assertNull(project.getNode(3, 6));
		assertNotNull(project.getNode(8, 9));
	}
	
	@Test
	public void RemoveNode_EndLooseNode_NodeRemoved() {
		project.addBar(3, 6, 8, 9);
		project.removeBar(project.getBar(4, 6.6));
		
		assertNull(project.getBar(4, 6.6));
		
		project.removeNode(project.getNode(8, 9));
		
		assertNotNull(project.getNode(3, 6));
		assertNull(project.getNode(8, 9));
	}
	
	@Test
	public void RemoveNode_BothLooseNodes_NodesRemoved() {
		project.addBar(3, 6, 8, 9);
		project.removeBar(project.getBar(4, 6.6));
		
		assertNull(project.getBar(4, 6.6));
		
		project.removeNode(project.getNode(3, 6));
		project.removeNode(project.getNode(8, 9));
		
		assertNull(project.getNode(3, 6));
		assertNull(project.getNode(8, 9));
	}

	@Test
	public void AddSupport_NoSupportsExisting_SuportAdded() {
		project.addNode(11.3, 2.22);
		Node node = project.getNode(11.3, 2.22);
		node.setSupport(Support.FIXED);

		Support expected = Support.FIXED;
		Support actual = project.getSupport(11.3, 2.22).getSupport();

		assertEquals(expected, actual);
	}

	@Test
	public void AddSupport_SupportAlreadyAdded_NothingChanges() {
		project.addNode(11.3, 2.22);
		Node node = project.getNode(11.3, 2.22);
		node.setSupport(Support.FIXED);
		node.setSupport(Support.FIXED);

		Support expected = Support.FIXED;
		Support actual = project.getSupport(11.3, 2.22).getSupport();

		assertEquals(expected, actual);
	}

	@Test
	public void RemoveSupport_NoSupportsExisting_NothingChanges() {
		project.addNode(11.3, 2.22);
		Node node = project.getNode(11.3, 2.22);
		project.removeSupport(node);

		assertNull(project.getSupport(11.3, 2.22));
	}

	@Test
	public void RemoveSupport_DifferentSupportExist_NothingChanges() {
		project.addNode(11.3, 2.22);
		project.addNode(2, 3);

		Node node1 = project.getNode(11.3, 2.22);
		Node node2 = project.getNode(2, 3);

		project.getNode(11.3, 2.22).setSupport(Support.HINGED);
		project.removeSupport(node2);

		assertEquals(node1, project.getSupport(11.3, 2.22));
		assertNull(project.getSupport(2, 3));
	}

	@Test
	public void RemoveSupport_SupportExist_SupportRemoved() {
		project.addNode(11.3, 2.22);
		project.addNode(2, 3);

		Node node1 = project.getNode(11.3, 2.22);;

		project.getNode(11.3, 2.22).setSupport(Support.HINGED);
		project.removeSupport(node1);

		assertNull(project.getSupport(11.3, 2.22));
	}
	
	@Test
	public void AddBar_FirstBar_AddBar() {
		project.addBar(3, 0, 5, 4);
		
		assertNotNull(project.getNode(3, 0));
		assertNotNull(project.getNode(5, 4));
		assertNotNull(project.getBar(4, 2));
	}
	
	@Test
	public void AddBar_SecondBarConnected_AddBar() {
		project.addBar(3, 0, 5, 4);
		project.addBar(5, 4, 0, 3);
		
		assertNotNull(project.getNode(3, 0));
		assertNotNull(project.getNode(0, 3));
		assertNotNull(project.getNode(5, 4));
		assertNotNull(project.getBar(4, 2));
		assertNotNull(project.getBar(2.5, 3.5));
	}
	
	public void RemoveBar_SecondBarRemoved_BarRemoved() {
		project.addBar(3, 0, 5, 4);
		project.addBar(5, 4, 0, 3);
		project.removeBar(project.getBar(3.5, 1));
		
		assertNotNull(project.getNode(3, 0));
		assertNotNull(project.getNode(0, 3));
		assertNotNull(project.getNode(5, 4));
		assertNull(project.getBar(4, 2));
		assertNotNull(project.getBar(2.5, 3.5));
	}

	@Test
	public void ToString_ProjectWithName_NameReturned() {
		String expected = "test";
		String actual = project.toString();

		assertEquals(expected, actual);
	}
}
