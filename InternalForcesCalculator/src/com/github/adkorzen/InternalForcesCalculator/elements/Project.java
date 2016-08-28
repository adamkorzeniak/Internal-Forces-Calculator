package com.github.adkorzen.InternalForcesCalculator.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.adkorzen.InternalForcesCalculator.loads.BarLoad;
import com.github.adkorzen.InternalForcesCalculator.loads.NodeLoad;
import com.github.adkorzen.InternalForcesCalculator.matrix.Matrix;
import com.github.adkorzen.InternalForcesCalculator.results.Force;
import com.github.adkorzen.InternalForcesCalculator.results.LinearForce;
import com.github.adkorzen.InternalForcesCalculator.results.RotationForce;

public class Project {

	public static final double ACCURACY = 0.000001;
	public static final double MINIMAL_DISTANCE_LEAP = 0.01; // m

	private String name;
	private boolean disksChanged = false;
	private List<Node> nodes = new ArrayList<Node>();
	private List<Node> supports = new ArrayList<Node>();
	private List<Bar> bars = new ArrayList<Bar>();
	private List<Disk> disks = new ArrayList<Disk>();

	private List<Force> forces;
	private List<Force> reactions;

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

	public void calculateReactions() {
		generateReactions();
		generateForces();

		double[][] matrix = generateMatrix();
		double[] results = Matrix.gaussianElimination(matrix);

		for (int i = 0; i < results.length; i++) {
			Force reaction = reactions.get(i);
			reaction.setValue(results[i]);
			Point p = reaction.getPoint();
			this.getNode(p.getX(), p.getY()).setReaction(reaction);
		}
	}

	private void generateReactions() {
		reactions = new ArrayList<Force>();

		for (Node node : supports) {
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

			if (support == Support.FIXED) {
				LinearForce react1 = new LinearForce(point, slope);
				LinearForce react2 = new LinearForce(point, verticalSlope);
				RotationForce react3 = new RotationForce(point);
				reactions.add(react1);
				reactions.add(react2);
				reactions.add(react3);
			} else if (support == Support.SLIDER) {
				LinearForce react1 = new LinearForce(point, slope);
				RotationForce react2 = new RotationForce(point);
				reactions.add(react1);
				reactions.add(react2);
			} else if (support == Support.HINGED) {
				LinearForce react1 = new LinearForce(point, slope);
				LinearForce react2 = new LinearForce(point, verticalSlope);
				reactions.add(react1);
				reactions.add(react2);
			} else if (support == Support.ROLLER) {
				LinearForce react1 = new LinearForce(point, verticalSlope);
				reactions.add(react1);
			}
		}
	}

	private void generateForces() {
		forces = new ArrayList<Force>();
		// implement angled forces
		for (Node node : nodes) {
			List<NodeLoad> loads = node.getLoads();
			if (loads == null) {
				continue;
			}
			for (NodeLoad load : loads) {
				if (load.getX() != 0) {
					LinearForce f = new LinearForce(new Point(node.getX(), node.getY()), 0, load.getX());
					forces.add(f);
				}
				if (load.getY() != 0) {
					LinearForce f = new LinearForce(new Point(node.getX(), node.getY()), Double.POSITIVE_INFINITY,
							load.getY());
					forces.add(f);
				}
				if (load.getMoment() != 0) {
					RotationForce f = new RotationForce(new Point(node.getX(), node.getY()), load.getMoment());
					forces.add(f);
				}
			}
		}
		for (Bar bar : bars) {
			List<BarLoad> loads = bar.getLoads();
			if (loads == null) {
				continue;
			}
			for (BarLoad load : loads) {
				double x1 = load.getX1();
				double x2 = load.getX2();
				double y1 = load.getY1();
				double y2 = load.getY2();
				double moment1 = load.getMoment1();
				double moment2 = load.getMoment2();

				// to change
				if (x1 != 0 || x2 != 0) {
					double length = 0;
					if (load.isProjected()) {
						length = Math.abs(bar.getStartingNode().getY() - bar.getEndingNode().getY());
					} else {
						length = Math.abs(bar.getStartingNode().getPoint().distanceTo(bar.getEndingNode().getPoint()));
					}
					if (x1 != -x2) {
						double relative = (x2 * 2 / 3 + x1 / 3) / (x1 + x2);

						Point forcePoint = bar.getPoint(relative);
						double value = (x1 + x2) / 2 * length;
						LinearForce f = new LinearForce(forcePoint, 0, value);
						forces.add(f);
					}
				}
				if (y1 != 0 || y2 != 0) {
					double length = 0;
					if (load.isProjected()) {
						length = Math.abs(bar.getStartingNode().getX() - bar.getEndingNode().getX());
					} else {
						length = Math.abs(bar.getStartingNode().getPoint().distanceTo(bar.getEndingNode().getPoint()));
					}
					if (y1 != -y2) {
						double relative = (y2 * 2 / 3 + y1 / 3) / (y1 + y2);

						Point forcePoint = bar.getPoint(relative);
						double value = (y1 + y2) / 2 * length;
						LinearForce f = new LinearForce(forcePoint, Double.POSITIVE_INFINITY, value);
						forces.add(f);
					}
				}
				if (moment1 != 0 || moment2 != 0) {
					double length = 0;
					if (load.isProjected()) {
						length = Math.abs(bar.getStartingNode().getX() - bar.getEndingNode().getX());
					} else {
						length = Math.abs(bar.getStartingNode().getPoint().distanceTo(bar.getEndingNode().getPoint()));
					}
					if (moment1 != -moment2) {
						double relative = (moment2 * 2 / 3 + moment1 / 3) / (moment1 + moment2);

						Point forcePoint = bar.getPoint(relative);
						double value = (moment1 + moment2) / 2 * length;
						RotationForce f = new RotationForce(forcePoint, value);
						forces.add(f);
					}
				}
			}
		}
	}

