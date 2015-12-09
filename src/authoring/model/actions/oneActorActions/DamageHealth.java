package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

/**
 * @author Inan
 * @param <V>
 *
 */
public class DamageHealth extends AOneActorAction {

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		Double decrement = 1.0;

		Property<Double> health = (Property<Double>) actor.getProperty("health");
		health.setValue(health.getValue() - decrement);
	}
}
