package bgu.cai.course_project.SA;

import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.auxiliary.common.SinglePFTF;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.oo.OODomain;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.singleagent.common.SingleGoalPFRF;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.visualizer.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author James MacGlashan.
 */
public class ExOOGridWorld implements DomainGenerator{

	protected int mapNum;

	public ExOOGridWorld(int mapNum) {
		this.setDeterministicTransitionDynamics();
		this.mapNum=mapNum;
	}

	public static final String VAR_X1 = "x1";
	public static final String VAR_Y1 = "y1";
	public static final String VAR_X2 = "x2";
	public static final String VAR_Y2 = "y2";

	public static final String VAR_TYPE = "type";

	public static final String CLASS_AGENT = "agent";
	public static final String CLASS_LOCATION = "location";

	public static final String ACTION_NORTH_NORTH = "nn";
	public static final String ACTION_SOUTH_NORTH = "sn";
	public static final String ACTION_EAST_NORTH = "en";
	public static final String ACTION_WEST_NORTH = "wn";
	public static final String ACTION_NORTH_SOUTH = "ns";
	public static final String ACTION_SOUTH_SOUTH = "ss";
	public static final String ACTION_EAST_SOUTH = "es";
	public static final String ACTION_WEST_SOUTH = "ws";
	public static final String ACTION_NORTH_EAST = "ne";
	public static final String ACTION_SOUTH_EAST = "se";
	public static final String ACTION_EAST_EAST = "ee";
	public static final String ACTION_WEST_EAST = "we";
	public static final String ACTION_NORTH_WEST = "nw";
	public static final String ACTION_SOUTH_WEST = "sw";
	public static final String ACTION_EAST_WEST = "ew";
	public static final String ACTION_WEST_WEST = "ww";

	public static final String PF_AT = "at";

	//ordered so first dimension is x
	protected int [][] map1 = new int[][]{
		{0,0,0,0,0,1,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,1,0,0,0,0,0},
		{0,0,0,0,0,1,0,0,0,0,0},
		{0,0,0,0,0,1,0,0,0,0,0},
		{1,0,1,1,1,1,1,1,0,1,1},
		{0,0,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,1,0,0,0,0,0,0},
	};

	//ordered so first dimension is x
	protected int [][] map2 = new int[][]{
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,0,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,0,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,0,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
	};

	/**
	 * Returns a deep copy of the map being used for the domain
	 * @return a deep copy of the map being used in the domain
	 */
	public int [][] getMap(){

		int[][] m;

		switch (mapNum) {
		case 1:
			m=map1;	
			break;
		case 2:
			m=map2;
			break;
		default:
			return null;
		}

		int [][] cmap = new int[m.length][m[0].length];
		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m[0].length; j++){
				cmap[i][j] = m[i][j];
			}
		}
		return cmap;
	}

	protected RewardFunction rf;
	protected TerminalFunction tf;

	public RewardFunction getRf() {
		return rf;
	}

	public void setRf(RewardFunction rf) {
		this.rf = rf;
	}

	public TerminalFunction getTf() {
		return tf;
	}

	public void setTf(TerminalFunction tf) {
		this.tf = tf;
	}

	protected double[][] transitionDynamics;

	protected void setDeterministicTransitionDynamics(){
		int na = 4;
		transitionDynamics = new double[na][na];
		for(int i = 0; i < na; i++){
			for(int j = 0; j < na; j++){
				if(i != j){
					transitionDynamics[i][j] = 0.;
				}
				else{
					transitionDynamics[i][j] = 1.;
				}
			}
		}
	}

	protected void setProbSucceedTransitionDynamics(double probSucceed){
		int na = 4;
		double pAlt = (1.-probSucceed)/3.;
		transitionDynamics = new double[na][na];
		for(int i = 0; i < na; i++){
			for(int j = 0; j < na; j++){
				if(i != j){
					transitionDynamics[i][j] = pAlt;
				}
				else{
					transitionDynamics[i][j] = probSucceed;
				}
			}
		}
	}

	public void setTransitionDynamics(double [][] transitionDynamics){
		this.transitionDynamics = transitionDynamics.clone();
	}

	public double [][] getTransitionDynamics(){
		double [][] copy = new double[transitionDynamics.length][transitionDynamics[0].length];
		for(int i = 0; i < transitionDynamics.length; i++){
			for(int j = 0; j < transitionDynamics[0].length; j++){
				copy[i][j] = transitionDynamics[i][j];
			}
		}
		return copy;
	}

	public List<PropositionalFunction> generatePfs(){
		return Arrays.<PropositionalFunction>asList(new AtLocation());
	}

	protected FactoredModel fm;

	@Override
	public OOSADomain generateDomain() {

		OOSADomain domain = new OOSADomain();

		domain.addStateClass(CLASS_AGENT, ExGridAgent.class)
		.addStateClass(CLASS_LOCATION, ExGridLocation.class);

		domain.addActionTypes(
				new UniversalActionType(ACTION_NORTH_NORTH),
				new UniversalActionType(ACTION_SOUTH_SOUTH),
				new UniversalActionType(ACTION_EAST_EAST),
				new UniversalActionType(ACTION_WEST_WEST));


		OODomain.Helper.addPfsToDomain(domain, this.generatePfs());

		OOGridWorldStateModel smodel = new OOGridWorldStateModel(getMap(),getTransitionDynamics());

		rf = new SingleGoalPFRF(domain.propFunction(PF_AT), 100, -1);
		tf = new SinglePFTF(domain.propFunction(PF_AT));

		fm = new FactoredModel(smodel, rf, tf);

		domain.setModel(fm);

		return domain;
	}

	public void setDeterministicTransitionFactoredModel() {	
		setDeterministicTransitionDynamics();
		OOGridWorldStateModel smodel = new OOGridWorldStateModel(getMap(),getTransitionDynamics());
		fm.setStateModel(smodel);
	}

	public void setProbSucceedTransitionFactoredModel(double probSucceed){
		setProbSucceedTransitionDynamics(probSucceed);
		OOGridWorldStateModel smodel = new OOGridWorldStateModel(getMap(),getTransitionDynamics());
		fm.setStateModel(smodel);
	}


	public  Visualizer getVisualizer(){

		return new Visualizer(this.getStateRenderLayer());
	}

	public  StateRenderLayer getStateRenderLayer(){

		StateRenderLayer rl = new StateRenderLayer();

		rl.addStatePainter(new WallPainter(getMap()));

		OOStatePainter ooStatePainter = new OOStatePainter();

		ooStatePainter.addObjectClassPainter(CLASS_LOCATION, new LocationPainter(getMap()));
		ooStatePainter.addObjectClassPainter(CLASS_AGENT, new AgentPainter(getMap()));

		rl.addStatePainter(ooStatePainter);


		return rl;
	}
}
