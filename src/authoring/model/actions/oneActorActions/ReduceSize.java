package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

/**
 * @author Inan
 *
 */
public class ReduceSize extends AOneActorAction {

	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		Double decrement = 1.0;

		Property<Double> size = (Property<Double>) actor.getProperty("size");
		size.setValue(size.getValue() - decrement);

	}
}