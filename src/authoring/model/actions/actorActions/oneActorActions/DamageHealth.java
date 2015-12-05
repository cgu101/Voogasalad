package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 * @param <V>
 *
 */
public class DamageHealth<V> extends AOneActorAction<V> {

	@SuppressWarnings("unchecked")
	@Override
	public void run(Map parameters_values, ActorGroups actorGroups, Actor actor) {
		Double decrement = 1.0;

		Property<Double> health = (Property<Double>) actor.getProperty("health");
		health.setValue(health.getValue() - decrement);
	}
}
