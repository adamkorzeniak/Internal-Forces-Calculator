package com.github.adkorzen.InternalForcesCalculator.tests;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;
import com.github.adkorzen.InternalForcesCalculator.elements.Support;
import com.github.adkorzen.InternalForcesCalculator.loads.BarLoad;
import com.github.adkorzen.InternalForcesCalculator.loads.NodeLoad;

public class ExamplesOfStructures {
	private static BarLoad bl;
	private static NodeLoad nl;

	public static Project createSimpleJointedBeam() {
		Project p = new Project("Simple Jointed Beam");
		p.addBar(0, 0, 6, 0);
		p.addBar(6, 0, 10, 0);
		p.getNode(0, 0).setSupport(Support.ROLLER);
		p.getNode(10, 0).setSupport(Support.FIXED);
		p.getBar(5, 0).setEndingNodeReleased(true);
		
		return p;
	}

	public static Project createSimpleSupportedBeam() {
		Project p = new Project("Simple Supported Beam");
		p.addBar(0, 0, 10, 0);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(10, 0).setSupport(Support.ROLLER);

		return p;
	}

	public static Project createSource1Frame1() {
		Project p = new Project("Source1 Frame1");
		p.addBar(2, 2, 6, 5);
		p.addBar(6, 5, 9, 5);
		p.addBar(7, 5, 7, 3);
		p.getNode(2, 2).setSupport(Support.ROLLER);
		p.getNode(9, 5).setSupport(Support.ROLLER, 90);
		p.getNode(7, 3).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().x(2).projected().build();
		p.getBar(3, 2.75).addLoad(bl);
		nl = new NodeLoad.Builder().moment(-2).build();
		p.getNode(6, 5).addLoad(nl);
		p.addNode(7, 4);
		nl = new NodeLoad.Builder().moment(2).build();
		p.getNode(7, 4).addLoad(nl);
		nl = new NodeLoad.Builder().y(-3).build();
		p.getNode(7, 4).addLoad(nl);
		
		return p;
	}

	public static Project createSource1Frame2() {
		Project p = new Project("Source1 Frame2");
		p.addBar(0, 6, 4, 3);
		p.addBar(4, 3, 4, 0);
		p.addBar(4, 3, 8, 3);
		p.getNode(0, 6).setSupport(Support.ROLLER);
		p.getNode(8, 3).setSupport(Support.SLIDER);
		
		bl = new BarLoad.Builder().x(1).projected().build();
		p.getBar(2, 4.5).addLoad(bl);
		p.getBar(4, 2).addLoad(bl);
		nl = new NodeLoad.Builder().y(-10).build();
		p.getNode(4, 3).addLoad(nl);
		return p;
	}

	public static Project createSource1Frame3() {
		Project p = new Project("Source1 Frame3");
		p.addBar(0, 6, 4, 3);
		p.addBar(4, 3, 4, 0);
		p.addBar(4, 3, 8, 3);
		p.getNode(0, 6).setSupport(Support.SLIDER);
		p.getNode(8, 3).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().x(1).projected().build();
		p.getBar(2, 4.5).addLoad(bl);
		p.getBar(4, 2).addLoad(bl);
		nl = new NodeLoad.Builder().moment(10).build();
		p.getNode(4, 3).addLoad(nl);
		nl = new NodeLoad.Builder().y(10).build();
		p.getNode(4, 0).addLoad(nl);
		return p;
	}

	public static Project createSource1Frame4() {
		Project p = new Project("Source1 Frame4");
		p.addBar(0, 0, 8, 0);
		p.addBar(8, 0, 8, 4);
		p.addBar(0, 0, 4, 0);
		p.addBar(4, 4, 3.99, 4);
		p.addBar(4, 4, 8, 4);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(8, 0).setSupport(Support.ROLLER);
		
		nl = new NodeLoad.Builder().y(-10).build();
		p.getNode(3.99, 4).addLoad(nl);
		nl = new NodeLoad.Builder().y(10).build();
		p.getNode(4, 4).addLoad(nl);
		
		return p;
	}

