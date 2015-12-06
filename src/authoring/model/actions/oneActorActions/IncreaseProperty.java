package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

public class IncreaseProperty extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3612830585385260543L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		// Double increment = (Double) value;
		// property.setValue((Double)property.getValue() + increment);
	}
}
