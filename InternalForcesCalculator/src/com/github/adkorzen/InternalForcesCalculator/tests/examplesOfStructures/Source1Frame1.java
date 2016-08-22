package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.createSource1Frame1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source1Frame1 {
	Project p;

	@Before
	public void setUp() {
		p = createSource1Frame1();
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
		
		double V1 = p.getNode(2, 2).getReactions().getY();
		double V2 = p.getNode(7, 3).getReactions().getY();
		double H3 = p.getNode(9, 5).getReactions().getX();
		
		assertEquals(0.6, V1, Project.ACCURACY);
		assertEquals(2.4, V2, Project.ACCURACY);
		assertEquals(-6, H3, Project.ACCURACY);
	}
}
