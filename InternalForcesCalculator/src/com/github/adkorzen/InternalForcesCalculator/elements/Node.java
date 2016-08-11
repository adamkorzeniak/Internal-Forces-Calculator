package com.github.adkorzen.InternalForcesCalculator.elements;

public class Node {
	private int x, y;
	private Support support;
	private double supportAngle;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setSupport(Support support) {
		setSupport(support, 0);
	}

	public void setSupport(Support support, double supportAngle) {
		this.support = support;
		setSupportAngle(supportAngle);
	}

	public Support getSupport() {
		return support;
	}

	public void setSupportAngle(double supportAngle) {
		if (support != null) {
			
			while (supportAngle < 0.0) {
				supportAngle += 360.0;
			}
			while (supportAngle > 360.0) {
				supportAngle -= 360.0;
			}
			
			this.supportAngle = supportAngle;
		} else {
			System.out.println("There is no support in this node. Angle cannot be changed.");
		}
	}

	public double getSupportAngle() {
		return supportAngle;
	}
}
