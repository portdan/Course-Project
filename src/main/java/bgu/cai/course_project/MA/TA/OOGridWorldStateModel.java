package bgu.cai.course_project.MA.TA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import burlap.debugtools.RandomFactory;
import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;

import static bgu.cai.course_project.MA.TA.ExOOGridWorld.*;

class OOGridWorldStateModel implements FullStateModel {

	protected int [][] map;
	protected double [][] transitionDynamics;
	protected Random rand = RandomFactory.getMapped(0);

	public OOGridWorldStateModel(int [][] map, double[][] transitionDynamics) {
		this.map = map;
		this.transitionDynamics = transitionDynamics;
	}

	public List<StateTransitionProb> stateTransitions(State s, Action a) {

		double [] directionProbs = transitionDynamics[actionDirection(a.actionName())];

		List <StateTransitionProb> transitions = new ArrayList<StateTransitionProb>();

		for(int i = 0; i < directionProbs.length; i++){

			double p = directionProbs[i];

			if(p == 0.)
				continue; //cannot transition in this direction

			State ns = s.copy();
			int [] dcomps = actionDirectionDeltaFromIndex(i);
			ns = actionMoveResult(ns, dcomps);

			//make sure this direction doesn't actually stay in the same place and replicate another no-op
			boolean isNew = true;
			for(StateTransitionProb tp : transitions){
				if(tp.s.equals(ns)){
					isNew = false;
					tp.p += p;
					break;
				}
			}

			if(isNew){
				StateTransitionProb tp = new StateTransitionProb(ns, p);
				transitions.add(tp);
			}
		}

		return transitions;
	}

	public State sample(State s, Action a) {

		s = s.copy();

		double [] directionProbs = transitionDynamics[actionDirection(a.actionName())];

		double roll = rand.nextDouble();
		double curSum = 0.;
		int dir = 0;

		for(int i = 0; i < directionProbs.length; i++){
			curSum += directionProbs[i];
			if(roll < curSum){
				dir = i;
				break;
			}
		}

		int [] dcomps = actionDirectionDeltaFromIndex(dir);

		return actionMoveResult(s, dcomps);
	}

	protected int actionDirection(String actionName){

		if(actionName.equals(ACTION_NORTH_NORTH)){
			return 0;
		}
		else if(actionName.equals(ACTION_SOUTH_SOUTH)){
			return 1;
		}
		else if(actionName.equals(ACTION_EAST_EAST)){
			return 2;
		}
		else if(actionName.equals(ACTION_WEST_WEST)){
			return 3;
		}

		throw new RuntimeException("Unknown action " + actionName);
	}

	protected int [] actionDirectionDeltaFromIndex(int i){

		int [] result = null;

		switch (i) {
		case 0:
			result = new int[]{0,1};
			break;

		case 1:
			result = new int[]{0,-1};
			break;

		case 2:
			result = new int[]{1,0};
			break;

		case 3:
			result = new int[]{-1,0};
			break;

		default:
			break;
		}

		return result;
	}

	protected State actionMoveResult(State s, int[] direction){

		ExGridWorldState gws = (ExGridWorldState)s;

		int ax = gws.agent.x1;
		int ay = gws.agent.y1;

		int nx = ax+direction[0];
		int ny = ay+direction[1];

		int width = this.map.length;
		int height = this.map[0].length;

		//make sure new position is valid (not a wall or off bounds)
		if(nx < 0 || nx >= width || ny < 0 || ny >= height ||
				map[nx][ny] == 1){
			nx = ax;
			ny = ay;
		}

		ExGridAgent nagent = gws.touchAgent();
		nagent.x1 = nx;
		nagent.y1 = ny;

		return s;
	}
}