package com.github.adkorzen.InternalForcesCalculator.results;

import com.github.adkorzen.InternalForcesCalculator.loads.NodeLoad;

public class Reaction {
	private final NodeLoad reactions;
	
	public Reaction (NodeLoad reactions) {
		this.reactions = reactions;
	}
	
	public NodeLoad getReactions() {
		return reactions;
	}
	
	public double getX() {
		return reactions.getX();
	}
	
	public double getY() {
		return reactions.getY();
	}
	
	public double getMoment() {
		return reactions.getMoment();
	}
}
