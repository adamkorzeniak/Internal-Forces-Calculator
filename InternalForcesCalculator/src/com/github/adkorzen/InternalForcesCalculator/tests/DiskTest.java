package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Bar;
import com.github.adkorzen.InternalForcesCalculator.elements.Disk;
import com.github.adkorzen.InternalForcesCalculator.elements.Line;
import com.github.adkorzen.InternalForcesCalculator.elements.Node;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class DiskTest {
	Project project;

	@Before
	public void setUp() {
		project = new Project("test");
	}

	@Test
	public void Constructor_Empty_EmptyDisk() {
		Disk d = new Disk();
		assertTrue(d.getBars().isEmpty());
	}

	@Test
	public void Constructor_BarProvided_DiskContainingBar() {
		Bar bar = new Bar(project, new Node(project, 0, 3), new Node(project, 3, 0));
		Disk d = new Disk(bar);
		assertEquals(1, d.getBars().size());
		assertEquals(bar, d.getBars().get(0));
	}

	@Test
	public void AddBar_BarProvided_DiskContainingBar() {
		Bar bar = new Bar(project, new Node(project, 0, 3), new Node(project, 3, 0));
		Bar bar2 = new Bar(project, new Node(project, 2, 3), new Node(project, 3, 2));
		Disk d = new Disk(bar);
		d.addBar(bar2);
		assertEquals(2, d.getBars().size());
		assertEquals(bar2, d.getBars().get(1));
	}

	@Test
	public void AddBonds_BondingWithOtherDisk_BondsReturned() {
		Disk d1 = new Disk();
		Disk d2 = new Disk();

		List<Line> bonds = new ArrayList<Line>();
		bonds.add(new Line(2, 5, 6, 7));
		bonds.add(new Line(3, 6, 1, 2));
		bonds.add(new Line(2, 7, 6, 3));
		bonds.add(new Line(9, 0, 0, 4));
		bonds.add(new Line(3, 3, 1, 5));

		for (Line b : bonds) {
			d1.addBond(d2, b);
			d2.addBond(d1, b);
		}

		assertEquals(bonds, d1.getBonds(d2));
		assertEquals(bonds, d2.getBonds(d1));
	}
	
	@Test
	public void Contains_NodeInside_ReturnTrue() {
	Bar bar = new Bar(project, new Node(project, 0, 3), new Node(project, 3, 0));
	Bar bar2 = new Bar(project, new Node(project, 3, 0), new Node(project, 3, 2));
	Disk d = new Disk(bar);
	d.addBar(bar2);
	
	assertTrue(d.contains(new Node(project, 2, 1)));
	assertTrue(d.contains(new Node(project, 3, 1)));
	}
	
	@Test
	public void Contains_NodeOnEnd_ReturnTrue() {
	Bar bar = new Bar(project, new Node(project, 0, 3), new Node(project, 3, 0));
	Bar bar2 = new Bar(project, new Node(project, 3, 0), new Node(project, 3, 2));
	Disk d = new Disk(bar);
	d.addBar(bar2);
	
	assertTrue(d.contains(new Node(project, 0, 3)));
	assertTrue(d.contains(new Node(project, 3, 0)));
	assertTrue(d.contains(new Node(project, 3, 2)));
	}
	
	@Test
	public void Contains_NodeOutside_ReturnTrue() {
	Bar bar = new Bar(project, new Node(project, 0, 3), new Node(project, 3, 0));
	Bar bar2 = new Bar(project, new Node(project, 3, 0), new Node(project, 3, 2));
	Disk d = new Disk(bar);
	d.addBar(bar2);
	
	assertFalse(d.contains(new Node(project, 2, 3)));
	assertFalse(d.contains(new Node(project, 3, 3)));
	assertFalse(d.contains(new Node(project, 1, 1)));
	}
}
