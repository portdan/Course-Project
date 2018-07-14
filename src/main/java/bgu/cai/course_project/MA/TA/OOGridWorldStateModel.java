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

		List <StateTransitionProb> transitions = new ArrayList<StateTransitionProb>();

		int [] actionDirections = actionDirection(a.actionName());

		// both wait
		if(actionDirections[0] == -1 && actionDirections[0] == -1){

			State ns = s.copy();
			int [] dcomps = actionDirectionDeltaFromIndex(-1,-1);
			ns = actionMoveResult(ns, dcomps);

			StateTransitionProb tp = new StateTransitionProb(ns, 1.0);
			transitions.add(tp);
		}
		else if(actionDirections[1] == -1) {

			double [] direction1Probs = transitionDynamics[actionDirections[0]];

			for(int i = 0; i < direction1Probs.length; i++){

				double p = direction1Probs[i];

				if(p == 0.)
					continue; //cannot transition in this direction

				State ns = s.copy();
				int [] dcomps = actionDirectionDeltaFromIndex(i,-1);
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
		}
		else if(actionDirections[0] == -1) {

			double [] direction2Probs = transitionDynamics[actionDirections[1]];

			for(int j = 0; j < direction2Probs.length; j++){

				double p = direction2Probs[j];

				if(p == 0.)
					continue; //cannot transition in this direction

				State ns = s.copy();
				int [] dcomps = actionDirectionDeltaFromIndex(-1,j);
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
		}
		else {
			double [] direction1Probs = transitionDynamics[actionDirections[0]];
			double [] direction2Probs = transitionDynamics[actionDirections[1]];


			for(int i = 0; i < direction1Probs.length; i++){
				for(int j = 0; j < direction2Probs.length; j++){

					double p = direction1Probs[i]*direction2Probs[j];

					if(p == 0.)
						continue; //cannot transition in this direction

					State ns = s.copy();
					int [] dcomps = actionDirectionDeltaFromIndex(i,j);
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
			}
		}
		
		return transitions;
	}

	public State sample(State s, Action a) {

		double roll = 0;
		double curSum = 0.;

		s = s.copy();

		int[] dir = {-1,-1};

		int [] actionDirections = actionDirection(a.actionName());

		if(actionDirections[0] != -1){

			double [] direction1Probs = transitionDynamics[actionDirections[0]];

			curSum = 0.;
			roll = rand.nextDouble();

			for(int i = 0; i < direction1Probs.length; i++){
				curSum += direction1Probs[i];
				if(roll < curSum){
					dir[0] = i;
					break;
				}
			}
		}

		if(actionDirections[1] != -1){

			double [] direction2Probs = transitionDynamics[actionDirections[1]];

			curSum = 0.;
			roll = rand.nextDouble();

			for(int i = 0; i < direction2Probs.length; i++){
				curSum += direction2Probs[i];
				if(roll < curSum){
					dir[1] = i;
					break;
				}
			}
		}

		int [] dcomps = actionDirectionDeltaFromIndex(dir[0],dir[1]);

		return actionMoveResult(s, dcomps);
	}

	protected int [] actionDirection(String actionName){

		if(actionName.equals(ACTION_NORTH_NORTH))			{return new int[]	{0,0};}
		else if(actionName.equals(ACTION_SOUTH_NORTH))		{return new int[]	{1,0};}
		else if(actionName.equals(ACTION_EAST_NORTH))		{return new int[]	{2,0};}
		else if(actionName.equals(ACTION_WEST_NORTH))		{return new int[]	{3,0};}
		else if(actionName.equals(ACTION_WAIT_NORTH))		{return new int[]	{-1,0};}
		else if(actionName.equals(ACTION_NORTH_SOUTH))		{return new int[]	{0,1};}
		else if(actionName.equals(ACTION_SOUTH_SOUTH))		{return new int[]	{1,1};}
		else if(actionName.equals(ACTION_EAST_SOUTH))		{return new int[]	{2,1};}
		else if(actionName.equals(ACTION_WEST_SOUTH))		{return new int[]	{3,1};}
		else if(actionName.equals(ACTION_WAIT_SOUTH))		{return new int[]	{-1,1};}
		else if(actionName.equals(ACTION_NORTH_EAST))		{return new int[]	{0,2};}
		else if(actionName.equals(ACTION_SOUTH_EAST))		{return new int[]	{1,2};}
		else if(actionName.equals(ACTION_EAST_EAST))		{return new int[]	{2,2};}
		else if(actionName.equals(ACTION_WEST_EAST))		{return new int[]	{3,2};}
		else if(actionName.equals(ACTION_WAIT_EAST))		{return new int[]	{-1,2};}
		else if(actionName.equals(ACTION_NORTH_WEST))		{return new int[]	{0,3};}
		else if(actionName.equals(ACTION_SOUTH_WEST))		{return new int[]	{1,3};}
		else if(actionName.equals(ACTION_EAST_WEST))		{return new int[]	{2,3};}
		else if(actionName.equals(ACTION_WEST_WEST))		{return new int[]	{3,3};}
		else if(actionName.equals(ACTION_WAIT_WEST))		{return new int[]	{-1,3};}
		else if(actionName.equals(ACTION_NORTH_WAIT))		{return new int[]	{0,-1};}
		else if(actionName.equals(ACTION_SOUTH_WAIT))		{return new int[]	{1,-1};}
		else if(actionName.equals(ACTION_EAST_WAIT))		{return new int[]	{2,-1};}
		else if(actionName.equals(ACTION_WEST_WAIT))		{return new int[]	{3,-1};}
		else if(actionName.equals(ACTION_WAIT_WAIT))		{return new int[]	{-1,-1};}

		throw new RuntimeException("Unknown action " + actionName);
	}

	protected int [] actionDirectionDeltaFromIndex(int i, int j){

		int [] result = new int[4];

		switch (i) {
		// wait
		case -1: result[0] = 0;result[1] = 0;	break;
		case 0:	result[0] = 0;result[1] = 1;	break;
		case 1:	result[0] = 0;result[1] = -1;	break;
		case 2:	result[0] = 1;result[1] = 0;	break;
		case 3:	result[0] = -1;result[1] = 0;	break;
		default:
			break;
		}
		switch (j) {
		// wait
		case -1: result[2] = 0;result[3] = 0;	break;
		case 0:	result[2] = 0;result[3] = 1;	break;
		case 1:	result[2] = 0;result[3] = -1;	break;
		case 2:	result[2] = 1;result[3] = 0;	break;
		case 3:	result[2] = -1;result[3] = 0;	break;
		default:
			break;
		}


		return result;
	}

	protected State actionMoveResult(State s, int[] direction){

		ExGridWorldState gws = (ExGridWorldState)s;

		int ax1 = gws.agent.x1;
		int ay1 = gws.agent.y1;
		int ax2 = gws.agent.x2;
		int ay2 = gws.agent.y2;

		int nx1 = ax1 + direction[0];
		int ny1 = ay1 + direction[1];
		int nx2 = ax2 + direction[2];
		int ny2 = ay2 + direction[3];

		int width = this.map.length;
		int height = this.map[0].length;

		//make sure agent 1 new position is valid (not a wall or off bounds)
		if(nx1 < 0 || nx1 >= width || ny1 < 0 || ny1 >= height ||
				map[nx1][ny1] == 1){
			nx1 = ax1;
			ny1 = ay1;
		}

		//make sure agent 2 new position is valid (not a wall or off bounds)
		if(nx2 < 0 || nx2 >= width || ny2 < 0 || ny2 >= height ||
				map[nx2][ny2] == 1){
			nx2 = ax2;
			ny2 = ay2;
		}

		//make sure no collision between agents
		if(nx1 == nx2 && ny1 == ny2) {
			nx1 = ax1;
			ny1 = ay1;
			nx2 = ax2;
			ny2 = ay2;
		}

		//make sure no swapping between agents
		if(nx1 == ax2 && ny1 == ay2 && nx2 == ax1 && ny2 == ay1) {
			nx1 = ax1;
			ny1 = ay1;
			nx2 = ax2;
			ny2 = ay2;
		}

		ExGridAgent nagent = gws.touchAgent();
		nagent.x1 = nx1;
		nagent.y1 = ny1;
		nagent.x2 = nx2;
		nagent.y2 = ny2;

		return s;
	}
}