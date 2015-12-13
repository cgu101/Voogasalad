package authoring.model.actors;

import engine.State;

/**
 * @author Austin
 */
public class ActionMail {
	private ActionType actionType;
	private State state;
	
	public ActionMail (ActionType actionType, State state) {
		this.actionType = actionType;
		this.state = state;
	}
	
	public ActionType getActionType () {
		return actionType;
	}
	
	public State getState () {
		return state;
	}
}
