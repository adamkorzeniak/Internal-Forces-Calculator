package com.github.adkorzen.InternalForcesCalculator.elements;

public enum Support {
	
	HINGED(true, true, false), 
	ROLLER(true, false, false),
	SLIDER(true, false, true), 
	FIXED(true, true, true);
	
	private boolean xMoveBlocked;
	private boolean yMoveBlocked;
	private boolean rotationBlocked;

	private Support(boolean xMoveBlocked, boolean yMoveBlocked, boolean rotationBlocked) {
		this.xMoveBlocked = xMoveBlocked;
		this.yMoveBlocked = yMoveBlocked;
		this.rotationBlocked = rotationBlocked;
	}
	
	public boolean isXMoveBlocked() {
		return xMoveBlocked;
	}

	public boolean isYMoveBlocked() {
		return yMoveBlocked;
	}

	public boolean isRotationBlocked() {
		return rotationBlocked;
	}
}
