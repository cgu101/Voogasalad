package authoring.tests;

import authoring.tests.interfaces.ICondition;
import authoring.tests.interfaces.IExecuter;
import authoring.tests.interfaces.IReaction;

public class Executer implements IExecuter {

	private IReaction reaction;
	private ICondition condition;
	
	public Executer (ICondition condition, IReaction reaction) {
		this.condition = condition;
		this.reaction = reaction;
	}
	
	@Override
	public void react() {
		reaction.react();
	}

	@Override
	public boolean testCondition() {
		return condition.testCondition();
	}

}
