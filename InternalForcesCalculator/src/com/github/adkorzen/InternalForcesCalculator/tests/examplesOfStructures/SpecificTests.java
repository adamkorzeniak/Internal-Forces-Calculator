package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;
import com.github.adkorzen.InternalForcesCalculator.elements.Support;

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

	@Test
	public void IsStaticallySolvable_TooManySupportBonds_ReturnsFalse() {
		p.addBar(3, 7, 8, 9);
		p.getNode(3, 7).setSupport(Support.HINGED);
		p.getNode(8, 9).setSupport(Support.HINGED);

		assertFalse(p.isStaticallySolvable());
	}

	@Test
	public void IsStaticallySolvable_TooFewSupportBonds_ReturnsFalse() {
		p.addBar(3, 7, 8, 9);
		p.getNode(3, 7).setSupport(Support.ROLLER);
		p.getNode(8, 9).setSupport(Support.ROLLER);

		assertFalse(p.isStaticallySolvable());
	}

	@Test
	public void IsStaticallySolvable_TooManySupportBondsWithRelease_ReturnsFalse() {
		p.addBar(3, 7, 8, 9);
		p.addBar(8, 9, 0, 0);
		p.getBar(5.5, 8).setEndingNodeReleased(true);
		p.getNode(3, 7).setSupport(Support.FIXED);
		p.getNode(8, 9).setSupport(Support.HINGED);

		assertFalse(p.isStaticallySolvable());
	}

	@Test
	public void IsStaticallySolvable_TooFewSupportBondsWithRealease_ReturnsFalse() {
		p.addBar(3, 7, 8, 9);
		p.addBar(8, 9, 0, 0);
		p.getBar(5.5, 8).setEndingNodeReleased(true);
		p.getNode(3, 7).setSupport(Support.HINGED);
		p.getNode(8, 9).setSupport(Support.ROLLER);

		assertFalse(p.isStaticallySolvable());
	}

	@Test
	public void IsStaticallySolvable_ClosedWithReleaseFrame_ReturnsFalse() {
		p.addBar(0, 5, 5, 10);
		p.addBar(5, 10, 5, 0);
		p.addBar(0, 5, 5, 0);
		p.getNode(0, 5).setSupport(Support.HINGED);
		p.getNode(5, 0).setSupport(Support.ROLLER);
		p.getBar(3, 8).setEndingNodeReleased(true);

		assertFalse(p.isStaticallySolvable());
	}

	@Test
	public void IsGeometricallyStable_SupportsVerticallyCrossInTheSamePoint_ReturnsFalse() {
		p.addBar(0, 5, 5, 10);
		p.addBar(5, 10, 0, 10);
		p.getNode(0, 5).setSupport(Support.HINGED);
		p.getNode(0, 10).setSupport(Support.ROLLER);

		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}

	@Test
	public void IsGeometricallyStable_SupportsHorizontallyCrossInTheSamePoint_ReturnsFalse() {
		p.addBar(5, 0, 5, 10);
		p.addBar(5, 10, 10, 0);
		p.getNode(5, 0).setSupport(Support.HINGED);
		p.getNode(10, 0).setSupport(Support.ROLLER, 90);

		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}

	@Test
	public void IsGeometricallyStable_BondsCrossTheSamePoint_ReturnsFalse() {
		p.addBar(0, 0, 5, 0);
		double y = 7 * Math.sqrt(3);
		p.addBar(5, 0, 7, y);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(7, y).setSupport(Support.ROLLER, 150);

		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}

	@Test
	public void IsGeometricallyStable_BondsVerticallyParallel_ReturnsFalse() {
		p.addBar(0, 0, 5, 0);
		p.addBar(5, 0, 10, 2);
		p.getNode(0, 0).setSupport(Support.ROLLER);
		p.getNode(5, 0).setSupport(Support.ROLLER);
		p.getNode(10, 2).setSupport(Support.ROLLER);

		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}

	@Test
	public void IsGeometricallyStable_BondsHorizontallyParallel_ReturnsFalse() {
		p.addBar(0, 0, 5, 1);
		p.addBar(5, 1, 10, 2);
		p.getNode(0, 0).setSupport(Support.ROLLER, 90);
		p.getNode(5, 1).setSupport(Support.ROLLER, 90);
		p.getNode(10, 2).setSupport(Support.ROLLER, 90);

		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}

	@Test
	public void IsGeometricallyStable_BondsParallel_ReturnsFalse() {
		p.addBar(0, 0, 5, 1);
		p.addBar(5, 1, 10, 2);
		p.getNode(0, 0).setSupport(Support.ROLLER, 30);
		p.getNode(5, 1).setSupport(Support.ROLLER, 30);
		p.getNode(10, 2).setSupport(Support.ROLLER, 30);

		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}
	
	@Test
	public void IsGeometricallyStable_BondsCrossInDifferentPoits_ReturnsTrue() {
		p.addBar(0, 0, 5, 1);
		p.addBar(5, 1, 10, 2);
		p.getNode(0, 0).setSupport(Support.ROLLER, 30);
		p.getNode(5, 1).setSupport(Support.ROLLER, 50);
		p.getNode(10, 2).setSupport(Support.ROLLER, 30);

		assertTrue(p.isStaticallySolvable());
		assertTrue(p.isGeometricallyStable());
	}

	@Test
	public void IsGeometricallyStable_TwoBondsTheSame_ReturnsFalse() {
		p.addBar(0, 0, 5, 0);
		p.addBar(5, 0, 10, 2);
		p.getNode(0, 0).setSupport(Support.ROLLER, 90);
		p.getNode(5, 0).setSupport(Support.ROLLER, 90);
		p.getNode(10, 2).setSupport(Support.ROLLER);

		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}

	@Test
	public void IsGeometricallyStable_ThreeDisksBondsOnSameLine_ReturnsFalse() {
		p.addBar(0, 0, 5, 0);
		p.addBar(5, 0, 10, 0);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(10, 0).setSupport(Support.HINGED);
		p.getBar(2, 0).setEndingNodeReleased(true);
		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}
	
	@Test
	public void IsGeometricallyStable_ThreeDisksTwoBondsOnLineOneParallelInInfinity_ReturnsFalse() {
		p.addBar(0, 0, 5, 0);
		p.addBar(5, 0, 5, 5);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(5, 5).setSupport(Support.ROLLER, 90);
		p.addNode(5, 4);
		p.getNode(5, 4).setSupport(Support.ROLLER, 90);
		p.getBar(2, 0).setEndingNodeReleased(true);
		
		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}
	
	@Test
	public void IsGeometricallyStable_ThreeDisksTwoBondsOnLineOneNotParallelInInfinity_ReturnsTrue() {
		p.addBar(0, 0, 5, 2);
		p.addBar(5, 2, 5, 5);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(5, 5).setSupport(Support.ROLLER, 90);
		p.addNode(5, 4);
		p.getNode(5, 4).setSupport(Support.ROLLER, 90);
		p.getBar(5, 3).setStartingNodeReleased(true);
		
		assertTrue(p.isStaticallySolvable());
		assertTrue(p.isGeometricallyStable());
	}
	
	@Test
	public void IsGeometricallyStable_ThreeDisksTwoBondsParallelInInfinity_ReturnsTrue() {
		p.addBar(0, 0, 0, 2);
		p.addBar(0, 0, 5, 0);
		p.addBar(5, 0, 5, 5);
		p.getNode(0, 0).setSupport(Support.ROLLER, 60);
		p.getNode(0, 2).setSupport(Support.ROLLER, 60);
		p.addNode(5, 4);
		p.getNode(5, 5).setSupport(Support.ROLLER, 60);
		p.getNode(5, 4).setSupport(Support.ROLLER, 60);
		p.getBar(5, 3).setStartingNodeReleased(true);
		
		assertTrue(p.isStaticallySolvable());
		assertFalse(p.isGeometricallyStable());
	}
}
