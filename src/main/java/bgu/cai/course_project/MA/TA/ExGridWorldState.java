package bgu.cai.course_project.MA.TA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.oo.state.MutableOOState;
import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.OOVariableKey;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.StateUtilities;

import static bgu.cai.course_project.MA.TA.ExOOGridWorld.*;

public class ExGridWorldState implements MutableOOState {

	public ExGridAgent agent;

	public ExGridAgent touchAgent(){
		this.agent = agent.copy();
		return agent;
	}

	public List<ExGridLocation> locations = new ArrayList<ExGridLocation>();

	public ExGridLocation touchLocation(int ind){
		ExGridLocation n = locations.get(ind).copy();
		touchLocations().remove(ind);
		locations.add(ind, n);
		return n;
	}

	public List<ExGridLocation> touchLocations(){
		this.locations = new ArrayList<ExGridLocation>(locations);
		return locations;
	}

	public List<ExGridLocation> deepTouchLocations(){
		List<ExGridLocation> nlocs = new ArrayList<ExGridLocation>(locations.size());
		for(ExGridLocation loc : locations){
			nlocs.add(loc.copy());
		}
		locations = nlocs;
		return locations;
	}



	public ExGridWorldState() {
	}

	public ExGridWorldState(int x1, int y1,int x2, int y2 , ExGridLocation...locations){
		this(new ExGridAgent(x1, y1, x2, y2), locations);
	}

	public ExGridWorldState(ExGridAgent agent, ExGridLocation...locations){
		this.agent = agent;
		if(locations.length == 0){
			this.locations = new ArrayList<ExGridLocation>();
		}
		else {
			this.locations = Arrays.asList(locations);
		}
	}

	public ExGridWorldState(ExGridAgent agent, List<ExGridLocation> locations){
		this.agent = agent;
		this.locations = locations;
	}

	@Override
	public List<Object> variableKeys() {
		return OOStateUtilities.flatStateKeys(this);
	}

	@Override
	public MutableOOState addObject(ObjectInstance o) {
		if(!(o instanceof ExGridLocation)){
			throw new RuntimeException("Can only add ExGridLocation objects to a ExGridWorldState.");
		}
		ExGridLocation loc = (ExGridLocation)o;

		//copy on write
		touchLocations().add(loc);

		return this;
	}

	@Override
	public MutableOOState removeObject(String oname) {
		if(oname.equals(agent.name())){
			throw new RuntimeException("Cannot remove agent object from state");
		}
		int ind = this.locationInd(oname);
		if(ind == -1){
			throw new RuntimeException("Cannot find object " + oname);
		}

		//copy on write
		touchLocations().remove(ind);

		return this;
	}

	@Override
	public MutableOOState renameObject(String objectName, String newName) {

		if(objectName.equals(agent.name())){
			ExGridAgent nagent = agent.copyWithName(newName);
			this.agent = nagent;
		}
		else{
			int ind = this.locationInd(objectName);
			if(ind == -1){
				throw new RuntimeException("Cannot find object " + objectName);
			}

			//copy on write
			ExGridLocation nloc = this.locations.get(ind).copyWithName(newName);
			touchLocations().remove(ind);
			locations.add(ind, nloc);

		}

		return this;
	}

	@Override
	public int numObjects() {
		return 1 + this.locations.size();
	}

	@Override
	public ObjectInstance object(String oname) {
		if(oname.equals(agent.name())){
			return agent;
		}
		int ind = this.locationInd(oname);
		if(ind != -1){
			return locations.get(ind);
		}
		return null;
	}

	@Override
	public List<ObjectInstance> objects() {
		List<ObjectInstance> obs = new ArrayList<ObjectInstance>(1+locations.size());
		obs.add(agent);
		obs.addAll(locations);
		return obs;
	}

	@Override
	public List<ObjectInstance> objectsOfClass(String oclass) {
		if(oclass.equals("agent")){
			return Arrays.<ObjectInstance>asList(agent);
		}
		else if(oclass.equals("location")){
			return new ArrayList<ObjectInstance>(locations);
		}
		throw new RuntimeException("Unknown class type " + oclass);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {

		OOVariableKey key = OOStateUtilities.generateKey(variableKey);

		int iv = StateUtilities.stringOrNumber(value).intValue();

		if(key.obName.equals(agent.name())){

			if(key.obVarKey.equals(VAR_X1)){
				touchAgent().x1 = iv;
			}
			else if(key.obVarKey.equals(VAR_Y1)){
				touchAgent().y1 = iv;
			}
			else if(key.obVarKey.equals(VAR_X2)){
				touchAgent().x2 = iv;
			}
			else if(key.obVarKey.equals(VAR_Y2)){
				touchAgent().y2 = iv;
			}
			else{
				throw new RuntimeException("Unknown variable key " + variableKey);
			}
			return this;
		}

		int ind = locationInd(key.obName);

		if(ind != -1){
			if(key.obVarKey.equals(VAR_X1)){
				touchLocation(ind).x1 = iv;
			}
			else if(key.obVarKey.equals(VAR_Y1)){
				touchLocation(ind).y1 = iv;
			}
			else if(key.obVarKey.equals(VAR_X2)){
				touchLocation(ind).x2 = iv;
			}
			else if(key.obVarKey.equals(VAR_Y2)){
				touchLocation(ind).y2 = iv;
			}
			else if(key.obVarKey.equals(VAR_TYPE)){
				touchLocation(ind).type = iv;
			}
			else{
				throw new RuntimeException("Unknown variable key " + variableKey);
			}

			return this;
		}

		throw new RuntimeException("Unknown variable key " + variableKey);
	}

	@Override
	public Object get(Object variableKey) {
		OOVariableKey key = OOStateUtilities.generateKey(variableKey);
		if(key.obName.equals(agent.name())){
			return agent.get(key.obVarKey);
		}
		int ind = this.locationInd(key.obName);
		if(ind == -1){
			throw new RuntimeException("Cannot find object " + key.obName);
		}
		return locations.get(ind).get(key.obVarKey);
	}

	@Override
	public ExGridWorldState copy() {
		return new ExGridWorldState(agent, locations);
	}

	@Override
	public String toString() {
		return OOStateUtilities.ooStateToString(this);
	}

	protected int locationInd(String oname){
		int ind = -1;
		for(int i = 0; i < locations.size(); i++){
			if(locations.get(i).name().equals(oname)){
				ind = i;
				break;
			}
		}
		return ind;
	}
}
