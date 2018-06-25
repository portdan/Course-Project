package bgu.cai.course_project.SA;

import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

import java.util.Arrays;
import java.util.List;

import static bgu.cai.course_project.SA.ExOOGridWorld.*;


/**
 * @author James MacGlashan.
 */
@DeepCopyState
public class ExGridAgent implements ObjectInstance {

	public int x;
	public int y;

	public String name = "agent";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final static List<Object> keys = Arrays.<Object>asList(VAR_X, VAR_Y);

	public ExGridAgent() {
	}

	public ExGridAgent(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public ExGridAgent(int x, int y, String name) {
		this.x = x;
		this.y = y;
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
	public ExGridAgent copyWithName(String objectName) {

		ExGridAgent nagent = this.copy();
		nagent.name = objectName;
		return nagent;
	}

	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {

		if(!(variableKey instanceof String)){
			throw new RuntimeException("ExGridAgent variable key must be a string");
		}

		String key = (String)variableKey;

		if(key.equals(VAR_X)){
			return x;
		}
		else if(key.equals(VAR_Y)){
			return y;
		}

		throw new UnknownKeyException(variableKey);
	}

	@Override
	public ExGridAgent copy() {
		return new ExGridAgent(x, y, name);
	}

	@Override
	public String toString() {
		return OOStateUtilities.objectInstanceToString(this);
	}

}
