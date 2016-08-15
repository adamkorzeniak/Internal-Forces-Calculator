package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source4Frame4 {
	Project p;

	@Before
	public void setUp() {
		p = createSource4Frame4();
	}
	
	@Test
	public void AreNodesStable__SimpleJointedBeam_ReturnsTrue() {
		boolean condition = p.areNodesStable();
		assertTrue(condition);
	}

	@Test
	public void IsStaticallySolvable_Source4Frame4_ReturnsTrue() {
		boolean condition = p.isStaticallySolvable();
		assertTrue(condition);
	}

	@Test
	public void IsGeometricallyStable_Source4Frame4_ReturnsTrue() {
		boolean condition = p.isGeometricallyStable();
		assertTrue(condition);
	}
}
