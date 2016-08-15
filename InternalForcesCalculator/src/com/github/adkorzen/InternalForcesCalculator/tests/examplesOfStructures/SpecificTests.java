package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class SpecificTests {
	Project p;

	@Before
	public void setUp() {
		p = new Project("test");
		
	}
	
	@Test
	public void AreNodesStable_ReleaseOnNodeConnectedToOneBar_ReturnFalse() {
		p.addBar(0, 3, 5, 8);
		p.addBar(5, 8, 1, 9);
		p.getBar(2, 5).setStartingNodeReleased(true);

		assertFalse(p.areNodesStable());
	}

	@Test
	public void AreNodesStable_TwoReleasesOnNodeConnectedToTwoBars_ReturnFalse() {
		p.addBar(0, 3, 5, 8);
		p.addBar(5, 8, 1, 9);
		p.getBar(2, 5).setEndingNodeReleased(true);
		p.getBar(2, 8.75).setEndingNodeReleased(true);

		assertFalse(p.areNodesStable());
	}

	@Test
	public void AreNodesStable_ThreeReleasesOnNodeConnectedToThreeBars_ReturnFalse() {
		p.addBar(0, 3, 5, 8);
		p.addBar(5, 8, 1, 9);
		p.addBar(5, 8, 10, 8);
		p.getBar(2, 5).setEndingNodeReleased(true);
		p.getBar(2, 8.75).setEndingNodeReleased(true);
		p.getBar(7, 8).setStartingNodeReleased(true);

		assertFalse(p.areNodesStable());
	}

	@Test
	public void AreNodesStable_FourReleasesOnNodeConnectedToFourBars_ReturnFalse() {
		p.addBar(0, 3, 5, 8);
		p.addBar(5, 8, 1, 9);
		p.addBar(5, 8, 10, 8);
		p.addBar(5, 8, 5, 10);
		p.getBar(2, 5).setEndingNodeReleased(true);
		p.getBar(2, 8.75).setEndingNodeReleased(true);
		p.getBar(7, 8).setStartingNodeReleased(true);
		p.getBar(5, 9).setStartingNodeReleased(true);

		assertFalse(p.areNodesStable());
	}

	@Test
	public void AreNodesStable_NoReleaseOnNodeConnectedToOneBar_ReturnTrue() {
		p.addBar(0, 3, 5, 8);
		p.addBar(5, 8, 1, 9);
		assertTrue(p.areNodesStable());
	}

	@Test
	public void AreNodesStable_OneReleaseOnNodeConnectedToTwoBars_ReturnTrue() {
		p.addBar(0, 3, 5, 8);
		p.addBar(5, 8, 1, 9);
		p.getBar(2, 5).setEndingNodeReleased(true);

		assertTrue(p.areNodesStable());
	}

	@Test
	public void AreNodesStable_TwoReleaseOnNodeConnectedToThreeBars_ReturnTrue() {
		p.addBar(0, 3, 5, 8);
		p.addBar(5, 8, 1, 9);
		p.addBar(5, 8, 10, 8);
		p.getBar(2, 5).setEndingNodeReleased(true);
		p.getBar(7, 8).setStartingNodeReleased(true);

		assertTrue(p.areNodesStable());
	}

	@Test
	public void AreNodesStable_FourReleaseOnNodeConnectedToFourBars_ReturnTrue() {
		p.addBar(0, 3, 5, 8);
		p.addBar(5, 8, 1, 9);
		p.addBar(5, 8, 10, 8);
		p.addBar(5, 8, 5, 10);
		p.getBar(2, 8.75).setEndingNodeReleased(true);
		p.getBar(7, 8).setStartingNodeReleased(true);
		p.getBar(5, 9).setStartingNodeReleased(true);

		assertTrue(p.areNodesStable());
	}
}
