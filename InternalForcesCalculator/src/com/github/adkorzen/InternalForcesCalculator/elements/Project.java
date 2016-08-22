package com.github.adkorzen.InternalForcesCalculator.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.adkorzen.InternalForcesCalculator.loads.NodeLoad;
import com.github.adkorzen.InternalForcesCalculator.results.Reaction;

public class Project {

	public static final double ACCURACY = 0.000001;
	public static final double MINIMAL_DISTANCE_LEAP = 0.01; // m

	private String name;
	private boolean disksChanged = false;
	private List<Node> nodes = new ArrayList<Node>();
	private List<Node> supports = new ArrayList<Node>();
	private List<Bar> bars = new ArrayList<Bar>();
	private List<Disk> disks = new ArrayList<Disk>();

	public Project(String name) {
		this.name = name;
	}

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
		disksChanged = true;
		int bonds = 0;
		for (Node support : supports) {
			bonds += support.getSupport().getAmountOfBonds();

			for (Disk disk : disks) {
				if (disk.contains(support) && !disk.equals(getFoundation())) {
					bondWithGround(disk, support);
				}
			}
		}
		for (Bar bar : bars) {
			if (bar.isStartingNodeReleased()) {
				Node node = bar.getStartingNode();
				bonds += 2;
				createBonds(bar, node);
			}
			if (bar.isEndingNodeReleased()) {
				Node node = bar.getEndingNode();
				bonds += 2;
				createBonds(bar, node);
			}
		}
		if ((disks.size() - 1) * 3 == bonds) {
			return true;
		}
		return false;
	}

	public boolean isGeometricallyStable() {
		while (disksChanged) {
			disksChanged = false;
			mergeTwoDisksIfStable();
			mergeThreeDisksIfStable();

			for (Iterator<Disk> it = disks.iterator(); it.hasNext();) {
				Disk disk = it.next();
				if (disk == null) {
					it.remove();
				}
			}
		}

		if (disks.size() == 1) {
			return true;
		}
		return false;
	}
	
	// To implement
	public void calculateReactions() {
		for (Node support: supports) {
			support.setReactions(new Reaction(new NodeLoad.Builder().build()));
		}
	}

	private void createDisks() {
		addFoundationDisk();
		List<Bar> assigned = new ArrayList<Bar>();
		for (Bar b : bars) {
			if (!assigned.contains(b)) {
				Disk disk = new Disk(b);
				disks.add(disk);
				assigned.add(b);

				if (!b.isStartingNodeReleased()) {
					Node toCheck = b.getStartingNode();
					checkBarConnection(disk, assigned, b, toCheck);
				}
				if (!b.isEndingNodeReleased()) {
					Node toCheck = b.getEndingNode();
					checkBarConnection(disk, assigned, b, toCheck);
				}
			}
		}
	}

	private void checkBarConnection(Disk disk, List<Bar> assigned, Bar bar, Node check) {
		for (Bar other : bars) {
			if (!assigned.contains(other)) {

				Node first = other.getStartingNode();
				Node second = other.getEndingNode();

				if (first.equals(check) && !other.isStartingNodeReleased()) {
					assigned.add(other);
					disk.addBar(other);
					if (!other.isEndingNodeReleased()) {
						Node toCheck = second;

						checkBarConnection(disk, assigned, other, toCheck);
					}
				} else if (second.equals(check) && !other.isEndingNodeReleased()) {
					assigned.add(other);
					disk.addBar(other);
					if (!other.isStartingNodeReleased()) {
						Node toCheck = first;

						checkBarConnection(disk, assigned, other, toCheck);
					}
				} else if (!first.equals(check) && !second.equals(check) && other.contains(check)) {
					assigned.add(other);
					disk.addBar(other);

					if (!other.isStartingNodeReleased()) {
						Node toCheck = first;

						checkBarConnection(disk, assigned, other, toCheck);
					}

					if (!other.isEndingNodeReleased()) {
						Node toCheck = second;

						checkBarConnection(disk, assigned, other, toCheck);
					}
				}

				else if (bar.contains(first) && !other.isStartingNodeReleased() && !bar.getStartingNode().equals(first)
						&& !bar.getEndingNode().equals(first)
						|| bar.contains(second) && !other.isEndingNodeReleased()
								&& !bar.getStartingNode().equals(second) && !bar.getEndingNode().equals(second)) {
					assigned.add(other);
					disk.addBar(other);

					if (!other.isStartingNodeReleased()) {
						Node toCheck = first;
						checkBarConnection(disk, assigned, other, toCheck);
					}
					if (!other.isEndingNodeReleased()) {
						Node toCheck = second;
						checkBarConnection(disk, assigned, other, toCheck);
					}
				}
			}
		}
	}

	private void bondWithGround(Disk disk, Node node) {

		Support support = node.getSupport();
		double slope = node.getSlope();
		double verticalSlope = 0;
		if (slope == 0) {
			verticalSlope = Double.POSITIVE_INFINITY;
		} else if (slope == Double.POSITIVE_INFINITY) {
			verticalSlope = 0;
		} else {
			verticalSlope = -1.0 / slope;
		}
		double x = node.getX();
		double y = node.getY();
		Point point = new Point(x, y);

		double x2 = x + 0.001;
		double y2 = y + 0.001;
		Point rotationBlocked = new Point(x2, y2);

		if (support == Support.FIXED) {
			Line bond1 = new Line(point, slope);
			Line bond2 = new Line(rotationBlocked, slope);
			Line bond3 = new Line(point, verticalSlope);
			addBonds(disk, getFoundation(), bond1, bond2, bond3);
		} else if (support == Support.SLIDER) {
			Line bond1 = new Line(point, slope);
			Line bond2 = new Line(rotationBlocked, slope);
			addBonds(disk, getFoundation(), bond1, bond2);
		} else if (support == Support.HINGED) {
			Line bond1 = new Line(point, slope);
			Line bond2 = new Line(point, verticalSlope);
			addBonds(disk, getFoundation(), bond1, bond2);
		} else if (support == Support.ROLLER) {
			Line bond1 = new Line(point, verticalSlope);
			addBonds(disk, getFoundation(), bond1);
		}
	}

	private void createBonds(Bar bar, Node node) {
		Disk original = null;
		for (Disk disk : disks) {
			if (disk.getBars().contains(bar)) {
				original = disk;
				break;
			}
		}

		for (Disk disk : disks) {
			if (disk.contains(node)) {
				Point point = new Point(node.getX(), node.getY());
				Line bond1 = new Line(point, 0);
				Line bond2 = new Line(point, Double.POSITIVE_INFINITY);
				addBonds(original, disk, bond1, bond2);
			}
		}
	}

	private void addBonds(Disk disk1, Disk disk2, Line... bonds) {
		for (Line b : bonds) {
			disk1.addBond(disk2, b);
			disk2.addBond(disk1, b);
		}
	}

	private void mergeTwoDisksIfStable() {

		outer: for (Disk disk : disks) {
			for (Disk other : disks) {
				if (other != null && !disk.equals(other)) {
					List<Line> bonds = disk.getBonds(other);
					if (bonds != null && bonds.size() == 3) {
						Line first = bonds.get(0);
						Line second = bonds.get(1);
						Line third = bonds.get(2);
						if (areBondsStable(first, second, third)) {
							mergeDisks(disk, other);
							break outer;
						}
					}
				}
			}
		}
	}

	private boolean areBondsStable(Line first, Line second, Line third) {
		if (Math.abs(first.getSlope() - second.getSlope()) < Project.ACCURACY
				&& Math.abs(third.getSlope() - second.getSlope()) < Project.ACCURACY) {
			return false;
		}
		if (first.equals(second) || second.equals(third)) {
			return false;
		}
		Point firstAndSecond = first.getCrossPoint(second);
		Point firstAndThird = first.getCrossPoint(third);
		Point secondAndThird = second.getCrossPoint(third);

		if (firstAndSecond.equals(firstAndThird) && firstAndSecond.equals(secondAndThird)) {
			return false;
		}

		return true;
	}

	private void mergeThreeDisksIfStable() {
		if (disks.size() < 3) {
			return;
		}
		outer: for (int a = 0; a < disks.size() - 2; a++) {
			Disk first = disks.get(a);
			for (int b = a + 1; b < disks.size() - 1; b++) {
				Disk second = disks.get(b);
				List<Line> bondsFirstSecond = first.getBonds(second);
				if (bondsFirstSecond != null && bondsFirstSecond.size() == 2) {
					for (int c = b + 1; c < disks.size(); c++) {
						Disk third = disks.get(c);

						List<Line> bondsFirstThird = first.getBonds(third);
						List<Line> bondsSecondThird = second.getBonds(third);

						if (bondsFirstThird != null && bondsFirstThird.size() == 2 && bondsSecondThird != null
								&& bondsSecondThird.size() == 2) {

							Point firstSecond = bondsFirstSecond.get(0).getCrossPoint(bondsFirstSecond.get(1));
							Point firstThird = bondsFirstThird.get(0).getCrossPoint(bondsFirstThird.get(1));
							Point secondThird = bondsSecondThird.get(0).getCrossPoint(bondsSecondThird.get(1));

							Line one = firstSecond.getCommonLine(firstThird);
							Line two = firstSecond.getCommonLine(secondThird);
							Line three = secondThird.getCommonLine(firstThird);

							if (one != null && two != null && three != null) {
								if (Math.abs(one.getSlope() - two.getSlope()) < Project.ACCURACY
										&& Math.abs(two.getSlope() - three.getSlope()) < Project.ACCURACY) {
									continue;
								}
							}
							mergeDisks(first, second);
							mergeDisks(first, third);
							break outer;
						}
					}
				}
			}
		}
	}

	private void mergeDisks(Disk first, Disk second) {
		disks.remove(second);
		List<Bar> firstElements = first.getBars();
		List<Bar> secondElements = second.getBars();

		Map<Disk, List<Line>> secondBonds = second.getBonds();

		for (Bar bar : secondElements) {
			firstElements.add(bar);
		}

		for (Map.Entry<Disk, List<Line>> entry : secondBonds.entrySet()) {
			Disk disk = entry.getKey();
			if (!disk.equals(first)) {
				List<Line> bonds = entry.getValue();
				for (Line l : bonds) {
					first.addBond(disk, l);
				}
			}
		}
		disksChanged = true;
	}

	private void addFoundationDisk() {
		disks.add(new Disk());
	}

	public Disk getFoundation() {
		return disks.get(0);
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
	//

	@Override
	public String toString() {
		return name;
	}
}
