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

		int ax = (Integer)agent.get(VAR_X1);
		int ay = (Integer)agent.get(VAR_Y1);

		int lx = (Integer)location.get(VAR_X1);
		int ly = (Integer)location.get(VAR_Y1);

		return ax == lx && ay == ly;

	}

}

