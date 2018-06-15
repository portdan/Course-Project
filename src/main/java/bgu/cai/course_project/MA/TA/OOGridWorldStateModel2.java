package bgu.cai.course_project.MA.TA;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.generic.GenericOOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;

import static bgu.cai.course_project.MA.TA.ExOOGridWorld.*;

class OOGridWorldStateModel2 implements FullStateModel {

	protected int [][] map;
	protected double [][] transitionDynamics;

	public OOGridWorldStateModel2(int [][] map, double[][] transitionDynamics) {
		this.map = map;
		this.transitionDynamics = transitionDynamics;
	}

	public List<StateTransitionProb> stateTransitions(State s, Action a) {

		//get agent current position
		GenericOOState gs = (GenericOOState)s;
		ExGridAgent2 agent = (ExGridAgent2)gs.object(CLASS_AGENT);

		int curX = agent.x1;
		int curY = agent.y1;

		int adir = actionDir(a);

		List<StateTransitionProb> tps = new ArrayList<StateTransitionProb>(4);
		StateTransitionProb noChange = null;
		for(int i = 0; i < 4; i++){

			int [] newPos = this.moveResult(curX, curY, i);
			if(newPos[0] != curX || newPos[1] != curY){
				//new possible outcome
				GenericOOState ns = gs.copy();
				ExGridAgent2 nagent = (ExGridAgent2)ns.touch(CLASS_AGENT);
				nagent.x1 = newPos[0];
				nagent.y1 = newPos[1];

				//create transition probability object and add to our list of outcomes
				tps.add(new StateTransitionProb(ns, this.transitionDynamics[adir][i]));
			}
			else{
				//this direction didn't lead anywhere new
				//if there are existing possible directions
				//that wouldn't lead anywhere, aggregate with them
				if(noChange != null){
					noChange.p += this.transitionDynamics[adir][i];
				}
				else{
					//otherwise create this new state and transition
					noChange = new StateTransitionProb(s.copy(), this.transitionDynamics[adir][i]);
					tps.add(noChange);
				}
			}

		}


		return tps;
	}

	public State sample(State s, Action a) {

		s = s.copy();
		GenericOOState gs = (GenericOOState)s;
		ExGridAgent2 agent = (ExGridAgent2)gs.touch(CLASS_AGENT);
		int curX = agent.x1;
		int curY = agent.y1;

		int adir = actionDir(a);

		//sample direction with random roll
		double r = Math.random();
		double sumProb = 0.;
		int dir = 0;
		for(int i = 0; i < 4; i++){
			sumProb += this.transitionDynamics[adir][i];
			if(r < sumProb){
				dir = i;
				break; //found direction
			}
		}

		//get resulting position
		int [] newPos = this.moveResult(curX, curY, dir);

		//set the new position
		agent.x1 = newPos[0];
		agent.y1 = newPos[1];

		//return the state we just modified
		return gs;
	}

	protected int actionDir(Action a){
		int adir = -1;
		if(a.actionName().equals(ACTION_NORTH_NORTH)){
			adir = 0;
		}
		else if(a.actionName().equals(ACTION_SOUTH_SOUTH)){
			adir = 1;
		}
		else if(a.actionName().equals(ACTION_EAST_EAST)){
			adir = 2;
		}
		else if(a.actionName().equals(ACTION_WEST_WEST)){
			adir = 3;
		}
		return adir;
	}


	protected int [] moveResult(int curX, int curY, int direction){

		//first get change in x and y from direction using 0: north; 1: south; 2:east; 3: west
		int xdelta = 0;
		int ydelta = 0;
		if(direction == 0){
			ydelta = 1;
		}
		else if(direction == 1){
			ydelta = -1;
		}
		else if(direction == 2){
			xdelta = 1;
		}
		else{
			xdelta = -1;
		}

		int nx = curX + xdelta;
		int ny = curY + ydelta;

		int width = this.map.length;
		int height = this.map[0].length;

		//make sure new position is valid (not a wall or off bounds)
		if(nx < 0 || nx >= width || ny < 0 || ny >= height ||
				map[nx][ny] == 1){
			nx = curX;
			ny = curY;
		}


		return new int[]{nx,ny};

	}
}