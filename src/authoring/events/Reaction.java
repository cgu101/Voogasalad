package authoring.events;

import authoring.events.interfaces.IAction;
import authoring.events.interfaces.IReaction;

public class Reaction implements IReaction {
	private IAction action;
	private String[] parameters;
	
	public Reaction (IAction action, String... parameters) {
		this.action = action;
		this.parameters = parameters;
	}

	@Override
	public void react() {
		action.run(parameters);		
	}
}
