package bgu.cai.course_project.TwoAgent;

import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_X1;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_X2;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_Y1;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_Y2;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;

public class TwoAgentRewardFunction implements RewardFunction {

	int goalX1;
	int goalY1;

	int goalX2;
	int goalY2;

	public TwoAgentRewardFunction(int goalX1, int goalY1,int goalX2, int goalY2){
		this.goalX1 = goalX1;
		this.goalY1 = goalY1;
		this.goalX2 = goalX2;
		this.goalY2 = goalY2;
	}

	@Override
	public double reward(State s, Action a, State sprime) {

		//get location of agents in next state
		int ax1 = (Integer)s.get(VAR_X1);
		int ay1 = (Integer)s.get(VAR_Y1);
		int ax2 = (Integer)s.get(VAR_X2);
		int ay2 = (Integer)s.get(VAR_Y2);

		//are they at goal location?
		if(ax1 == this.goalX1 && ay1 == this.goalY1 &&
				ax2 == this.goalX2 && ay2 == this.goalY2){
			return 100.;
		}

		return -1;
	}

}
