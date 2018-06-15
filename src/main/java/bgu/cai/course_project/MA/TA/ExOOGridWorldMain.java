package bgu.cai.course_project.MA.TA;

/**
 * @author James MacGlashan.
 */
public class ExOOGridWorldMain {


	public static void main(String [] args){

		TARunner example = new TARunner();

		String outputPath = "./results/"; //directory to record results
		
		String DeterministicOutputPath = outputPath+"Deterministic/"; //directory to record results
		String StochasticOutputPath = outputPath+"Stochastic/"; //directory to record results

		//example.BFSExample(DeterministicOutputPath);
		//example.DFSExample(DeterministicOutputPath);
		//example.AStarExample(DeterministicOutputPath);
		//example.ValueIterationExample(StochasticOutputPath);
		//example.QLearningExample(StochasticOutputPath);
		//example.SarsaLearningExample(StochasticOutputPath);
		
		//example.visualizeActionObserver(DeterministicOutputPath);
		example.visualizeActionObserver(StochasticOutputPath);

		//example.visualizeExplorer(true);
	}


}
