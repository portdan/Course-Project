package bgu.cai.course_project.OOTwoAgent;

import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_EAST_EAST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_EAST_NORTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_EAST_SOUTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_EAST_WEST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_NORTH_EAST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_NORTH_NORTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_NORTH_SOUTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_NORTH_WEST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_SOUTH_EAST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_SOUTH_NORTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_SOUTH_SOUTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_SOUTH_WEST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_WEST_EAST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_WEST_NORTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_WEST_SOUTH;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.ACTION_WEST_WEST;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.CLASS_AGENT;


import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.generic.GenericOOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;


public class OOTwoAgentGridWorldStateModel implements FullStateModel {

	protected double [][] transitionProbs1;
	protected double [][] transitionProbs2;

	protected int [][] map;

	public OOTwoAgentGridWorldStateModel(int [][] map) {

		this.map = map.clone();

		this.transitionProbs1 = new double[4][2];
		this.transitionProbs2 = new double[4][2];

		for(int i = 0; i < 4; i++){
			transitionProbs1[i][0] = 0.8;
			transitionProbs2[i][0] = 0.8;

			transitionProbs1[i][1] = 0.2;
			transitionProbs2[i][1] = 0.2;
		}
	}

	protected int[] actionDir(Action a){

		int adir[] = {-1,-1};

		if(a.actionName().equals(ACTION_NORTH_NORTH)){
			adir[0] = 0;adir[1] = 0;
		}
		else if(a.actionName().equals(ACTION_NORTH_SOUTH)){
			adir[0] = 0;adir[1] = 1;
		}
		else if(a.actionName().equals(ACTION_NORTH_EAST)){
			adir[0] = 0;adir[1] = 2;
		}
		else if(a.actionName().equals(ACTION_NORTH_WEST)){
			adir[0] = 0;adir[1] = 3;
		}
		if(a.actionName().equals(ACTION_SOUTH_NORTH)){
			adir[0] = 1;adir[1] = 0;
		}
		else if(a.actionName().equals(ACTION_SOUTH_SOUTH)){
			adir[0] = 1;adir[1] = 1;
		}
		else if(a.actionName().equals(ACTION_SOUTH_EAST)){
			adir[0] = 1;adir[1] = 2;
		}
		else if(a.actionName().equals(ACTION_SOUTH_WEST)){
			adir[0] = 1;adir[1] = 3;
		}
		if(a.actionName().equals(ACTION_EAST_NORTH)){
			adir[0] = 2;adir[1] = 0;
		}
		else if(a.actionName().equals(ACTION_EAST_SOUTH)){
			adir[0] = 2;adir[1] = 1;
		}
		else if(a.actionName().equals(ACTION_EAST_EAST)){
			adir[0] = 2;adir[1] = 2;
		}
		else if(a.actionName().equals(ACTION_EAST_WEST)){
			adir[0] = 2;adir[1] = 3;
		}
		if(a.actionName().equals(ACTION_WEST_NORTH)){
			adir[0] = 3;adir[1] = 0;
		}
		else if(a.actionName().equals(ACTION_WEST_SOUTH)){
			adir[0] = 3;adir[1] = 1;
		}
		else if(a.actionName().equals(ACTION_WEST_EAST)){
			adir[0] = 3;adir[1] = 2;
		}
		else if(a.actionName().equals(ACTION_WEST_WEST)){
			adir[0] = 3;adir[1] = 3;
		}

		return adir;
	}

	protected int [] moveResult(int curX1, int curY1,int curX2, int curY2, int[] direction){

		//first get change in x and y from direction using 0: north; 1: south; 2:east; 3: west
		int x1delta = 0;
		int y1delta = 0;
		int x2delta = 0;
		int y2delta = 0;

		if(direction[0] == -1){ // no move
			y1delta = 0;
			x1delta = 0;
		}
		else if(direction[0] == 0){
			y1delta = 1;
		}
		else if(direction[0] == 1){
			y1delta = -1;
		}
		else if(direction[0] == 2){
			x1delta = 1;
		}
		else{
			x1delta = -1;
		}

		if(direction[1] == -1){ // no move
			y2delta = 0;
			x2delta = 0;
		}
		else if(direction[1] == 0){
			y2delta = 1;
		}
		else if(direction[1] == 1){
			y2delta = -1;
		}
		else if(direction[1] == 2){
			x2delta = 1;
		}
		else{
			x2delta = -1;
		}

		int nx1 = curX1 + x1delta;
		int ny1 = curY1 + y1delta;

		int nx2 = curX2 + x2delta;
		int ny2 = curY2 + y2delta;

		int width = map.length;
		int height = map[0].length;

		//make sure new position is valid (not a wall or off bounds)
		if(nx1 < 0 || nx1 >= width || ny1 < 0 || ny1 >= height || map[nx1][ny1] == 1){
			nx1 = curX1;
			ny1 = curY1;
		}

		//make sure new position is valid (not a wall or off bounds)
		if(nx2 < 0 || nx2 >= width || ny2 < 0 || ny2 >= height || map[nx2][ny2] == 1){
			nx2 = curX2;
			ny2 = curY2;
		}

		//make sure new position is valid (no collisions)
		if(nx1 == nx2 && ny1 == ny2 ){

			nx1 = curX1;
			ny1 = curY1;

			nx2 = curX2;
			ny2 = curY2;
		}


		return new int[]{nx1,ny1,nx2,ny2};

	}

