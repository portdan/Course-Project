package bgu.cai.course_project.MA.TA;

import static bgu.cai.course_project.MA.TA.ExOOGridWorld.*;

import burlap.behavior.learningrate.ConstantLR;
import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.learning.tdmethods.SarsaLam;
import burlap.behavior.singleagent.planning.Planner;
import burlap.behavior.singleagent.planning.deterministic.DeterministicPlanner;
import burlap.behavior.singleagent.planning.deterministic.informed.Heuristic;
import burlap.behavior.singleagent.planning.deterministic.informed.astar.AStar;
import burlap.behavior.singleagent.planning.deterministic.uninformed.bfs.BFS;
import burlap.behavior.singleagent.planning.deterministic.uninformed.dfs.DFS;
import burlap.behavior.singleagent.planning.stochastic.montecarlo.uct.UCT;
import burlap.behavior.singleagent.planning.stochastic.rtdp.RTDP;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.debugtools.DPrint;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.auxiliary.stateconditiontest.TFGoalCondition;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.VisualActionObserver;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.shell.visual.VisualExplorer;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;

public class TARunner {

	ExOOGridWorld gwdg;
	OOSADomain domain;
	State initialState;
	SimulatedEnvironment env;

	StateConditionTest goalCondition;
	HashableStateFactory hashingFactory;


	Visualizer v;
	VisualExplorer exp;

	public TARunner(int mapNum) {

		gwdg = new ExOOGridWorld(mapNum);

		domain = gwdg.generateDomain();

		if(mapNum == 1)			// map1
			initialState = new ExGridWorldState(new ExGridAgent(0, 0, 10, 0), new ExGridLocation(10, 10, 0, 10, "loc0"));
		else if (mapNum == 2)	//map2
			initialState = new ExGridWorldState(new ExGridAgent(0, 0, 14, 0), new ExGridLocation(14, 14, 0, 14, "loc0"));

		hashingFactory = new SimpleHashableStateFactory();

		goalCondition = new TFGoalCondition(gwdg.getTf());

		env = new SimulatedEnvironment(domain, initialState);
	}

	public void visualizeActionObserver(String outputPath){

		VisualActionObserver observer = new VisualActionObserver(domain, gwdg.getVisualizer());

		observer.initGUI();

		env.addObservers(observer);

		Visualizer v = gwdg.getVisualizer();
		new EpisodeSequenceVisualizer(v, domain, outputPath);
	}

	public void visualizeExplorer(boolean isDeterministic) {

		if(isDeterministic)
			gwdg.setDeterministicTransitionFactoredModel();
		else
			gwdg.setProbSucceedTransitionFactoredModel(0.8);

		Visualizer v = gwdg.getVisualizer();

		VisualExplorer exp = new VisualExplorer(domain, env, v);

		exp.addKeyAction("w", ACTION_NORTH_NORTH, "");
		exp.addKeyAction("s", ACTION_SOUTH_SOUTH, "");
		exp.addKeyAction("d", ACTION_EAST_EAST, "");
		exp.addKeyAction("a", ACTION_WEST_WEST, "");

		exp.initGUI();
	}	


	public void BFSExample(String outputPath){

		gwdg.setDeterministicTransitionFactoredModel();

		DeterministicPlanner planner = new BFS(domain, goalCondition, hashingFactory);
		Policy p = planner.planFromState(initialState);
		PolicyUtils.rollout(p, initialState, domain.getModel()).write(outputPath + "bfs");

	}

	public void DFSExample(String outputPath){

		gwdg.setDeterministicTransitionFactoredModel();

		DeterministicPlanner planner = new DFS(domain, goalCondition, hashingFactory);
		Policy p = planner.planFromState(initialState);
		PolicyUtils.rollout(p, initialState, domain.getModel()).write(outputPath + "dfs");

	}

	public void AStarExample(String outputPath){

		gwdg.setDeterministicTransitionFactoredModel();

		Heuristic mdistHeuristic = new Heuristic() {

			public double h(State s) {
				ExGridAgent a = ((ExGridWorldState)s).agent;
				double mdist = Math.abs(a.x1-10) + Math.abs(a.y1-10);

				return -mdist;
			}
		};

		DeterministicPlanner planner = new AStar(domain, goalCondition, hashingFactory,
				mdistHeuristic);

		Policy p = planner.planFromState(initialState);
		PolicyUtils.rollout(p, initialState, domain.getModel()).write(outputPath + "astar");
	}	