	private double[][] generateMatrix() {
		int numOfReactions = reactions.size();
		int numOfNodes = nodes.size();
		int numOfReleases = getNumOfReleases();

		double[][] matrix = new double[numOfNodes + 2 * numOfReleases + 2][numOfReactions + 1];

		// Equation in direction X
		for (int i = 0; i < numOfReactions; i++) {
			matrix[0][i] = reactions.get(i).calculateXForce();
		}
		double xFree = 0;
		for (Force f : forces) {
			xFree += f.calculateXForce();
		}
		matrix[0][numOfReactions] = -xFree;

		// Equation in direction Y
		for (int i = 0; i < numOfReactions; i++) {
			matrix[1][i] = reactions.get(i).calculateYForce();
		}
		double yFree = 0;
		for (Force f : forces) {
			yFree += f.calculateYForce();
		}
		matrix[1][numOfReactions] = -yFree;

		// Equations for rotations
		for (int n = 0; n < numOfNodes; n++) {
			Point point = new Point(nodes.get(n).getX(), nodes.get(n).getY());
			for (int i = 0; i < numOfReactions; i++) {
				matrix[n + 2][i] = reactions.get(i).calculateMoment(point);
			}
			double free = 0;
			for (Force f : forces) {
				free += f.calculateMoment(point);
			}
			matrix[n + 2][numOfReactions] = -free;
		}

		// Equations for rotations left or right to release
		int addIndex = 0;
		for (Bar b : bars) {
			if (b.isStartingNodeReleased()) {
				Point point = b.getStartingNode().getPoint();
				for (int i = 0; i < numOfReactions; i++) {
					Force f = reactions.get(i);
					if (connected(b, b.getStartingNode(), f)) {
						matrix[numOfNodes + addIndex + 2][i] = f.calculateMoment(point);
					} else {
						matrix[numOfNodes + addIndex + 1 + 2][i] = f.calculateMoment(point);
					}
				}
				double free1 = 0;
				double free2 = 0;
				for (Force f : forces) {
					if (connected(b, b.getStartingNode(), f)) {
						free1 += f.calculateMoment(point);
					} else {
						free2 += f.calculateMoment(point);
					}
				}
				matrix[numOfNodes + addIndex + 2][numOfReactions] = -free1;
				matrix[numOfNodes + addIndex + 1 + 2][numOfReactions] = -free2;

				addIndex += 2;
			}
			if (b.isEndingNodeReleased()) {
				Point point = b.getEndingNode().getPoint();
				for (int i = 0; i < numOfReactions; i++) {
					Force f = reactions.get(i);
					if (connected(b, b.getEndingNode(), f)) {
						matrix[numOfNodes + addIndex + 2][i] = f.calculateMoment(point);
					} else {
						matrix[numOfNodes + addIndex + 1 + 2][i] = f.calculateMoment(point);
					}
				}
				double free1 = 0;
				double free2 = 0;
				for (Force f : forces) {
					if (connected(b, b.getEndingNode(), f)) {
						free1 += f.calculateMoment(point);
					} else {
						free2 += f.calculateMoment(point);
					}
				}
				matrix[numOfNodes + addIndex + 2][numOfReactions] = -free1;
				matrix[numOfNodes + addIndex + 1 + 2][numOfReactions] = -free2;

				addIndex += 2;
			}
		}

		return matrix;
	}

	private boolean connected(Bar end, Node released, Force force) {
		Point fPoint = force.getPoint();
		Point node = released.getPoint();
		List<Bar> checked = new ArrayList<Bar>();
		if (!fPoint.equals(node)) {
			Bar forceBar = null;
			for (Bar b : bars) {
				if (b.contains(fPoint)) {
					if (b.equals(end)) {
						return true;
					}
					forceBar = b;
				}
			}
			checked.add(forceBar);
			boolean result = checkBarConnection(forceBar, end, released, checked);
			return result;
		}
		return false;
	}

	private boolean checkBarConnection(Bar start, Bar end, Node released, List<Bar> checked) {
		Point connected = start.getCommonPoint(end);
		if (connected != null && !connected.equals(released.getPoint())) {
			return true;
		}
		for (Bar other : bars) {
			if (!checked.contains(other)) {
				Point common = start.getCommonPoint(other);
				if (common != null && !common.equals(released.getPoint())) {
					checked.add(other);
					boolean condition = checkBarConnection(other, end, released, checked);
					if (condition) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private int getNumOfReleases() {
		int result = 0;
		for (Bar b : bars) {
			if (b.isStartingNodeReleased())
				result++;
			if (b.isEndingNodeReleased())
				result++;
		}
		return result;
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
