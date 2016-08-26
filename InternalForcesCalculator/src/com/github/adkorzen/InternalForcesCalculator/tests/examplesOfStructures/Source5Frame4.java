package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source5Frame4 {
	Project p;

	@Before
	public void setUp() {
		p = createSource5Frame4();
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
		double V1 = p.getNode(0, 0).getVerticalReaction();
		double V2 = p.getNode(8, 1).getVerticalReaction();
		
		assertEquals(-8, H1, Project.ACCURACY);
		assertEquals(39.0 / 8, V1, Project.ACCURACY);
		assertEquals(81.0 / 8, V2, Project.ACCURACY);
	}
}
