package bgu.cai.course_project.TwoAgent;

import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_X1;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_X2;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_Y1;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_Y2;

import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;

/**
 * @author daniel
 *
 */
public class TwoAgentGridState implements MutableState {

	private final static List<Object> keys = Arrays.<Object>asList(VAR_X1, VAR_Y1,VAR_X2, VAR_Y2);

	public int x1,y1;
	public int x2,y2;

	public TwoAgentGridState() {}

	public TwoAgentGridState(int x1,int y1,int x2,int y2) {

		this.x1 = x1;
		this.y1 = y1;

		this.x2 = x2;
		this.y2 = y2;
	} 


	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {

		if(variableKey.equals(VAR_X1)){
			return x1;
		}
		else if(variableKey.equals(VAR_Y1)){
			return y1;
		}
		if(variableKey.equals(VAR_X2)){
			return x2;
		}
		else if(variableKey.equals(VAR_Y2)){
			return y2;
		}

		throw new UnknownKeyException(variableKey);
	}

	@Override
	public State copy() {
		return new TwoAgentGridState(x1,y1,x2,y2);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {

		if(variableKey.equals(VAR_X1)){
			x1 = StateUtilities.stringOrNumber(value).intValue();
		}
		else if(variableKey.equals(VAR_Y1)){
			y1 = StateUtilities.stringOrNumber(value).intValue();
		}
		if(variableKey.equals(VAR_X2)){
			x2 = StateUtilities.stringOrNumber(value).intValue();
		}
		else if(variableKey.equals(VAR_Y2)){
			y2 = StateUtilities.stringOrNumber(value).intValue();
		}
		else{
			throw new UnknownKeyException(variableKey);
		}
		return this;
	}

	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}
}
