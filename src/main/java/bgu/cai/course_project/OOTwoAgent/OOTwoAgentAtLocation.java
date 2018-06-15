package bgu.cai.course_project.OOTwoAgent;

import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.VAR_X1;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.VAR_X2;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.VAR_Y1;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.VAR_Y2;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.CLASS_AGENT;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.CLASS_LOCATION;
import static bgu.cai.course_project.OOTwoAgent.OOTwoAgentGridWorld.PF_AT;

import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.ObjectInstance;

public class OOTwoAgentAtLocation extends PropositionalFunction {

	public OOTwoAgentAtLocation(){
		super(PF_AT, new String []{CLASS_AGENT, CLASS_LOCATION});
	}

	@Override
	public boolean isTrue(OOState s, String... params) {
		ObjectInstance agent = s.object(params[0]);
		ObjectInstance location = s.object(params[1]);

		int ax1 = (Integer)agent.get(VAR_X1);
		int ay1 = (Integer)agent.get(VAR_Y1);
		int ax2 = (Integer)agent.get(VAR_X2);
		int ay2 = (Integer)agent.get(VAR_Y2);

		int lx1 = (Integer)location.get(VAR_X1);
		int ly1 = (Integer)location.get(VAR_Y1);
		int lx2 = (Integer)location.get(VAR_X2);
		int ly2 = (Integer)location.get(VAR_Y2);

		return ax1 == lx1 && ay1 == ly1 && ax2 == lx2 && ay2 == ly2;

	}

}
