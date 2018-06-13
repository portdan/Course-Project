package bgu.cai.course_project.TwoAgent;

import bgu.cai.course_project.TwoAgent.Visual.TwoAgentAgentPainter;
import bgu.cai.course_project.TwoAgent.Visual.TwoAgentGoalPainter;
import bgu.cai.course_project.TwoAgent.Visual.TwoAgentWallPainter;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.visualizer.StateRenderLayer;
import burlap.visualizer.Visualizer;

public class TwoAgentGridWorld implements DomainGenerator {

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

	protected int goalx1,goaly1,goalx2,goaly2 = -1;

	public void setGoalLocation(int goalx1, int goaly1,int goalx2, int goaly2){
		this.goalx1 = goalx1;
		this.goaly1 = goaly1;
		this.goalx2 = goalx2;
		this.goaly2 = goaly2;
	}

	public StateRenderLayer getStateRenderLayer(){
		StateRenderLayer rl = new StateRenderLayer();
		rl.addStatePainter(new TwoAgentWallPainter(map));
		rl.addStatePainter(new TwoAgentAgentPainter(map));
		rl.addStatePainter(new TwoAgentGoalPainter(map,goalx1,goaly1,goalx2,goaly2));

		return rl;
	}

	public Visualizer getVisualizer(){
		return new Visualizer(this.getStateRenderLayer());
	}

	@Override
	public Domain generateDomain() {

		SADomain domain = new SADomain();

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

		TwoAgentGridWorldStateModel smodel = new TwoAgentGridWorldStateModel(map);
		TwoAgentRewardFunction rf = new TwoAgentRewardFunction(goalx1,goaly1,goalx2,goaly2);
		TwoAgentTerminalFunction tf = new TwoAgentTerminalFunction(goalx1,goaly1,goalx2,goaly2);

		domain.setModel(new FactoredModel(smodel, rf, tf));

		return domain;
	}

}
