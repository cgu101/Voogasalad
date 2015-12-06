package authoring.model.triggers.externalconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.externaltriggers.AExternalTrigger;

public class TrueExternalTrigger extends AExternalTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -7010803213186332125L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor... actors) {
		return true;
	}
}
