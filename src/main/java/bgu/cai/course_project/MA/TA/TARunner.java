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

		exp.addKeyAction("w", ACTION_NORTH_WAIT, "");
		exp.addKeyAction("s", ACTION_SOUTH_WAIT, "");
		exp.addKeyAction("d", ACTION_EAST_WAIT, "");
		exp.addKeyAction("a", ACTION_WEST_WAIT, "");
		exp.addKeyAction("i", ACTION_WAIT_NORTH, "");
		exp.addKeyAction("k", ACTION_WAIT_SOUTH, "");
		exp.addKeyAction("l", ACTION_WAIT_EAST, "");
		exp.addKeyAction("j", ACTION_WAIT_WEST, "");
		exp.addKeyAction(" ", ACTION_WAIT_WAIT, "");

		exp.initGUI();
	}	


	/**
	 * running BFS algorithm
	 * @return policy (plan) after running BFS
	 */
	public Policy GetBFSPolicy(){

		gwdg.setDeterministicTransitionFactoredModel();

		DeterministicPlanner planner = new BFS(domain, goalCondition, hashingFactory);

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 

		Policy p = planner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}

	/**
	 * running DFS algorithm
	 * @return policy (plan) after running DFS
	 */
	public Policy GetDFSPolicy(){

		gwdg.setDeterministicTransitionFactoredModel();

		DeterministicPlanner planner = new DFS(domain, goalCondition, hashingFactory);

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 

		Policy p = planner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}

	/**
	 * running A* algorithm
	 * @param mapHeight for heuristic
	 * @param mapWidth for heuristic
	 * @return policy (plan) after running A*
	 */
	public Policy GetAStar(final int mapHeight, final int mapWidth){

		gwdg.setDeterministicTransitionFactoredModel();

		Heuristic mdistHeuristic = new Heuristic() {

			public double h(State s) {
				ExGridAgent a = ((ExGridWorldState)s).agent;
				double mdist = Math.abs(a.x1-mapWidth) + Math.abs(a.y1-mapHeight);

				return -mdist;
			}
		};

		DeterministicPlanner planner = new AStar(domain, goalCondition, hashingFactory,
				mdistHeuristic);

		long startTime = System.nanoTime(); 

		Policy p = planner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}	

	/**
	 * running Value Iteration algorithm
	 * @param MaxDelta
	 * @param MaxIterations
	 * @return policy s after running Value Iteration
	 */
	public Policy GetValueIterationPolicy(double MaxDelta, int MaxIterations){

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		Planner planner = new ValueIteration(domain, 0.99, hashingFactory, MaxDelta, MaxIterations);

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 
		System.out.println("Start Time : " + startTime);

		Policy p = planner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}

	/**
	 * running QLearning algorithm
	 * @param NumOfEpisodes
	 * @return policy  after running QLearning
	 */
	public Policy GetQLearningPolicy(int NumOfEpisodes){

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		QLearning lerner = new QLearning(domain, 0.99, hashingFactory, 0., 1.);

		lerner.setLearningPolicy(new EpsilonGreedy(lerner,0.1));
		lerner.setLearningRateFunction(new ConstantLR(0.5));

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 
		System.out.println("Start Time : " + startTime);

		//run learning for NumOfEpisodes
		for(int i = 0; i < NumOfEpisodes; i++){

			lerner.runLearningEpisode(env);

			//reset environment for next learning episode
			env.resetEnvironment();
		}

		Policy p = lerner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}	

	/**
	 * running Sarsa algorithm
	 * @param NumOfEpisodes
	 * @return policy (plan) after running Sarsa
	 */
	public Policy GetSarsaLearningPolicy(int NumOfEpisodes) {

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		SarsaLam lerner = new SarsaLam(domain, 0.99, hashingFactory, 0., 0.5, 0.3);

		lerner.setLearningPolicy(new EpsilonGreedy(lerner,0.1));
		lerner.setLearningRateFunction(new ConstantLR(0.5));

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 
		System.out.println("Start Time : " + startTime);

		//run learning for NumOfEpisodes
		for(int i = 0; i < NumOfEpisodes; i++){

			lerner.runLearningEpisode(env);

			//reset environment for next learning episode
			env.resetEnvironment();
		}

		Policy p = lerner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}

	/**
	 * running UCT algorithm
	 * @return policy after running UCT
	 */
	public Policy GetUCTPolicy() {

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 
		System.out.println("Start Time : " + startTime);

		Planner planner = new UCT(domain, 0.99, hashingFactory, 50, 10000, 20);
		Policy p = planner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}

	/**
	 * running RTDP algorithm
	 * @param numRollouts
	 * @param maxDelta
	 * @param maxDepth
	 * @return policy after running RTDP
	 */
	public Policy GetRTDPPolicy(int numRollouts, double maxDelta, int maxDepth) {

		gwdg.setProbSucceedTransitionFactoredModel(0.8);

		DPrint.toggleUniversal(false);

		long startTime = System.nanoTime(); 
		System.out.println("Start Time : " + startTime);

		Planner planner = new RTDP(domain, 0.99, hashingFactory, -100., numRollouts, maxDelta, maxDepth);
		Policy p = planner.planFromState(initialState);

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Done Time : " + estimatedTime);

		return p;
	}

	/**
	 * rollouts the given policy #numOfIterations times and save the best one in the given path
	 * @param p
	 * @param numOfIterations
	 * @param outputPath
	 * @param Ename
	 */
	public void RunPolicy(Policy p , int numOfIterations, String outputPath, String Ename) {

		int sumNumOfSteps = 0;

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
			}

			System.out.println("End rollout - " + (i+1));

			System.out.println("Avg Num Time Steps (iter "+ (i+1) +"): " + sumNumOfSteps/(i+1));
		}

		System.out.println("Avg Num Time Steps: " + sumNumOfSteps/numOfIterations);

		minE.write(outputPath + Ename + "_"+min );
	}
}