	public static Project createSource1Frame5() {
		Project p = new Project("Source1 Frame5");
		p.addBar(0, 0, 0, 2.5);
		p.addBar(0, 2.5, 9, 2.5);
		p.addBar(9, 0, 9, 2.5);
		p.addBar(4.5, 0, 9, 0);
		p.getNode(0, 0).setSupport(Support.FIXED);
		
		nl = new NodeLoad.Builder().y(-10).build();
		p.getNode(4.5, 0).addLoad(nl);
		return p;
	}

	public static Project createSource1Frame6() {
		Project p = new Project("Source1 Frame6");
		p.addBar(0, 0, 8, 6);
		p.addBar(4, 6, 8, 6);
		p.getNode(0, 0).setSupport(Support.ROLLER, 90);
		p.getNode(8, 6).setSupport(Support.HINGED);
		
		p.getBar(4, 3).divide(0.5);
		bl = new BarLoad.Builder().y(-6).projected().build();
		p.getBar(2, 1.5).addLoad(bl);
		bl = new BarLoad.Builder().y(-6).build();
		p.getBar(6, 6).addLoad(bl);
		nl = new NodeLoad.Builder().moment(-60).build();
		p.getNode(4, 3).addLoad(nl);
		return p;
		
	}

	public static Project createSource1Frame7() {
		Project p = new Project("Source1 Frame7");
		p.addBar(0, 3, 0, 0);
		p.addBar(0, 0, 8, 6);
		p.getNode(0, 3).setSupport(Support.ROLLER);
		p.getNode(8, 6).setSupport(Support.HINGED);
		
		bl = new BarLoad.Builder().x(6).projected().build();
		p.getBar(0, 2).addLoad(bl);
		p.getBar(4, 3).divide(0.5);
		p.getBar(6, 4.5).addLoad(bl);
		nl = new NodeLoad.Builder().moment(40).build();
		p.getNode(4, 3).addLoad(nl);
		return p;
	}

	public static Project createSource1Frame8() {
		Project p = new Project("Source1 Frame8");
		p.addBar(0, 0, 8, 6);
		p.addBar(0, 3, 4, 3);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(8, 6).setSupport(Support.ROLLER, 90);
		
		bl = new BarLoad.Builder().y(-6).projected().build();
		p.getBar(2, 3).addLoad(bl);
		p.getBar(6, 4.5).divide(0.5);
		p.getBar(6, 4.5).addLoad(bl);
		nl = new NodeLoad.Builder().moment(60).build();
		p.getNode(4, 3).addLoad(nl);
		return p;
	}

	public static Project createSource1Frame9() {
		Project p = new Project("Source1 Frame9");
		p.addBar(0, 0, 8, 6);
		p.addBar(4, 3, 4, 6);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(8, 6).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().x(6).projected().build();
		p.getBar(6, 4.5).divide(0.5);
		p.getBar(2, 1.5).addLoad(bl);
		p.getBar(4, 4).addLoad(bl);
		nl = new NodeLoad.Builder().moment(-40).build();
		p.getNode(4, 3).addLoad(nl);
		return p;
	}

	public static Project createSource2Frame1() {
		Project p = new Project("Source2 Frame1");
		p.addBar(0, 0, 0, 6);
		p.addBar(0, 6, 6, 6);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(6, 6).setSupport(Support.ROLLER);
		
		
		bl = new BarLoad.Builder().y(-10).build();
		p.getBar(3, 6).addLoad(bl);
		p.addNode(0, 3);
		nl = new NodeLoad.Builder().x(10).build();
		p.getNode(0, 3).addLoad(nl);
		nl = new NodeLoad.Builder().moment(-10).build();
		p.getNode(0, 6).addLoad(nl);
		return p;
	}

	public static Project createSource2Frame2() {
		Project p = new Project("Source2 Frame2");
		p.addBar(0, 0, 0, 12);
		p.addBar(0, 6, 9, 6);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(9, 6).setSupport(Support.ROLLER);
		
		nl = new NodeLoad.Builder().x(10).build();
		p.getNode(0, 12).addLoad(nl);
		nl = new NodeLoad.Builder().y(-10).build();
		p.addNode(6, 6);
		p.getNode(6, 6).addLoad(nl);
		return p;
	}