	@Override
	public State sample(State s, Action a) {

		s = s.copy();
		GenericOOState gs = (GenericOOState)s;
		OOTwoAgentGridState agents = (OOTwoAgentGridState)gs.touch(CLASS_AGENT);

		int curX1 = agents.x1;
		int curY1 = agents.y1;
		int curX2 = agents.x2;
		int curY2 = agents.y2;

		int[] adir = actionDir(a);

		//sample direction with random roll
		double r; 
		int dir[] = {-1,-1};

		r = Math.random();

		if(r < transitionProbs1[adir[0]][0])
			dir[0] = adir[0];
		else
			dir[0] = -1;

		r = Math.random();

		if(r < transitionProbs1[adir[1]][0])
			dir[1] = adir[1];
		else
			dir[1] = -1;

		//get resulting position
		int [] newPos = this.moveResult(curX1, curY1,curX2, curY2, dir);

		//set the new position
		agents.x1 = newPos[0];
		agents.y1 = newPos[1];
		agents.x2 = newPos[2];
		agents.y2 = newPos[3];

		//return the state we just modified
		return agents;
	}

	@Override

	public List<StateTransitionProb> stateTransitions(State s, Action a) {

		//get agent current position
		GenericOOState gs = (GenericOOState)s;
		OOTwoAgentGridState agents = (OOTwoAgentGridState)gs.object(CLASS_AGENT);

		int curX1 = agents.x1;
		int curY1 = agents.y1;
		int curX2 = agents.x2;
		int curY2 = agents.y2;

		int[] adir = actionDir(a);

		List<StateTransitionProb> tps = new ArrayList<StateTransitionProb>(4);
		StateTransitionProb noChange = null;

		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){

				int dir[] = {i,j};

				int [] newPos = this.moveResult(curX1, curY1,curX2, curY2, dir);

				if(newPos[0] == curX1 
						&& newPos[1] == curY1 
						&& newPos[2] == curX2 
						&& newPos[3] == curY2){

					//this direction didn't lead anywhere new
					//if there are existing possible directions
					//that wouldn't lead anywhere, aggregate with them
					if(noChange != null){
						noChange.p += transitionProbs1[adir[0]][1]*transitionProbs2[adir[1]][1];
					}
					else{
						//otherwise create this new state and transition
						noChange = new StateTransitionProb(s.copy(), 
								transitionProbs1[adir[0]][1]*transitionProbs2[adir[1]][1]);
						tps.add(noChange);
					}
				}

				else{

					GenericOOState ns = (GenericOOState) agents.copy();
					OOTwoAgentGridState nagent = (OOTwoAgentGridState)ns.touch(CLASS_AGENT);

					double p = 1;

					if(newPos[0] != curX1 || newPos[1] != curY1){
						//new possible outcome
						nagent.x1 = newPos[0];
						nagent.y1 = newPos[1];

						p *= transitionProbs1[adir[0]][0];
					}
					else {
						p *= transitionProbs1[adir[0]][1];
					}

					if(newPos[2] != curX2 || newPos[3] != curY2){
						//new possible outcome
						nagent.x2 = newPos[2];
						nagent.y2 = newPos[3];

						p *= transitionProbs2[adir[1]][0];
					}
					else {
						p *= transitionProbs2[adir[1]][1];
					}

					//create transition probability object and add to our list of outcomes
					tps.add(new StateTransitionProb(ns,p));
				}
			}
		}
		return tps;
	}
}