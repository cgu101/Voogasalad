package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.AActorSelfTrigger;

public class TrueSelfTrigger extends AActorSelfTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -7049875777286733171L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor actor) {
		return true;
	}
}
