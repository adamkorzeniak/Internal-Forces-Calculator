package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source7Frame10 {
	Project p;

	@Before
	public void setUp() {
		p = createSource7Frame10();
	}
	
	@Test
	public void AreNodesStable__PreCreatedConstruction_ReturnsTrue() {
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
		double V1 = p.getNode(0, 0).getVerticalReaction();
		double M1 = p.getNode(0, 0).getMomentReaction();
		double V2 = p.getNode(6, 2).getVerticalReaction();
		
		assertEquals(163, H1, Project.ACCURACY);
		assertEquals(-52.0 / 3, V1, Project.ACCURACY);
		assertEquals(235, M1, Project.ACCURACY);
		assertEquals(-80.0 / 3, V2, Project.ACCURACY);
	}
}
