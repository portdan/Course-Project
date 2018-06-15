package bgu.cai.course_project.OOTwoAgent;

import burlap.mdp.core.oo.state.generic.GenericOOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.shell.visual.VisualExplorer;
import burlap.visualizer.Visualizer;

import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_NORTH_NORTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_SOUTH_SOUTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_EAST_EAST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_WEST_WEST;

public class OOTwoAgentMain {

	public static void main(String[] args) {

		OOTwoAgentGridWorld gen = new OOTwoAgentGridWorld();
		OOSADomain domain = gen.generateDomain();
		State initialState = new GenericOOState(new OOTwoAgentGridState(0, 0, 10, 0), 
				new OOTwoAgentGridLocation(0,10, 10, 10, "loc0"));

		SimulatedEnvironment env = new SimulatedEnvironment(domain, initialState);

		Visualizer v = gen.getVisualizer();
		VisualExplorer exp = new VisualExplorer(domain, env, v);

		exp.addKeyAction("w", ACTION_NORTH_NORTH, "");
		exp.addKeyAction("s", ACTION_SOUTH_SOUTH, "");
		exp.addKeyAction("d", ACTION_EAST_EAST, "");
		exp.addKeyAction("a", ACTION_WEST_WEST, "");

		exp.initGUI();

	}
}
