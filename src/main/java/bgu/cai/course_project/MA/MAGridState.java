package bgu.cai.course_project.MA;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;

/**
 * @author daniel
 *
 */
public class MAGridState implements MutableState {

	private final List<Object> keys = new ArrayList<Object>();

	public List<Integer> x = new ArrayList<Integer>();
	public List<Integer> y = new ArrayList<Integer>();

	public MAGridState() {}

	public MAGridState(ArrayList<Integer> x, ArrayList<Integer> y) {

		this.x =  new ArrayList<>(x);
		this.y = new ArrayList<>(y);

		for (int i=0; i<x.size(); i++) {
			keys.add("x"+i);
		}

		for (int i=0; i<y.size(); i++) {
			keys.add("y"+i);
		}
	} 


	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {

		for (int i=0; i<keys.size(); i++) {

			if(variableKey.equals("x"+i)){
				return x.get(i);
			}
			else if(variableKey.equals("y"+i)){
				return y.get(i);
			}
		}
		throw new UnknownKeyException(variableKey);
	}

	@Override
	public State copy() {
		return new MAGridState(new ArrayList<>(x), new ArrayList<>(y));
	}

	@Override
	public MutableState set(Object variableKey, Object value) {

		for (int i=0; i<keys.size(); i++) {

			if(variableKey.equals("x"+i)){
				x.set(i,StateUtilities.stringOrNumber(value).intValue());
			}
			else if(variableKey.equals("y"+i)){
				y.set(i,StateUtilities.stringOrNumber(value).intValue());
			}
			else{
				throw new UnknownKeyException(variableKey);
			}
		}

		return this;
	}
	
	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}
}
