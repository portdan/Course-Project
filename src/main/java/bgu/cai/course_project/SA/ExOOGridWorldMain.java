package bgu.cai.course_project.SA;

import bgu.cai.course_project.SA.SARunner;

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


		long startTime = System.nanoTime(); 
		System.out.println("Start Time : " + startTime);

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

		//example.visualizeExplorer(false);

		long estimatedTime = System.nanoTime() - startTime;
		System.out.println("Done Time : " + estimatedTime);
	}


}