	public static Project createSource2Frame3() {
		Project p = new Project("Source2 Frame3");
		p.addBar(0, 0, 0, 1);
		p.addBar(0, 1, 3, 1);
		p.addBar(1, 1, 1, 2);
		p.addNode(2, 1);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(2, 1).setSupport(Support.ROLLER);
		
		nl = new NodeLoad.Builder().x(10).build();
		p.getNode(0, 1).addLoad(nl);
		p.getNode(1, 2).addLoad(nl);
		nl = new NodeLoad.Builder().y(-20).build();
		p.getNode(0, 1).addLoad(nl);
		nl = new NodeLoad.Builder().moment(-10).build();
		p.getNode(3, 1).addLoad(nl);
		return p;
	}

	public static Project createSource2Frame4() {
		Project p = new Project("Source2 Frame4");
		p.addBar(0, 0, 0, 6);
		p.addBar(0, 6, 3, 6);
		p.addBar(3, 6, 6, 6);
		p.getBar(1, 6).setEndingNodeReleased(true);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(6, 6).setSupport(Support.HINGED);
		
		bl = new BarLoad.Builder().y(-10).build();
		p.getBar(1, 6).addLoad(bl);
		p.getBar(4, 6).addLoad(bl);
		nl = new NodeLoad.Builder().moment(10).build();
		p.addNode(0, 3);
		p.getNode(0, 3).addLoad(nl);
		nl = new NodeLoad.Builder().moment(-10).build();
		p.getNode(0, 6).addLoad(nl);
		return p;
	}

	public static Project createSource2Frame4ReleaseOnOtherSideOfNode() {
		Project p = new Project("Source2 Frame4");
		p.addBar(0, 0, 0, 6);
		p.addBar(0, 6, 3, 6);
		p.addBar(3, 6, 6, 6);
		p.getBar(4, 6).setStartingNodeReleased(true);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(6, 6).setSupport(Support.HINGED);
		
		bl = new BarLoad.Builder().y(-10).build();
		p.getBar(1, 6).addLoad(bl);
		p.getBar(4, 6).addLoad(bl);
		nl = new NodeLoad.Builder().moment(10).build();
		p.addNode(0, 3);
		p.getNode(0, 3).addLoad(nl);
		nl = new NodeLoad.Builder().moment(-10).build();
		p.getNode(0, 6).addLoad(nl);
		return p;
	}

	public static Project createSource3Beam1() {
		Project p = new Project("Source3 Beam1");
		p.addBar(2, 3, 5, 3);
		p.addBar(5, 3, 11, 3);
		p.getNode(2, 3).setSupport(Support.FIXED);
		p.getBar(4, 3).setEndingNodeReleased(true);
		p.addNode(8, 3);
		p.getNode(8, 3).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().y1(0).y2(-15).build();
		p.getBar(3, 3).addLoad(bl);
		p.getBar(6, 3).divide(0.5);
		bl = new BarLoad.Builder().y1(0).y2(-12).build();
		p.getBar(6, 3).addLoad(bl);
		nl = new NodeLoad.Builder().y(-6).build();
		p.addNode(10, 3);
		p.getNode(10, 3).addLoad(nl);
		nl = new NodeLoad.Builder().moment(8).build();
		p.getNode(11, 3).addLoad(nl);
		return p;
	}

	public static Project createSource3Frame2() {
		Project p = new Project("Source3 Frame2");
		p.addBar(0.5, 0, 2.5, 4);
		p.addBar(0, 4, 5.5, 4);
		p.addBar(5.5, 4, 8.5, 4);
		p.addBar(8.5, 4, 10.5, 0);
		p.getBar(5, 4).setEndingNodeReleased(true);
		p.getNode(0.5, 0).setSupport(Support.HINGED);
		p.getNode(10.5, 0).setSupport(Support.HINGED);
		
		bl = new BarLoad.Builder().x(6).projected().build();
		p.getBar(1.5, 2).addLoad(bl);
		bl = new BarLoad.Builder().y(-4).projected().build();
		p.getBar(6, 4).addLoad(bl);
		p.getBar(9.5, 2).addLoad(bl);
		nl = new NodeLoad.Builder().y(-10).build();
		p.getNode(0, 4).addLoad(nl);
		return p;
	}

