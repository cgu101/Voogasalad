package engine.test.objectives;

public enum GoalState {
	ACTIVE, OCCURRED, IDLE;
	
	public static GoalState getState (String goalState) {
		return GoalState.valueOf(goalState.toUpperCase());
	}
}
