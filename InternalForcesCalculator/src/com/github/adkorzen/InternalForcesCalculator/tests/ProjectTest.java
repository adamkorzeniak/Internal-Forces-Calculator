package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Bar;
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
	public void GetName_ProjectWithName_NameReturned() {
		String expected = "test";
		String actual = project.getName();

		assertEquals(expected, actual);

	}

	@Test
	public void addNode_SimpleValues_NodeCreatedAndAdded() {
		project.addNode(11.3, 2.22);

		Node node = project.getNodesList().get(0);

		double expectedX = 11.3;
		double actualX = node.getX();
		double expectedY = 2.22;
		double actualY = node.getY();

		assertEquals(expectedX, actualX, 0.0001);
		assertEquals(expectedY, actualY, 0.0001);
	}

	@Test
	public void removeNode_TwoNodesExists_OneNodeRemoved() {
		project.addNode(11.3, 2.22);
		project.addNode(22.3, -1.23);

		project.removeNode(project.getNodesList().get(0));

		Node node = project.getNodesList().get(0);

		double expectedX = 22.3;
		double actualX = node.getX();
		double expectedY = -1.23;
		double actualY = node.getY();

		assertEquals(expectedX, actualX, 0.0001);
		assertEquals(expectedY, actualY, 0.0001);
		assertEquals(1, project.getNodesList().size());
	}

	@Test
	public void addSupport_NoSupportsExisting_SuportAdded() {
		project.addNode(11.3, 2.22);
		Node node = project.getNodesList().get(0);
		node.setSupport(Support.FIXED);

		Support expected = Support.FIXED;
		Support actual = project.getSupportsList().get(0).getSupport();

		assertEquals(expected, actual);
		assertEquals(1, project.getSupportsList().size());
	}

	@Test
	public void addSupport_SupportAlreadyAdded_NothingChanges() {
		project.addNode(11.3, 2.22);
		Node node = project.getNodesList().get(0);
		node.setSupport(Support.FIXED);
		node.setSupport(Support.FIXED);

		Support expected = Support.FIXED;
		Support actual = project.getSupportsList().get(0).getSupport();

		assertEquals(expected, actual);
		assertEquals(1, project.getSupportsList().size());
	}

	@Test
	public void removeSupport_NoSupportsExisting_NothingChanges() {
		project.addNode(11.3, 2.22);
		Node node = project.getNodesList().get(0);
		project.removeSupport(node);

		assertTrue(project.getSupportsList().isEmpty());
	}

	@Test
	public void removeSupport_DifferentSupportExist_NothingChanges() {
		project.addNode(11.3, 2.22);
		project.addNode(2, 3);

		Node node1 = project.getNodesList().get(0);
		Node node2 = project.getNodesList().get(1);

		project.getNodesList().get(0).setSupport(Support.HINGED);
		project.removeSupport(node2);

		assertTrue(project.containsSupport(node1));
		assertEquals(1, project.getSupportsList().size());
	}

	@Test
	public void removeSupport_SupportExist_SupportRemoved() {
		project.addNode(11.3, 2.22);
		project.addNode(2, 3);

		Node node1 = project.getNodesList().get(0);

		project.getNodesList().get(0).setSupport(Support.HINGED);
		project.removeSupport(node1);

		assertTrue(project.getSupportsList().isEmpty());
	}

	@Test
	public void isStaticallySolvable_SimpleSupportedBeam_BeamIsSolvable() {
		Project project = ExamplesOfStructures.createSimpleSupportedBeam();
		boolean condition = project.isStaticallySolvable();

		assertTrue(condition);
	}

	@Test
	public void isStaticallySolvable_SimpleJointedBeam_BeamIsSolvable() {
		Project project = ExamplesOfStructures.createSimpleJointedBeam();
		boolean condition = project.isStaticallySolvable();

		assertTrue(condition);
	}

}