	public static Project createSource4Beam1() {
		Project p = new Project("Source4 Beam1");
		p.addBar(0, 0, 8, 0);
		p.getNode(0, 0).setSupport(Support.ROLLER);
		p.getNode(8, 0).setSupport(Support.HINGED);
		
		p.getBar(4, 0).divide(0.5);
		bl = new BarLoad.Builder().y(-2).build();
		p.getBar(7, 0).addLoad(bl);
		nl = new NodeLoad.Builder().y(-4).build();
		p.addNode(2, 0);
		p.getNode(2, 0).addLoad(nl);
		return p;
	}

	public static Project createSource4Beam2() {
		Project p = new Project("Source4 Beam2");
		p.addBar(0, 0, 4, 0);
		p.addBar(4, 0, 10, 0);
		p.addBar(10, 0, 14, 0);
		p.getBar(2, 0).setEndingNodeReleased(true);
		p.getBar(8, 0).setEndingNodeReleased(true);
		p.getNode(0, 0).setSupport(Support.SLIDER);
		p.getNode(14, 0).setSupport(Support.ROLLER);
		p.addNode(6, 0);
		p.addNode(8, 0);
		p.getNode(6, 0).setSupport(Support.ROLLER);
		p.getNode(8, 0).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().y(-2).build();
		p.getBar(2, 0).addLoad(bl);
		p.getBar(6, 0).divide(1.0 / 3);
		p.getBar(5, 0).addLoad(bl);
		nl = new NodeLoad.Builder().y(-5).build();
		p.getNode(10, 0).addLoad(nl);
		nl = new NodeLoad.Builder().moment(-40).build();
		p.addNode(12, 0);
		p.getNode(12, 0).addLoad(nl);
		return p;
	}

	public static Project createSource4Frame3() {
		Project p = new Project("Source4 Frame3");
		p.addBar(0, 0, 0, 4);
		p.addBar(0, 4, 4, 4);
		p.addBar(4, 4, 4, 2);
		p.addBar(4, 2, 6, 2);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(6, 2).setSupport(Support.HINGED);
		p.getBar(0, 2).setEndingNodeReleased(true);
		
		bl = new BarLoad.Builder().y(-3).build();
		p.getBar(3, 4).addLoad(bl);
		nl = new NodeLoad.Builder().x(6).build();
		p.addNode(0, 2);
		p.getNode(0, 2).addLoad(nl);
		return p;
	}

	public static Project createSource4Frame4() {
		Project p = new Project("Source4 Frame4");
		p.addBar(2, 0, 2, 4);
		p.addBar(0, 4, 6, 4);
		p.addBar(2, 2, 6, 2);
		p.addBar(6, 0, 6, 4);
		p.getNode(2, 0).setSupport(Support.HINGED);
		p.getNode(6, 0).setSupport(Support.ROLLER);
		p.getBar(2, 1).setEndingNodeReleased(true);
		p.getBar(4, 2).setEndingNodeReleased(true);
		p.getBar(4, 2).setStartingNodeReleased(true);
		
		bl = new BarLoad.Builder().y(-2).build();
		p.getBar(2, 4).divide(2.0 / 3);
		p.getBar(5, 4).addLoad(bl);
		nl = new NodeLoad.Builder().y(-4).build();
		p.getNode(0, 4).addLoad(nl);
		nl = new NodeLoad.Builder().moment(6).build();
		p.addNode(2, 1);
		p.getNode(2, 1).addLoad(nl);
		return p;
	}

	public static Project createSource4Frame5() {
		Project p = new Project("Source4 Frame5");
		p.addBar(0, 0, 4, 3);
		p.addBar(4, 3, 6, 3);
		p.addBar(6, 0, 6, 3);
		p.getNode(0, 0).setSupport(Support.ROLLER);
		p.getNode(6, 0).setSupport(Support.HINGED);
		
		bl = new BarLoad.Builder().y(-3).projected().build();
		p.getBar(2, 1.5).addLoad(bl);
		nl = new NodeLoad.Builder().x(-4).build();
		p.addNode(6, 1.5);
		p.getNode(6, 1.5).addLoad(nl);
		return p;
	}

