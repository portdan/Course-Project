package bgu.cai.course_project.tutorial.oo;

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
public class ExampleOOGridWorld implements DomainGenerator{

	public static final String VAR_X = "x";
	public static final String VAR_Y = "y";
	public static final String VAR_TYPE = "type";

	public static final String CLASS_AGENT = "agent";
	public static final String CLASS_LOCATION = "location";

	public static final String ACTION_NORTH = "north";
	public static final String ACTION_SOUTH = "south";
	public static final String ACTION_EAST = "east";
	public static final String ACTION_WEST = "west";

	public static final String PF_AT = "at";


	//ordered so first dimension is x
	protected int [][] map = new int[][]{
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

	public List<PropositionalFunction> generatePfs(){
		return Arrays.<PropositionalFunction>asList(new AtLocation());
	}

	@Override
	public OOSADomain generateDomain() {

		OOSADomain domain = new OOSADomain();

		domain.addStateClass(CLASS_AGENT, ExGridAgent.class)
		.addStateClass(CLASS_LOCATION, EXGridLocation.class);

		domain.addActionTypes(
				new UniversalActionType(ACTION_NORTH),
				new UniversalActionType(ACTION_SOUTH),
				new UniversalActionType(ACTION_EAST),
				new UniversalActionType(ACTION_WEST));


		OODomain.Helper.addPfsToDomain(domain, this.generatePfs());

		OOGridWorldStateModel smodel = new OOGridWorldStateModel(map);
		RewardFunction rf = new SingleGoalPFRF(domain.propFunction(PF_AT), 100, -1);
		TerminalFunction tf = new SinglePFTF(domain.propFunction(PF_AT));

		domain.setModel(new FactoredModel(smodel, rf, tf));


		return domain;
	}

	public Visualizer getVisualizer(){
		return new Visualizer(this.getStateRenderLayer());
	}

	public StateRenderLayer getStateRenderLayer(){
		StateRenderLayer rl = new StateRenderLayer();
		rl.addStatePainter(new WallPainter(map));
		OOStatePainter ooStatePainter = new OOStatePainter();
		ooStatePainter.addObjectClassPainter(CLASS_LOCATION, new LocationPainter(map));
		ooStatePainter.addObjectClassPainter(CLASS_AGENT, new AgentPainter(map));
		rl.addStatePainter(ooStatePainter);


		return rl;
	}
}
