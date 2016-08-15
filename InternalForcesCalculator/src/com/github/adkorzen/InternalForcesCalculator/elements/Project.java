package com.github.adkorzen.InternalForcesCalculator.elements;

import java.util.ArrayList;
import java.util.List;

public class Project {
	public static final double ACCURACY = 0.000001;

	private String name;
	private List<Node> nodes = new ArrayList<Node>();
	private List<Node> supports = new ArrayList<Node>();
	private List<Bar> bars = new ArrayList<Bar>();
	private List<Disk> disks = new ArrayList<Disk>();

	public Project(String name) {
		this.name = name;
	}

	///// to test and to change
	public void createDisks() {
		addFoundationDisk();
	}

	/// to change without testing (test above method)
	private void addFoundationDisk() {
		disks.add(new Disk(this));
	}

	public String getName() {
		return name;
	}

	public void addNode(double x, double y) {
		Node node = new Node(this, x, y);
		nodes.add(node);
	}

	public void removeNode(Node node) {
		nodes.remove(node);
	}

	public List<Node> getNodesList() {
		return nodes;
	}

	public void addSupport(Node support) {
		if (!supports.contains(support)) {
			supports.add(support);
		}
	}

	public void removeSupport(Node support) {
		if (supports.contains(support)) {
			supports.remove(support);
		}
	}
	
	public List<Node> getSupportsList() {
		return supports;
	}
	
	// to test and to implement
	public List<Bar> selectBars(int x1, int y1, int x2, int y2) {
		return null;
	}
	
	public List<Node> selectNodes(int x1, int y1, int x2, int y2) {
		return null;
	}
	
	public List<Element> selectElements(int x1, int y1, int x2, int y2) {
		return null;
	}
	
	public Element selectElement(int x, int y) {
		return null;
	}

	///toTest
	public void addBar(double xStart, double yStart, double xEnd, double yEnd) {
		Node start = null;
		Node end = null;
		for (Node node : nodes) {
			if (xStart == node.getX() && yStart == node.getY()) {
				start = node;
			} else if (xEnd == node.getX() && yEnd == node.getY()) {
				end = node;
			}
		}
		if (start == null) {
			start = new Node(this, xStart, yStart);
			addNode(xStart, yStart);
		}
		if (end == null) {
			end = new Node(this, xEnd, yEnd);
			addNode(xEnd, yEnd);
		}

		Bar bar = new Bar(this, start, end);
		bars.add(bar);
	}
	public List<Bar> getBarsList() {
		return bars;
	}
	
	public Node getNode(double x, double y) {
		Node compare = new Node(this, x, y);
		for (Node el: nodes) {
			if (el.equals(compare)) {
				return el;
			}
		}
		return null;
	}
	
	public Bar getBar(double x, double y) {
		Point p = new Point(x, y);
		for (Bar el: bars) {
			if (el.contains(p)) {
				return el;
			}
		}
		return null;
	}

	public void removeBar(Bar bar) {
		bars.remove(bar);
	}

	public void addDisk(Disk disk) {
		disks.add(disk);
	}

	public void removeDisk(Disk disk) {
		disks.remove(disk);
	}

	public boolean contains(Disk disk) {
		return disks.contains(disk);
	}

	public boolean contains(Bar bar) {
		return bars.contains(bar);
	}

	public boolean contains(Node node) {
		return nodes.contains(node);
	}
	
	public boolean containsSupport(Node support) {
		return supports.contains(support);
	}

	public boolean isStaticallySolvable() {
		int bonds = 0;
		for (Node support : supports) {
			bonds += support.getSupport().getAmountOfBonds();
		}
		for (Bar bar : bars) {
			if (bar.isStartingNodeJoint()) {
				bonds += 2;
			}
			if (bar.isEndingNodeJoint()) {
				bonds += 2;
			}
		}
		if ((disks.size() - 1) * 3 == bonds) {
			return true;
		}
		return false;
	}

	public boolean isGeometricallyStable() {
		return false;
	}
}
