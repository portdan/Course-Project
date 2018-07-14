package bgu.cai.course_project.SA;

import bgu.cai.course_project.SA.SARunner;
import burlap.behavior.policy.Policy;

/**
 * @author James MacGlashan.
 */
public class ExOOGridWorldMain {


	public static void main(String [] args){

		int MapNum = 2;

		SARunner example = new SARunner(MapNum);

		String outputPath = "./results/SA/Map"+MapNum+"/"; //directory to record results

		String DeterministicOutputPath = outputPath + "Deterministic/"; //directory to record results
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

		//example.ValueIterationExample(StochasticOutputPathVI);
		//example.visualizeActionObserver(StochasticOutputPathVI);

		//example.QLearningExample(StochasticOutputPathQ);
		//example.visualizeActionObserver(StochasticOutputPathQ);

		//example.SarsaLearningExample(StochasticOutputPathSarsa);
		//example.visualizeActionObserver(StochasticOutputPathSarsa);

		//example.RunPolicy(example.GetUCTPolicy(), 1, StochasticOutputPathUCT,"uct");
		//example.visualizeActionObserver(StochasticOutputPathUCT);

		Policy p = example.GetRTDPTPolicy();	
		example.RunPolicy(p, 100, StochasticOutputPathRTDP,"rtdp");
		example.visualizeActionObserver(StochasticOutputPathRTDP);

		//example.visualizeExplorer(true);

	}
}
