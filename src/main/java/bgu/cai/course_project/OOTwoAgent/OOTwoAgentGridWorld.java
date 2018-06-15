package bgu.cai.course_project.OOTwoAgent;

import java.util.Arrays;
import java.util.List;

import bgu.cai.course_project.OOTwoAgent.Visual.OOTwoAgentAgentPainter;
import bgu.cai.course_project.OOTwoAgent.Visual.OOTwoAgentLocationPainter;
import bgu.cai.course_project.OOTwoAgent.Visual.OOTwoAgentWallPainter;

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
import burlap.visualizer.OOStatePainter;
import burlap.visualizer.StateRenderLayer;
import burlap.visualizer.Visualizer;

public class OOTwoAgentGridWorld implements DomainGenerator {

	public static final String VAR_X1 = "x1";
	public static final String VAR_Y1 = "y1";
	public static final String VAR_X2 = "x2";
	public static final String VAR_Y2 = "y2";

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

	public static final String VAR_TYPE = "type";

	public static final String CLASS_AGENT = "agent";

	public static final String CLASS_LOCATION = "location";

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
		return Arrays.<PropositionalFunction>asList(new OOTwoAgentAtLocation());
	}

	public Visualizer getVisualizer(){
		return new Visualizer(this.getStateRenderLayer());
	}

	public StateRenderLayer getStateRenderLayer(){
		StateRenderLayer rl = new StateRenderLayer();
		rl.addStatePainter(new OOTwoAgentWallPainter(map));
		OOStatePainter ooStatePainter = new OOStatePainter();
		ooStatePainter.addObjectClassPainter(CLASS_LOCATION, new OOTwoAgentLocationPainter(map));
		ooStatePainter.addObjectClassPainter(CLASS_AGENT, new OOTwoAgentAgentPainter(map));
		rl.addStatePainter(ooStatePainter);

		return rl;
	}

	@Override
	public OOSADomain generateDomain() {

		OOSADomain domain = new OOSADomain();

		domain.addStateClass(CLASS_AGENT, OOTwoAgentGridState.class)
		.addStateClass(CLASS_LOCATION, OOTwoAgentGridLocation.class);

		domain.addActionTypes(
				new UniversalActionType(ACTION_NORTH_EAST),
				new UniversalActionType(ACTION_NORTH_NORTH),
				new UniversalActionType(ACTION_SOUTH_NORTH),
				new UniversalActionType(ACTION_EAST_NORTH),
				new UniversalActionType(ACTION_WEST_NORTH),
				new UniversalActionType(ACTION_NORTH_SOUTH),
				new UniversalActionType(ACTION_SOUTH_SOUTH),
				new UniversalActionType(ACTION_EAST_SOUTH),
				new UniversalActionType(ACTION_WEST_SOUTH),
				new UniversalActionType(ACTION_NORTH_EAST),
				new UniversalActionType(ACTION_SOUTH_EAST),
				new UniversalActionType(ACTION_EAST_EAST),
				new UniversalActionType(ACTION_WEST_EAST),
				new UniversalActionType(ACTION_NORTH_WEST),
				new UniversalActionType(ACTION_SOUTH_WEST),
				new UniversalActionType(ACTION_EAST_WEST),
				new UniversalActionType(ACTION_WEST_WEST));

		OODomain.Helper.addPfsToDomain(domain, this.generatePfs());

		OOTwoAgentGridWorldStateModel smodel = new OOTwoAgentGridWorldStateModel(map);

		//RewardFunction rf = new OOTwoAgentRewardFunction(domain.propFunction(PF_AT), 100, -1);
		//		TerminalFunction tf = new OOTwoAgentTerminalFunction(domain.propFunction(PF_AT));

		RewardFunction rf = new SingleGoalPFRF(domain.propFunction(PF_AT), 100, -1);
		TerminalFunction tf = new SinglePFTF(domain.propFunction(PF_AT));

		domain.setModel(new FactoredModel(smodel, rf, tf));

		return domain;
	}
}
