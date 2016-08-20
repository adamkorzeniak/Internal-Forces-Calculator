package com.github.adkorzen.InternalForcesCalculator.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Disk {
	private final List<Bar> elements = new ArrayList<Bar>();
	private final Map<Disk, List<Line>> bonds = new HashMap<Disk, List<Line>>();

	public Disk() {
	}

	public Disk(Bar bar) {
		elements.add(bar);
	}
	
	public void addBond(Disk another, Line bond) {
		if (bonds.get(another) == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(bond);
			bonds.put(another, lines);
		}
		if (!bonds.get(another).contains(bond)) {
			List<Line> lines = bonds.get(another);
			lines.add(bond);
			bonds.put(another, lines);
		}
	}
	
	public boolean contains(Node n) {
		for (Bar b : elements) {
			if (b.getStartingNode().equals(n) && !b.isStartingNodeReleased()) {
				return true;
			} else if (b.getEndingNode().equals(n) && !b.isEndingNodeReleased()) {
				return true;
			} else if (!b.getStartingNode().equals(n) && !b.getEndingNode().equals(n) && b.contains(n)) {
				return true;
			}
		}
		return false;
	}

	public void addBar(Bar bar) {
		elements.add(bar);
	}

	public List<Bar> getBars() {
		return elements;
	}

	public List<Line> getBonds(Disk another) {
		return bonds.get(another);
	}
	
	public Map<Disk, List<Line>> getBonds() {
		return bonds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disk other = (Disk) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String result = "Disc containing: ";
		for (Bar b : elements) {
			result += b + ", ";
		}
		return result;
	}
}
