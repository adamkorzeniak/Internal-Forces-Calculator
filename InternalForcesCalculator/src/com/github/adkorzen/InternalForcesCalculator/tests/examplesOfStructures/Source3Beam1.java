package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source3Beam1 {
	Project p;

	@Before
	public void setUp() {
		p = createSource3Beam1();
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
		
		double H1 = p.getNode(2, 3).getHorizontalReaction();
		double V1 = p.getNode(2, 3).getVerticalReaction();
		double M1 = p.getNode(2, 3).getMomentReaction();
		double V2 = p.getNode(8, 3).getVerticalReaction();
		
		assertEquals(0, H1, Project.ACCURACY);
		assertEquals(21.83333333, V1, Project.ACCURACY);
		assertEquals(-43, M1, Project.ACCURACY);
		assertEquals(24.66666667, V2, Project.ACCURACY);
	}
}
