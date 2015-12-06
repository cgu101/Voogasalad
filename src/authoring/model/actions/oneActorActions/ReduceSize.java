package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 *
 */
public class ReduceSize extends AOneActorAction {

	@Override
	public void run(Parameters parameters, State state, Actor a) {
		Double decrement = 1.0;

		Property<Double> size = (Property<Double>) a.getProperty("size");
		size.setValue(size.getValue() - decrement);

	}
}