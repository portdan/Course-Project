package bgu.cai.course_project.TwoAgent;

import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.shell.visual.VisualExplorer;
import burlap.visualizer.Visualizer;

import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.ACTION_NORTH_NORTH;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.ACTION_SOUTH_SOUTH;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.ACTION_EAST_EAST;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.ACTION_WEST_WEST;

public class TwoAgentMain {

	public static void main(String[] args) {

		TwoAgentGridWorld gen = new TwoAgentGridWorld();

		gen.setGoalLocation(0, 10, 10, 10);

		SADomain domain = (SADomain) gen.generateDomain();

		State initialState = new TwoAgentGridState(0, 0, 10, 0);

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
