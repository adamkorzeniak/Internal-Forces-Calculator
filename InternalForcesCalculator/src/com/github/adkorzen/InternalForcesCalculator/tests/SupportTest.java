package com.github.adkorzen.InternalForcesCalculator.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Support;

public class SupportTest {

	@Test
	public void CreateSupport_CreateHingedSupport_HingedBlockadesSets() {
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
	public void CreateSupport_CreateRollerSupport_RollerBlockadesSets() {
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
	public void CreateSupport_CreateSliderSupport_SliderBlockadesSets() {
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
	public void CreateSupport_CreateFixedSupport_FixedBlockadesSets() {
		Support s = Support.FIXED;
		boolean expectedX = true;
		boolean actualX = s.isXMoveBlocked();
		
		boolean expectedY = true;
		boolean actualY = s.isYMoveBlocked();
		
		boolean expectedRotation = true;
		boolean actualRotation = s.isRotationBlocked();
		
		assertEquals(expectedX, actualX);
		assertEquals(expectedY, actualY);
		assertEquals(expectedRotation, actualRotation);
	}

}
