package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Support;

public class SupportTest {

	@Test
	public void CreateSupport_HingedSupport_HingedBlockadesSets() {
		Support s = Support.HINGED;
		boolean expectedX = true;
		boolean actualX = s.isXMoveBlocked();
		
		boolean expectedY = true;
		boolean actualY = s.isYMoveBlocked();
		
		boolean expectedRotation = false;
		boolean actualRotation = s.isRotationBlocked();
		
		assertEquals(expectedX, actualX);
		assertEquals(expectedY, actualY);
		assertEquals(expectedRotation, actualRotation);
	}
	
	@Test
	public void CreateSupport_RollerSupport_RollerBlockadesSets() {
		Support s = Support.ROLLER;
		boolean expectedX = true;
		boolean actualX = s.isXMoveBlocked();
		
		boolean expectedY = false;
		boolean actualY = s.isYMoveBlocked();
		
		boolean expectedRotation = false;
		boolean actualRotation = s.isRotationBlocked();
		
		assertEquals(expectedX, actualX);
		assertEquals(expectedY, actualY);
		assertEquals(expectedRotation, actualRotation);
	}
	
	@Test
	public void CreateSupport_SliderSupport_SliderBlockadesSets() {
		Support s = Support.SLIDER;
		boolean expectedX = true;
		boolean actualX = s.isXMoveBlocked();
		
		boolean expectedY = false;
		boolean actualY = s.isYMoveBlocked();
		
		boolean expectedRotation = true;
		boolean actualRotation = s.isRotationBlocked();
		
		assertEquals(expectedX, actualX);
		assertEquals(expectedY, actualY);
		assertEquals(expectedRotation, actualRotation);
	}
	
	@Test
	public void CreateSupport_FixedSupport_FixedBlockadesSets() {
		Support s = Support.FIXED;
		boolean expectedX = true;
		boolean actualX = s.isXMoveBlocked();
		
		boolean expectedY = true;
		boolean actualY = s.isYMoveBlocked();
		
		assertEquals(expectedX, actualX);
		assertEquals(expectedY, actualY);
	}
	
	@Test
	public void GetAmountOfBonds_HingedSupport_ReturnTwo() {
		Support s = Support.HINGED;
		int expected = 2;
		int actual = s.getAmountOfBonds();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetAmountOfBonds_RollerSupport_ReturnOne() {
		Support s = Support.ROLLER;
		int expected = 1;
		int actual = s.getAmountOfBonds();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetAmountOfBonds_SliderSupport_ReturnTwo() {
		Support s = Support.SLIDER;
		int expected = 2;
		int actual = s.getAmountOfBonds();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void GetAmountOfBonds_FixedSupport_ReturnThree() {
		Support s = Support.FIXED;
		int expected = 3;
		int actual = s.getAmountOfBonds();
		
		assertEquals(expected, actual);
	}

}
