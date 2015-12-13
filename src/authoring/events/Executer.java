package authoring.events;

import authoring.events.interfaces.ICondition;
import authoring.events.interfaces.IExecuter;
import authoring.events.interfaces.IReaction;

/**
 * @author Austin
 */

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
