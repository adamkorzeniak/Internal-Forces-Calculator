package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source7Frame8 {
	Project p;

	@Before
	public void setUp() {
		p = createSource7Frame8();
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
		
		double V1 = p.getNode(0, 4).getReactions().getY();
		double H2 = p.getNode(8, 0).getReactions().getX();
		double V2 = p.getNode(8, 0).getReactions().getY();
		
		assertEquals(75, V1, Project.ACCURACY);
		assertEquals(50, H2, Project.ACCURACY);
		assertEquals(-10, V2, Project.ACCURACY);
	}
}
