package bgu.cai.course_project.tutorial.oo;

import burlap.mdp.core.oo.state.generic.GenericOOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.shell.visual.VisualExplorer;
import burlap.visualizer.*;

import static bgu.cai.course_project.tutorial.oo.ExampleOOGridWorld.ACTION_NORTH;
import static bgu.cai.course_project.tutorial.oo.ExampleOOGridWorld.ACTION_SOUTH;
import static bgu.cai.course_project.tutorial.oo.ExampleOOGridWorld.ACTION_EAST;
import static bgu.cai.course_project.tutorial.oo.ExampleOOGridWorld.ACTION_WEST;

/**
 * @author James MacGlashan.
 */
public class ExampleOOGridWorldMain {


	public static void main(String [] args){

		ExampleOOGridWorld gen = new ExampleOOGridWorld();
		OOSADomain domain = gen.generateDomain();
		State initialState = new GenericOOState(new ExGridAgent(0, 0), new EXGridLocation(10, 10, "loc0"));
		SimulatedEnvironment env = new SimulatedEnvironment(domain, initialState);

		Visualizer v = gen.getVisualizer();
		VisualExplorer exp = new VisualExplorer(domain, env, v);

		exp.addKeyAction("w", ACTION_NORTH, "");
		exp.addKeyAction("s", ACTION_SOUTH, "");
		exp.addKeyAction("d", ACTION_EAST, "");
		exp.addKeyAction("a", ACTION_WEST, "");

		exp.initGUI();


	}


}
