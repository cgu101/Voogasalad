package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

public abstract class AActionProperty extends AActionOneActor {
	private static final int PROPERTY_INDEX = 0;
	private static final int VALUE_INDEX = 0;
	private static final String STRING_LIST = "string";
	private static final String DOUBLE_LIST = "double";

	@SuppressWarnings("unchecked")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		String propertyName = (String) parameters.getParameterList(STRING_LIST).get(PROPERTY_INDEX);
		Double value = (Double) parameters.getParameterList(DOUBLE_LIST).get(VALUE_INDEX);
		Property<Double> property = (Property<Double>) actor.getProperty(propertyName);
		ExecuteOperation(value, property);
		
		ActorGroups actorGroup = state.getActorMap();
		actorGroup.addActor(actor);
	}

	protected abstract void ExecuteOperation(Double value, Property<Double> property);
}
