package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;

public class DecreaseProperty extends AActionOneActor {
	
	private static final int PROPERTY_INDEX = 0;
	private static final int DECREMENT_INDEX = 0;

	@SuppressWarnings("unchecked")
	@Override
	public void run(Parameters parameters, ActorGroups actorGroup, Actor actor) {

		String propertyName = (String) parameters.getParameterList("string").get(PROPERTY_INDEX);
		Double decrement = (Double) parameters.getParameterList("double").get(DECREMENT_INDEX);
		
		Property<Double> property = (Property<Double>) actor.getProperty(propertyName);
		property.setValue(property.getValue() - decrement);

		actorGroup.addActor(actor);
	}
}
