package bgu.cai.course_project.tutorial;

import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

import static bgu.cai.course_project.tutorial.ExampleGridWorld.VAR_X;
import static bgu.cai.course_project.tutorial.ExampleGridWorld.VAR_Y;

@DeepCopyState
public class EXGridState implements MutableState {

	private final static List<Object> keys = Arrays.<Object>asList(VAR_X, VAR_Y);

	public int x;
	public int y;

	public EXGridState() {}

	public EXGridState(int x, int y) {
		this.x = x;
		this.y = y;
	} 

	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {

		if(variableKey.equals(VAR_X)){
			return x;
		}
		else if(variableKey.equals(VAR_Y)){
			return y;
		}
		throw new UnknownKeyException(variableKey);
	}

	@Override
	public State copy() {
		return new EXGridState(x, y);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {

		if(variableKey.equals(VAR_X)){
			this.x = StateUtilities.stringOrNumber(value).intValue();
		}
		else if(variableKey.equals(VAR_Y)){
			this.y = StateUtilities.stringOrNumber(value).intValue();
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
