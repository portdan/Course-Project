package bgu.cai.course_project.MA.TA;

import burlap.behavior.policy.Policy;

/**
 * @author James MacGlashan.
 */
public class ExOOGridWorldMain {


	public static void main(String [] args){

		int MapNum = 2;

		TARunner example = new TARunner(MapNum);

		String outputPath = "./results/TA/Map"+MapNum+"/"; //directory to record results

		String DeterministicOutputPath = outputPath + "Deterministic/";	//directory to record results
		String StochasticOutputPath = outputPath + "Stochastic/"; //directory to record results
		String StochasticOutputPathVI = StochasticOutputPath + "VI/"; //directory to record results
		String StochasticOutputPathQ = StochasticOutputPath + "QLearning/"; //directory to record results
		String StochasticOutputPathSarsa = StochasticOutputPath + "Sarsa/"; //directory to record results
		String StochasticOutputPathUCT = StochasticOutputPath + "UCT/"; //directory to record results
		String StochasticOutputPathRTDP = StochasticOutputPath + "RTDP/"; //directory to record results

		//example.BFSExample(DeterministicOutputPath);
		//example.visualizeActionObserver(DeterministicOutputPath);

		//example.DFSExample(DeterministicOutputPath);
		//example.visualizeActionObserver(DeterministicOutputPath);

		//example.AStarExample(DeterministicOutputPath);
		//example.visualizeActionObserver(DeterministicOutputPath);

		//Policy p = example.GetValueIterationPolicy(0.000000001, 20);
		//example.RunPolicy(p, 10, StochasticOutputPathVI,"vi");
		//example.visualizeActionObserver(StochasticOutputPathVI);

		//Policy p = example.GetQLearningPolicy(200000);
		//example.RunPolicy(p, 10, StochasticOutputPathQ,"ql");
		//example.visualizeActionObserver(StochasticOutputPathQ);

		//example.SarsaLearningExample(StochasticOutputPathSarsa);
		//example.visualizeActionObserver(StochasticOutputPathSarsa);

		//example.RunPolicy(example.GetUCTPolicy(), 1, StochasticOutputPathUCT,"uct");
		//example.visualizeActionObserver(StochasticOutputPathUCT);

		//Policy p = example.GetRTDPPolicy(10000000, 0.000000001, 1000);
		//example.RunPolicy(p, 10, StochasticOutputPathRTDP,"rtdp");
		//example.visualizeActionObserver(StochasticOutputPathRTDP);

		//example.visualizeExplorer(false);

	}
}
