package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import authoring.model.triggers.selftriggers.AStateTrigger;
import engine.State;

public class NumberOfActors extends AStateTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -2651964358927085580L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, State state, Actor actor) {
		String groupID = (String) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0");
		double numberD = (double) parameters.getParameter(ParametersKey.PARAM_PREFIX + "1");
		int number = (int) numberD;
		return state.getActorMap().getGroup(groupID).getSize() == number;
	}
}
