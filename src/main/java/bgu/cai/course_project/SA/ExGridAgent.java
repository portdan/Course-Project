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

	public int x1;
	public int y1;
	public int x2;
	public int y2;

	public String name = "agent";
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final static List<Object> keys = Arrays.<Object>asList(VAR_X1, VAR_Y1, VAR_X2, VAR_Y2);

	public ExGridAgent() {
	}

	public ExGridAgent(int x1, int y1,int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public ExGridAgent(int x1, int y1,int x2, int y2, String name) {
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
		
		if(key.equals(VAR_X1)){
			return x1;
		}
		else if(key.equals(VAR_Y1)){
			return y1;
		}
		else if(key.equals(VAR_X2)){
			return x2;
		}
		else if(key.equals(VAR_Y2)){
			return y2;
		}
		
		throw new UnknownKeyException(variableKey);
	}

	@Override
	public ExGridAgent copy() {
		return new ExGridAgent(x1, y1, x2, y2, name);
	}

	@Override
	public String toString() {
		return OOStateUtilities.objectInstanceToString(this);
	}

}
