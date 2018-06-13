package bgu.cai.course_project.TwoAgent;

import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_X1;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_X2;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_Y1;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_Y2;

import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;

public class TwoAgentTerminalFunction implements TerminalFunction {

	int goalX1;
	int goalY1;

	int goalX2;
	int goalY2;

	public TwoAgentTerminalFunction(int goalX1, int goalY1,int goalX2, int goalY2){
		this.goalX1 = goalX1;
		this.goalY1 = goalY1;
		this.goalX2 = goalX2;
		this.goalY2 = goalY2;
	}

	@Override
	public boolean isTerminal(State s) {

		//get location of agents in next state
		int ax1 = (Integer)s.get(VAR_X1);
		int ay1 = (Integer)s.get(VAR_Y1);
		int ax2 = (Integer)s.get(VAR_X2);
		int ay2 = (Integer)s.get(VAR_Y2);

		//are they at goal location?
		if(ax1 == this.goalX1 && ay1 == this.goalY1 &&
				ax2 == this.goalX2 && ay2 == this.goalY2){
			return true;
		}

		return false;
	}

}
