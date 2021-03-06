package com.github.adkorzen.InternalForcesCalculator.tests.examplesOfStructures;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.tests.ExamplesOfStructures.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class Source7Beam5 {
	Project p;

	@Before
	public void setUp() {
		p = createSource7Beam5();
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

	// Sources claim that this example (and some other) is geometrically stable,
	// although i have not encounter explanation why they are stable
	// Two Disk and Three Disk rule does not prove this example stable,
	// I have no idea why this construction is stable therefore i cannot
	// implement algorithm to evaluate this.
	@Ignore
	@Test
	public void IsGeometricallyStable_PreCreatedConstruction_ReturnsTrue() {
		p.isStaticallySolvable();
		boolean condition = p.isGeometricallyStable();
		assertTrue(condition);
	}
}
