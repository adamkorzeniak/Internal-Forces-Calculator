package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source4Beam2 {
	Project p;

	@Before
	public void setUp() {
		p = createSource4Beam2();
	}
	
	@Test
	public void AreNodesStable_PreCreatedConstruction_ReturnsTrue() {
		boolean condition = p.areNodesStable();
		assertTrue(condition);
	}

	@Test
	public void IsStaticallySolvable_PreCreatedConstruction_ReturnsTrue() {
		boolean condition = p.isStaticallySolvable();
		assertTrue(condition);
	}

	@Test
	public void IsGeometricallyStable_PreCreatedConstruction_ReturnsTrue() {
		p.isStaticallySolvable();
		boolean condition = p.isGeometricallyStable();
		assertTrue(condition);
	}
	
	@Test
	public void CalculateReactions_Reactions_CorrectValues() {
		p.isStaticallySolvable();
		p.isGeometricallyStable();
		p.calculateReactions();
		
		double H1 = p.getNode(0, 0).getHorizontalReaction();
		double M1 = p.getNode(0, 0).getMomentReaction();
		double V2 = p.getNode(6, 0).getVerticalReaction();
		double V3 = p.getNode(8, 0).getVerticalReaction();
		double V4 = p.getNode(14, 0).getVerticalReaction();
		
		assertEquals(0, H1, Project.ACCURACY);
		assertEquals(16, M1, Project.ACCURACY);
		assertEquals(16, V2, Project.ACCURACY);
		assertEquals(2, V3, Project.ACCURACY);
		assertEquals(-1, V4, Project.ACCURACY);
	}
}
