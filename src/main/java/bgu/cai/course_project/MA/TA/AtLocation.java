package bgu.cai.course_project.MA.TA;

import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.ObjectInstance;

import static bgu.cai.course_project.MA.TA.ExOOGridWorld.*;

class AtLocation extends PropositionalFunction {

	public AtLocation(){
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