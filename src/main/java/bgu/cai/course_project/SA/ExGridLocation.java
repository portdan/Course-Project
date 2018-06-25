package bgu.cai.course_project.SA;

import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.UnknownKeyException;

import java.util.Arrays;
import java.util.List;

import static bgu.cai.course_project.SA.ExOOGridWorld.*;

/**
 * @author James MacGlashan.
 */
public class ExGridLocation implements ObjectInstance {

	public int x;
	public int y;
	public int type;

	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final static List<Object> keys = Arrays.<Object>asList(VAR_X, VAR_Y, VAR_TYPE);

	public ExGridLocation() {
	}

	public ExGridLocation(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;	
	}

	public ExGridLocation(int x, int y, int type, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.type = type;
	}

	@Override
	public String className() {
		return CLASS_LOCATION;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {

		if(!(variableKey instanceof String)){
			throw new RuntimeException("ExGridLocation variable key must be a string");
		}

		String key = (String)variableKey;

		if(variableKey.equals(VAR_TYPE)){
			return this.type;
		}
		else if(key.equals(VAR_X)){
			return x;
		}
		else if(key.equals(VAR_Y)){
			return y;
		}

		throw new UnknownKeyException(variableKey);
	}

	@Override
	public ExGridLocation copyWithName(String objectName) {
		ExGridLocation nloc = this.copy();
		nloc.name = objectName;
		return nloc;	}

	@Override
	public ExGridLocation copy() {
		return new ExGridLocation(x, y, type, name);
	}

	@Override
	public String toString() {
		return OOStateUtilities.objectInstanceToString(this);
	}
}
