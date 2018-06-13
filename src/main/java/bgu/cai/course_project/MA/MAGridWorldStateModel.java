package bgu.cai.course_project.MA;

import java.util.List;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;

public class MAGridWorldStateModel implements FullStateModel {

	protected double [][] transitionProbs;

	public MAGridWorldStateModel() {

		this.transitionProbs = new double[4][2];

		for(int i = 0; i < 4; i++){
			transitionProbs[i][0] = 0.8;
			transitionProbs[i][1] = 0.2;
		}
	}

	@Override
	public State sample(State s, Action a) {		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateTransitionProb> stateTransitions(State s, Action a) {
		// TODO Auto-generated method stub
		return null;
	}

}
