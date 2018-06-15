package bgu.cai.course_project.OOTwoAgent;

import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.VAR_X1;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.VAR_X2;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.VAR_Y1;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.VAR_Y2;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;

public class OOTwoAgentRewardFunction implements RewardFunction {

	PropositionalFunction			pf;
	double							goalReward;
	double							nonGoalReward;

	public OOTwoAgentRewardFunction(PropositionalFunction pf){
		this.pf = pf;
		this.goalReward = 1.;
		this.nonGoalReward = 0.;
	}
	
	public OOTwoAgentRewardFunction(PropositionalFunction pf, double goalReward, double nonGoalReward){
		this.pf = pf;
		this.goalReward = goalReward;
		this.nonGoalReward = nonGoalReward;
	}

	@Override
	public double reward(State s, Action a, State sprime) {
		
		if(this.pf.someGroundingIsTrue((OOState)sprime)){
			return goalReward;
		}
		return nonGoalReward;
	}
}
