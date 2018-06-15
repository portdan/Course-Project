package bgu.cai.course_project.MA.TA;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

import java.util.Arrays;
import java.util.List;

import static bgu.cai.course_project.MA.TA.ExOOGridWorld.*;


/**
 * @author James MacGlashan.
 */
@DeepCopyState
public class ExGridAgent2 implements ObjectInstance, MutableState {

	public int x1;
	public int y1;
	public int x2;
	public int y2;

	public String name = "agent";

	private final static List<Object> keys = Arrays.<Object>asList(VAR_X1, VAR_Y1, VAR_X2, VAR_Y2);

	public ExGridAgent2() {
	}

	public ExGridAgent2(int x1, int y1,int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public ExGridAgent2(int x1, int y1,int x2, int y2, String name) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.name = name;
	}

	@Override
	public String className() {
		return CLASS_AGENT;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ObjectInstance copyWithName(String objectName) {
		return new ExGridAgent(x1, y1, x2, y2, objectName);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		if(variableKey.equals(VAR_X1)){
			this.x1 = StateUtilities.stringOrNumber(value).intValue();
		}
		else if(variableKey.equals(VAR_Y1)){
			this.y1 = StateUtilities.stringOrNumber(value).intValue();
		}
		else if(variableKey.equals(VAR_X2)){
			this.x2 = StateUtilities.stringOrNumber(value).intValue();
		}
		else if(variableKey.equals(VAR_Y2)){
			this.y2 = StateUtilities.stringOrNumber(value).intValue();
		}
		else{
			throw new UnknownKeyException(variableKey);
		}
		return this;
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
		else if(variableKey.equals(VAR_X2)){
			return x2;
		}
		else if(variableKey.equals(VAR_Y2)){
			return y2;
		}
		throw new UnknownKeyException(variableKey);
	}

	@Override
	public ExGridAgent2 copy() {
		return new ExGridAgent2(x1, y1, x2, y2, name);
	}

	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}

}