	public static Project createSource5Beam1() {
		Project p = new Project("Source5 Beam1");
		p.addBar(0, 0, 11, 0);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(11, 0).setSupport(Support.ROLLER);
		
		nl = new NodeLoad.Builder().y(3).build();
		p.addNode(2, 0);
		p.getNode(2, 0).addLoad(nl);
		nl = new NodeLoad.Builder().y(-5).build();
		p.addNode(5, 0);
		p.getNode(5, 0).addLoad(nl);
		return p;
	}

	public static Project createSource5Beam2() {
		Project p = new Project("Source5 Beam2");
		p.addBar(0, 0, 10, 0);
		p.getNode(0, 0).setSupport(Support.FIXED);
		
		bl = new BarLoad.Builder().y(-3).build();
		p.getBar(5, 0).divide(0.3);
		p.getBar(5, 0).divide(5.0 / 7);
		p.getBar(5, 0).addLoad(bl);
		nl = new NodeLoad.Builder().x(-10).build();
		p.getNode(10, 0).addLoad(nl);
		return p;
	}

	public static Project createSource5Beam3() {
		Project p = new Project("Source5 Beam3");
		p.addBar(0, 0, 2, 0);
		p.addBar(2, 0, 10, 0);
		p.addBar(10, 0, 17, 0);
		p.getNode(0, 0).setSupport(Support.FIXED);
		p.getBar(6, 0).setStartingNodeReleased(true);
		p.getBar(6, 0).setEndingNodeReleased(true);
		p.addNode(6, 0);
		p.getNode(6, 0).setSupport(Support.ROLLER);
		p.getNode(17, 0).setSupport(Support.ROLLER);
		
		nl = new NodeLoad.Builder().x(15).build();
		p.getNode(17, 0).addLoad(nl);
		nl = new NodeLoad.Builder().y(-8).build();
		p.getNode(10, 0).addLoad(nl);
		nl = new NodeLoad.Builder().moment(-6).build();
		p.getNode(6, 0).addLoad(nl);
		return p;
	}

	public static Project createSource5Frame4() {
		Project p = new Project("Source5 Frame4");
		p.addBar(0, 0, 0, 3);
		p.addBar(0, 3, 5, 3);
		p.addBar(5, 3, 8, 3);
		p.addBar(8, 3, 8, 1);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(8, 1).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().y(-2).build();
		p.getBar(2, 3).addLoad(bl);
		nl = new NodeLoad.Builder().x(8).build();
		p.addNode(0, 2);
		p.getNode(0, 2).addLoad(nl);
		nl = new NodeLoad.Builder().y(-5).build();
		p.getNode(8, 3).addLoad(nl);
		return p;
	}

	public static Project createSource6Frame1() {
		Project p = new Project("Source6 Frame1");
		p.addBar(0, 0, 0, 6);
		p.addBar(0, 3, 4, 3);
		p.addBar(4, 3, 4, 0);
		p.getNode(0, 0).setSupport(Support.ROLLER);
		p.getNode(4, 0).setSupport(Support.HINGED);
		
		bl = new BarLoad.Builder().y(-5).build();
		p.getBar(2, 3).addLoad(bl);
		nl = new NodeLoad.Builder().moment(10).build();
		p.getNode(0, 3).addLoad(nl);
		nl = new NodeLoad.Builder().x(10).build();
		p.addNode(0, 1);
		p.getNode(0, 1).addLoad(nl);
		return p;
	}

	public static Project createSource7Beam1() {
		Project p = new Project("Source7 Beam1");
		p.addBar(0, 0, 7, 0);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(7, 0).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().y(-2).build();
		p.getBar(3, 0).divide(3.0 / 7);
		p.getBar(4, 0).addLoad(bl);
		nl = new NodeLoad.Builder().y(-10).build();
		p.addNode(1, 0);
		p.getNode(1, 0).addLoad(nl);
		nl = new NodeLoad.Builder().x(20).y(20).build();
		p.getNode(3, 0).addLoad(nl);
		return p;
	}

