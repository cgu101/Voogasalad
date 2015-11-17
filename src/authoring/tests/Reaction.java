package authoring.tests;

import authoring.tests.interfaces.IAction;
import authoring.tests.interfaces.IReaction;

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
