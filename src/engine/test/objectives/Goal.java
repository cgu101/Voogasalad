package engine.test.objectives;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import authoring.tests.interfaces.IAction;
import authoring.tests.interfaces.IReaction;
import authoring.tests.interfaces.IWorker;

public class Goal<T> implements IWorker {

	private GoalState goalState;
	private String goalIdentifier;
	private Map<GoalState, IReaction> reactionMap;
	private Map<String, IAction> actionMap;
	private Map<Predicate<T>, GoalState> conditionMap;
	
	@Override
	public IAction getAction(String actionName) {
		return actionMap.get(actionName);
	}

	public Goal () {
		try {
			actionMap = this.getActionMap();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			actionMap = new HashMap<String, IAction>(); 
		}
		
		conditionMap = new HashMap<Predicate<T>, GoalState>();
		reactionMap = new HashMap<GoalState, IReaction>();
		this.goalState = GoalState.IDLE;
		this.goalIdentifier = null;
	}
	
	public void addReaction (GoalState gs, IReaction reaction) {
		reactionMap.put(gs, reaction);
	}
	
	public void addCondition (Predicate<T> condition, GoalState gs) {
		conditionMap.put(condition, gs);
	}
	
	private void doReactions () {
		IReaction myReaction = reactionMap.get(goalState);
		if (myReaction != null) {
			myReaction.react();
		}
	}
	
	public void update () {
		if (this.goalState == GoalState.ACTIVE) {
			doReactions();
		}
	}
}
