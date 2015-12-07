package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

public abstract class AChangeProperty extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3550470371445425654L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		String name = (String) parameters.getParameter("property0");
		Double value = actor.getPropertyValue(name);
		Double operand = (Double) parameters.getParameter("value0");
		Double extremum = (Double) parameters.getParameter("value1");
		actor.setProperty(name, calculateValue(value, operand, extremum));
	}

	protected abstract Double calculateValue(Double value, Double operand, Double extremum);
}
