package bgu.cai.course_project.MA.TA;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.StateUtilities;

import java.util.Arrays;
import java.util.List;

import static bgu.cai.course_project.MA.TA.ExOOGridWorld.*;

/**
 * @author James MacGlashan.
 */
public class ExGridLocation2 extends ExGridAgent2{

	public int type;

	private final static List<Object> keys = Arrays.<Object>asList(VAR_X1, VAR_Y1, VAR_TYPE);

	public ExGridLocation2() {
	}

	public ExGridLocation2(int x1, int y1, int x2, int y2, String name) {
		super(x1, y1, x2, y2, name);
	}

	public ExGridLocation2(int x1, int y1, int x2, int y2, int type, String name) {
		super(x1, y1, x2, y2, name);
		this.type = type;
	}


	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {
		if(variableKey.equals(VAR_TYPE)){
			return this.type;
		}
		return super.get(variableKey);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		if(variableKey.equals(VAR_TYPE)){
			this.type = StateUtilities.stringOrNumber(value).intValue();
		}
		else{
			super.set(variableKey, value);
		}
		return this;

	}

	@Override
	public String className() {
		return CLASS_LOCATION;
	}

	@Override
	public ObjectInstance copyWithName(String objectName) {
		return new ExGridLocation2(x1, y1, x2, y2, type, objectName);
	}

	@Override
	public ExGridLocation2 copy() {
		return new ExGridLocation2(x1, y1, x2, y2, type, name);
	}
}
