package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Bar;
import com.github.adkorzen.InternalForcesCalculator.elements.Disk;
import com.github.adkorzen.InternalForcesCalculator.elements.Node;
import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class DiskTest {
	private Project project;
	
	@Before
	public void setUp() {
		project = new Project("test");
	}

//	@Test
//	public void Merge_MergeSmallerWithBigger_MergesAndRemovesOther() {
//		Disk one = new Disk(project);
//		Bar bar1 = new Bar(project, new Node(project, 1, 2), new Node(project, 3, 4));
//		one.addElement(bar1);
//		Bar bar2 = new Bar(project, new Node(project, 3, 4), new Node(project, 3, 8));
//		one.addElement(bar2);
//		
//		Disk two = new Disk(project);
//		Bar bar3 = new Bar(project, new Node(project, 3, 8), new Node(project, 10, 8));
//		two.addElement(bar3);
//		Bar bar4 = new Bar(project, new Node(project, 10, 8), new Node(project, 10, 0));
//		two.addElement(bar4);
//		Bar bar5 = new Bar(project, new Node(project, 10, 0), new Node(project, 0, 0));
//		two.addElement(bar5);
//
//		one.merge(two);
//		
//		assertTrue(one.contains(bar1));
//		assertTrue(one.contains(bar2));
//		assertTrue(one.contains(bar3));
//		assertTrue(one.contains(bar4));
//		assertTrue(one.contains(bar5));
//		
//		assertTrue(project.contains(one));
//		assertFalse(project.contains(two));
//	}
//	
//	@Test
//	public void Merge_MergeBiggerWithSmaller_MergesAndRemovesOther() {
//		Disk one = new Disk(project);
//		Bar bar1 = new Bar(project, new Node(project, 1, 2), new Node(project, 3, 4));
//		one.addElement(bar1);
//		Bar bar2 = new Bar(project, new Node(project, 3, 4), new Node(project, 3, 8));
//		one.addElement(bar2);
//		
//		Disk two = new Disk(project);
//		Bar bar3 = new Bar(project, new Node(project, 3, 8), new Node(project, 10, 8));
//		two.addElement(bar3);
//		Bar bar4 = new Bar(project, new Node(project, 10, 8), new Node(project, 10, 0));
//		two.addElement(bar4);
//		Bar bar5 = new Bar(project, new Node(project, 10, 0), new Node(project, 0, 0));
//		two.addElement(bar5);
//
//		two.merge(one);
//		
//		assertTrue(two.contains(bar1));
//		assertTrue(two.contains(bar2));
//		assertTrue(two.contains(bar3));
//		assertTrue(two.contains(bar4));
//		assertTrue(two.contains(bar5));
//		
//		assertTrue(project.contains(two));
//		assertFalse(project.contains(one));
//	}
}
