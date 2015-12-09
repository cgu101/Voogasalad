package authoring.model.triggers.selfconditions;

import java.util.Random;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import authoring.model.triggers.selftriggers.AActorSelfTrigger;

public class PercentChance extends AActorSelfTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -1184609160488448329L;
	private static final int MULTIPLIER = 100;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor actor) {
		Double percentChance = (Double) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0");
		Random generator = new Random();
		Double random = generator.nextDouble() * MULTIPLIER;
		return Double.compare(random, percentChance) < 0 ? true : false;
	}
}