	public static Project createSource7Beam2() {
		Project p = new Project("Source7 Beam2");
		p.addBar(0, 0, 14, 0);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.addNode(10, 0);
		p.getNode(10, 0).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().y(-10).build();
		p.getBar(7, 0).divide(4.0 / 14);
		p.getBar(7, 0).divide(0.6);
		p.getBar(2, 0).addLoad(bl);
		p.getBar(12, 0).addLoad(bl);
		nl = new NodeLoad.Builder().y(-10).build();
		p.addNode(6, 0);
		p.getNode(6, 0).addLoad(nl);
		nl = new NodeLoad.Builder().moment(60).build();
		p.addNode(8, 0);
		p.getNode(8, 0).addLoad(nl);
		nl = new NodeLoad.Builder().x(-20).build();
		p.getNode(14, 0).addLoad(nl);
		return p;
	}

	public static Project createSource7Beam3() {
		Project p = new Project("Source7 Beam3");
		p.addBar(0, 0, 6, 0);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.addNode(4, 0);
		p.getNode(4, 0).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().y(-20).build();
		p.getBar(3, 0).divide(2.0 / 6);
		p.getBar(3, 0).addLoad(bl);
		nl = new NodeLoad.Builder().x(-20).y(20).build();
		p.getNode(2, 0).addLoad(nl);
		return p;
	}

	public static Project createSource7Beam4() {
		Project p = new Project("Source7 Beam4");
		p.addBar(0, 0, 8, 0);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.addNode(5, 0);
		p.getNode(5, 0).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().y1(0).y2(-20).build();
		p.getBar(3, 0).divide(3.0 / 8);
		p.getBar(2, 0).addLoad(bl);
		bl = new BarLoad.Builder().y1(-10).y2(0).build();
		p.getBar(5, 0).divide(2.0 / 5);
		p.getBar(6, 0).addLoad(bl);
		return p;
	}

	public static Project createSource7Beam5() {
		Project p = new Project("Source7 Beam5");
		p.addBar(0, 0, 2, 0);
		p.addBar(2, 0, 4, 0);
		p.addBar(4, 0, 8, 0);
		p.addBar(8, 0, 12, 0);
		p.getNode(0, 0).setSupport(Support.FIXED);
		p.addNode(6, 0);
		p.getNode(6, 0).setSupport(Support.ROLLER);
		p.addNode(10, 0);
		p.getNode(10, 0).setSupport(Support.ROLLER);
		p.getNode(12, 0).setSupport(Support.ROLLER);
		p.getBar(3, 0).setStartingNodeReleased(true);
		p.getBar(3, 0).setEndingNodeReleased(true);
		p.getBar(6, 0).setEndingNodeReleased(true);
		
		bl = new BarLoad.Builder().y(-10).build();
		p.getBar(3, 0).addLoad(bl);
		nl = new NodeLoad.Builder().moment(40).build();
		p.getNode(8, 0).addLoad(nl);
		nl = new NodeLoad.Builder().y(-60).build();
		p.addNode(11, 0);
		p.getNode(11, 0).addLoad(nl);
		return p;
	}

	public static Project createSource7Beam6() {
		Project p = new Project("Source7 Beam6");
		p.addBar(0, 0, 4, 0);
		p.addBar(4, 0, 8, 0);
		p.addBar(8, 0, 11, 0);
		p.getNode(0, 0).setSupport(Support.ROLLER);
		p.addNode(2, 0);
		p.getNode(2, 0).setSupport(Support.ROLLER);
		p.getNode(11, 0).setSupport(Support.FIXED);
		p.getBar(6, 0).setStartingNodeReleased(true);
		p.getBar(6, 0).setEndingNodeReleased(true);
		
		bl = new BarLoad.Builder().y(-10).build();
		p.getBar(2, 0).divide(0.5);
		p.getBar(1, 0).addLoad(bl);
		bl = new BarLoad.Builder().y2(-20).build();
		p.getBar(10, 0).addLoad(bl);
		nl = new NodeLoad.Builder().moment(15).build();
		p.getNode(4, 0).addLoad(nl);
		p.addNode(6, 0);
		nl = new NodeLoad.Builder().y(-20).build();
		p.getNode(6, 0).addLoad(nl);
		return p;
	}

	public static Project createSource7Frame7() {
		Project p = new Project("Source7 Frame7");
		p.addBar(0, 0, 4, 3);
		p.getNode(0, 0).setSupport(Support.HINGED);
		p.getNode(4, 3).setSupport(Support.ROLLER);
		
		bl = new BarLoad.Builder().x(10).projected().build();
		p.getBar(2, 1.5).addLoad(bl);
		return p;
	}

