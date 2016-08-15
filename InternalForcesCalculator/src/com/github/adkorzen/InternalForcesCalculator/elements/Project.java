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

	private void createDisks() {
		addFoundationDisk();
		List<Bar> assigned = new ArrayList<Bar>();
		for (Bar b : bars) {
			if (!assigned.contains(b)) {
				Disk disk = new Disk(b);
				disks.add(disk);
				assigned.add(b);

				if (!b.isStartingNodeReleased()) {
					Node check = b.getStartingNode();
					for (Bar other : bars) {
						if (!assigned.contains(other)) {

							Node first = other.getStartingNode();
							Node second = other.getStartingNode();

							if (first.equals(check) && !other.isStartingNodeReleased()) {
								assigned.add(other);
								disk.addBar(other);
								if (!other.isEndingNodeReleased()) {
									Node toCheck = second;

									checkBarConnection(disk, assigned, toCheck);

								}
							} else if (second.equals(check) && !other.isEndingNodeReleased()) {
								assigned.add(other);
								disk.addBar(other);
								if (!other.isStartingNodeReleased()) {
									Node toCheck = first;

									checkBarConnection(disk, assigned, toCheck);

								}
							}
						}
					}
				}
				if (!b.isEndingNodeReleased()) {
					Node check = b.getEndingNode();
					for (Bar other : bars) {
						if (!assigned.contains(other)) {

							Node first = other.getStartingNode();
							Node second = other.getStartingNode();

							if (first.equals(check) && !other.isStartingNodeReleased()) {
								assigned.add(other);
								disk.addBar(other);
								if (!other.isEndingNodeReleased()) {
									Node toCheck = second;

									checkBarConnection(disk, assigned, toCheck);

								}
							} else if (second.equals(check) && !other.isEndingNodeReleased()) {
								assigned.add(other);
								disk.addBar(other);
								if (!other.isStartingNodeReleased()) {
									Node toCheck = first;

									checkBarConnection(disk, assigned, toCheck);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void checkBarConnection(Disk disk, List<Bar> assigned, Node check) {
		for (Bar other : bars) {
			if (!assigned.contains(other)) {

				Node first = other.getStartingNode();
				Node second = other.getStartingNode();

				if (first.equals(check) && !other.isStartingNodeReleased()) {
					assigned.add(other);
					disk.addBar(other);
					if (!other.isEndingNodeReleased()) {
						Node toCheck = second;

						checkBarConnection(disk, assigned, toCheck);

					}
				} else if (second.equals(check) && !other.isEndingNodeReleased()) {
					assigned.add(other);
					disk.addBar(other);
					if (!other.isStartingNodeReleased()) {
						Node toCheck = first;

						checkBarConnection(disk, assigned, toCheck);
					}
				}
			}
		}
	}

	private void addFoundationDisk() {
		disks.add(new Disk());
	}

	public void addNode(double x, double y) {
		Node node = new Node(this, x, y);
		if (!nodes.contains(node)) {
			nodes.add(node);
		}
	}

	public void removeNode(Node node) {
		for (Bar b : bars) {
			if (b.getStartingNode().equals(node) || b.getEndingNode().equals(node)) {
				System.out.println(
						"You can't remove node if there are existing bars containing that node. Remove that bars first");
				return;
			}
		}
		nodes.remove(node);
	}

	public Node getNode(double x, double y) {
		Node compare = new Node(this, x, y);
		for (Node el : nodes) {
			if (el.equals(compare)) {
				return el;
			}
		}
		return null;
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

	public Node getSupport(double x, double y) {
		Node compare = new Node(this, x, y);
		for (Node el : supports) {
			if (el.equals(compare)) {
				return el;
			}
		}
		return null;
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

	/// toTest
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
		if (!bars.contains(bar)) {
			bars.add(bar);
		}
	}

	public Bar getBar(double x, double y) {
		Point p = new Point(x, y);
		for (Bar el : bars) {
			if (el.contains(p)) {
				return el;
			}
		}
		return null;
	}

	public void removeBar(Bar bar) {
		bars.remove(bar);
	}

	// public void addDisk(Disk disk) {
	// disks.add(disk);
	// }
	//
	// public void removeDisk(Disk disk) {
	// disks.remove(disk);
	// }

	public boolean areNodesStable() {
		int barCount = 0;
		int releaseCount = 0;
		for (Node n : nodes) {
			barCount = 0;
			releaseCount = 0;
			for (Bar b : bars) {
				if (b.contains(n)) {
					barCount++;
					if (b.getStartingNode().equals(n) && b.isStartingNodeReleased()) {
						releaseCount++;
					} else if (b.getEndingNode().equals(n) && b.isEndingNodeReleased()) {
						releaseCount++;
					}
				}
			}

			if (releaseCount == barCount) {
				return false;
			} else if (releaseCount > barCount) {
				System.out.println("Programming error");
				return false;
			}
		}
		return true;
	}

	public boolean isStaticallySolvable() {
		createDisks();
		int bonds = 0;
		for (Node support : supports) {
			bonds += support.getSupport().getAmountOfBonds();
		}
		for (Bar bar : bars) {
			if (bar.isStartingNodeReleased()) {
				bonds += 2;
			}
			if (bar.isEndingNodeReleased()) {
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

	public Project(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
