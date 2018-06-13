//package bgu.cai.course_project.testing;
//
//import java.util.List;
//
//import bgu.cai.course_project.tutorial.ExampleGridWorld.ExampleRF;
//import bgu.cai.course_project.tutorial.ExampleGridWorld.ExampleTF;
//import bgu.cai.course_project.tutorial.ExampleGridWorld.GridWorldStateModel;
//import burlap.mdp.auxiliary.DomainGenerator;
//import burlap.mdp.core.Domain;
//import burlap.mdp.core.TerminalFunction;
//import burlap.mdp.core.action.Action;
//import burlap.mdp.core.action.ActionType;
//import burlap.mdp.core.action.UniversalActionType;
//import burlap.mdp.core.state.State;
//import burlap.mdp.singleagent.SADomain;
//import burlap.mdp.singleagent.model.FactoredModel;
//import burlap.mdp.singleagent.model.RewardFunction;
//import burlap.mdp.stochasticgames.world.World;
//
//
//
//
//public class MultiAgentDomainGenerator implements DomainGenerator {
//
//	public class MultiAgentAction implements Action {
//
//		private Action[] actions;
//
//		public MultiAgentAction(Action[] actions) {
//			this.actions = actions;
//		}
//		@Override
//		public String actionName() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public Action copy() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//	}
//	public class MultiAgentUniversalActionType extends UniversalActionType{
//
//		private UniversalActionType[] actionTypes;
//		public MultiAgentUniversalActionType(String typeName, UniversalActionType[] actionTypes) {
//			super(typeName);
//
//			this.actionTypes = actionTypes;
//		}
//
//		@Override
//		public List<Action> allApplicableActions(State s) {
//			Action[] jointAction = new Action[this.actionTypes.length];
//			/* State[] saStates = ((MultiAgentState)s).getSingleAgentStates();
//			for (int i=0;i<saStates.length;i++) {
//				jointAction[i] = this.actionTypes[i].allApplicableActions(saStates[i])
//			}
//			 */
//			return allActions;
//		}
//
//		//private static List<Action> allApplicableActionsHelper(){}
//
//	}
//
//
//	private SADomain[] domains;
//
//	@Override
//	public Domain generateDomain() {
//		SADomain domain = new SADomain();
//
//		for(int i=0;i<domains.length;i++) {
//			for(ActionType saActionType : domains[i].getActionTypes()) {
//				//saActionType.
//			}			
//		}
//
//		/*
//		domain.addActionTypes(
//				new UniversalActionType(ACTION_NORTH),
//				new UniversalActionType(ACTION_SOUTH),
//				new UniversalActionType(ACTION_EAST),
//				new UniversalActionType(ACTION_WEST));
//		 */
//		//GridWorldStateModel smodel = new GridWorldStateModel();
//		//RewardFunction rf = new ExampleRF(this.goalx, this.goaly);
//		//TerminalFunction tf = new ExampleTF(this.goalx, this.goaly);
//
//		//domain.setModel(new FactoredModel(smodel, rf, tf));
//
//		return domain;
//	}
//
//}
