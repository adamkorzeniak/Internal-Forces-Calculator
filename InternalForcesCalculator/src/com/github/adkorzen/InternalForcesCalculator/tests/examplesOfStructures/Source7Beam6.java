package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source7Beam6 {
	Project p;

	@Before
	public void setUp() {
		p = createSource7Beam6();
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
		
		double V1 = p.getNode(0, 0).getVerticalReaction();
		double V2 = p.getNode(2, 0).getVerticalReaction();
		double V3 = p.getNode(11, 0).getVerticalReaction();
		double M3 = p.getNode(11, 0).getMomentReaction();
		
		assertEquals(-7.5, V1, Project.ACCURACY);
		assertEquals(37.5, V2, Project.ACCURACY);
		assertEquals(40, V3, Project.ACCURACY);
		assertEquals(60, M3, Project.ACCURACY);
	}
}
