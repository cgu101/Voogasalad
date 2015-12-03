package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class PropertyEquals extends ASelfTrigger {
	private static final int PROPERTY_INDEX = 0;
	private static final int VALUE_INDEX = 0;
	private static final String STRING_LIST = "string";
	private static final String DOUBLE_LIST = "double";

	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor actor) {
		String propertyName = (String) parameters.getParameterList(STRING_LIST).get(PROPERTY_INDEX);
		Double value = (Double) parameters.getParameterList(DOUBLE_LIST).get(VALUE_INDEX);
		Double property = (Double) actor.getProperty(propertyName).getValue();
		return property == value;
	}
}
