package authoring.model.triggers.externaltriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;

public abstract class ATwoActorExternalTrigger extends AExternalTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3850880728871052401L;

	@SuppressWarnings("rawtypes")
	public abstract boolean condition(Parameters parameters, Actor a, Actor b);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor... actors) {
		return condition(parameters, actors[0], actors[1]);
	}
}
