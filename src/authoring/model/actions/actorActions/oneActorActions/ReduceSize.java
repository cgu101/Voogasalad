package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class ReduceSize<V> extends AOneActorAction<V>{

	@SuppressWarnings("unchecked")
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor a) {
		Double decrement = 1.0;

		Property<Double> size = (Property<Double>) a.getProperty("size");
		size.setValue(size.getValue() - decrement);

	}
}