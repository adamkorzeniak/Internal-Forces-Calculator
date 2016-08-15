package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source7Frame7 {
	Project p;

	@Before
	public void setUp() {
		p = createSource7Frame7();
	}
	
	@Test
	public void AreNodesStable__SimpleJointedBeam_ReturnsTrue() {
		boolean condition = p.areNodesStable();
		assertTrue(condition);
	}

	@Test
	public void IsStaticallySolvable_Source7Frame7_ReturnsTrue() {
		boolean condition = p.isStaticallySolvable();
		assertTrue(condition);
	}

	@Test
	public void IsGeometricallyStable_Source7Frame7_ReturnsTrue() {
		boolean condition = p.isGeometricallyStable();
		assertTrue(condition);
	}
}