	public void ValueIterationExample(String outputPath){

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		Planner planner = new ValueIteration(domain, 0.99, hashingFactory, 0.001, 100);
		//Planner planner = new ValueIteration(domain, 0.99, hashingFactory, 0.000000001, 100);

		Policy p = planner.planFromState(initialState);

		Episode fin = PolicyUtils.rollout(p, initialState, domain.getModel());

		System.out.println("Num Time Steps: " + fin.numTimeSteps());

		fin.write(outputPath + "vi");
	}

	public void QLearningExample(String outputPath){

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		QLearning agent = new QLearning(domain, 0.99, hashingFactory, 0., 1.);

		agent.setLearningPolicy(new EpsilonGreedy(agent,0.1));
		agent.setLearningRateFunction(new ConstantLR(0.5));

		int minInd = 0;
		int min = Integer.MAX_VALUE;
		Episode minE = null;

		//run learning for 1000-100000 episodes
		for(int i = 0; i < 1000; i++){

			Episode e = agent.runLearningEpisode(env);

			//e.write(outputPath + "ql_" + i);
			//System.out.println(i + ": " + e.maxTimeStep());

			if( min > e.maxTimeStep()) {
				min = e.maxTimeStep();	
				minE = e.copy();
				minInd = i;
			}

			//System.out.println(i);

			//reset environment for next learning episode
			env.resetEnvironment();
		}

		//minE.write(outputPath + "ql_min"+minInd);

		Policy p = agent.planFromState(initialState);

		Episode fin = PolicyUtils.rollout(p, initialState, domain.getModel());

		System.out.println("Num Time Steps: " + fin.numTimeSteps());

		fin.write(outputPath + "Final");

	}	

	public void SarsaLearningExample(String outputPath) {

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		SarsaLam agent = new SarsaLam(domain, 0.99, hashingFactory, 0., 0.5, 0.3);

		agent.setLearningPolicy(new EpsilonGreedy(agent,0.1));
		agent.setLearningRateFunction(new ConstantLR(0.5));

		int minInd = 0;
		int min = Integer.MAX_VALUE;
		Episode minE = null;

		//run learning for 1000 episodes
		for(int i = 0; i < 1000; i++){

			Episode e = agent.runLearningEpisode(env);

			//e.write(outputPath + "sarsa_" + i);
			//System.out.println(i + ": " + e.maxTimeStep());

			if( min > e.maxTimeStep()) {
				min = e.maxTimeStep();	
				minE = e.copy();
				minInd = i;
			}

			System.out.println(i);	

			//reset environment for next learning episode
			env.resetEnvironment();
		}

		//minE.write(outputPath + "sarsa_min"+minInd);

		Policy p = agent.planFromState(initialState);

		Episode fin = PolicyUtils.rollout(p, initialState, domain.getModel());

		System.out.println("Num Time Steps: " + fin.numTimeSteps());

		fin.write(outputPath + "Final");
	}
	
	public Policy GetUCTPolicy() {

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 

		Planner planner = new UCT(domain, 0.99, hashingFactory, 1000, 50000, 3);
		Policy p = planner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}

	public Policy GetRTDPTPolicy() {

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 

		Planner planner = new RTDP(domain, 0.99, hashingFactory, 0., 1000, 0.001, 1000);
		Policy p = planner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}

	public void RunPolicy(Policy p , int numOfIterations, String outputPath, String Ename) {

		int sumNumOfSteps = 0;

		int minInd = 0;
		int min = Integer.MAX_VALUE;
		Episode minE = null;

		//run policy for 1000 iterations
		for(int i = 0; i < numOfIterations; i++){

			System.out.println("Start rollout - " + (i+1));

			Episode e = PolicyUtils.rollout(p, initialState, domain.getModel());

			sumNumOfSteps += e.numTimeSteps(); 

			if( min > e.numTimeSteps()) {
				min = e.numTimeSteps();	
				minE = e.copy();
				minInd = i;
			}
			
			System.out.println("End rollout - " + (i+1));
		}

		System.out.println("Avg Num Time Steps: " + sumNumOfSteps/numOfIterations);

		minE.write(outputPath + Ename);
	}
}