	public static Project createSource7Frame8() {
		Project p = new Project("Source7 Frame8");
		p.addBar(0, 4, 4, 4);
		p.addBar(4, 0, 4, 6);
		p.addBar(4, 0, 8, 0);
		p.getNode(0, 4).setSupport(Support.ROLLER);
		p.getNode(8, 0).setSupport(Support.HINGED);
		
		bl = new BarLoad.Builder().y(-20).build();
		p.getBar(2, 4).divide(0.5);
		p.getBar(1, 4).addLoad(bl);
		nl = new NodeLoad.Builder().x(-50).build();
		p.getNode(4, 6).addLoad(nl);
		nl = new NodeLoad.Builder().y(-25).build();
		p.addNode(6, 0);
		p.getNode(6, 0).addLoad(nl);
		nl = new NodeLoad.Builder().moment(30).build();
		p.addNode(4, 2);
		p.getNode(4, 2).addLoad(nl);
		return p;
	}

	public static Project createSource7Frame9() {
		Project p = new Project("Source7 Frame9");
		p.addBar(0, 0, 0, 4);
		p.addBar(0, 2, 4, 2);
		p.addBar(4, 2, 6, 0);
		p.getNode(6, 0).setSupport(Support.HINGED);
		p.getNode(0, 4).setSupport(Support.ROLLER, 90);
		
		bl = new BarLoad.Builder().x(20).build();
		p.getBar(0, 1).divide(0.5);
		p.getBar(0, 1).addLoad(bl);
		bl = new BarLoad.Builder().x(-10).projected().build();
		p.getBar(5, 1).addLoad(bl);
		nl = new NodeLoad.Builder().moment(50).build();
		p.getNode(4, 2).addLoad(nl);
		nl = new NodeLoad.Builder().y(-40).build();
		p.addNode(2, 2);
		p.getNode(2, 2).addLoad(nl);
		return p;
	}

	public static Project createSource7Frame10() {
		Project p = new Project("Source7 Frame10");
		p.addBar(0, 0, 3, 4);
		p.addBar(3, 4, 6, 4);
		p.addBar(6, 2, 6, 8);
		double proportion = 3.0 / 4.0;
		double angle = Math.atan(proportion);
		p.getNode(0, 0).setSupport(Support.FIXED, angle);
		p.getNode(6, 2).setSupport(Support.ROLLER);
		p.getBar(5, 4).setStartingNodeReleased(true);
		
		bl = new BarLoad.Builder().x(-20).build();
		p.getBar(6, 3).addLoad(bl);
		bl = new BarLoad.Builder().y(-20).projected().build();
		p.getBar(1.5, 2).addLoad(bl);
		nl = new NodeLoad.Builder().moment(-10).build();
		p.getNode(6, 8).addLoad(nl);
		nl = new NodeLoad.Builder().y(-25).build();
		p.addNode(5, 4);
		p.getNode(5, 4).addLoad(nl);
		return p;
	}

	public static Project createSource7Frame11() {
		Project p = new Project("Source7 Frame11");
		p.addBar(0, 6, 8, 6);
		p.addBar(8, 6, 12, 9);
		p.addBar(8, 6, 8, 3);
		p.addBar(8, 3, 12, 0);
		p.addBar(12, 0, 15, 3);
		p.addBar(15, 3, 18, 3);
		p.getNode(0, 6).setSupport(Support.FIXED);
		p.getNode(18, 3).setSupport(Support.ROLLER);
		p.getBar(8, 5).setStartingNodeReleased(true);
		
		bl = new BarLoad.Builder().x(20).projected().build();
		p.getBar(8, 4).addLoad(bl);
		p.getBar(10, 1.5).addLoad(bl);
		bl = new BarLoad.Builder().y(-10).projected().build();
		p.getBar(10, 7.5).addLoad(bl);
		nl = new NodeLoad.Builder().moment(-60).build();
		p.getNode(12, 0).addLoad(nl);
		nl = new NodeLoad.Builder().y(-100).build();
		p.addNode(4, 6);
		p.getNode(4, 6).addLoad(nl);
		return p;
	}
}
