package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class ReduceSize extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {
		Property<Double> sizeP = (Property<Double>) actor.getProperties().getComponents().get("size");
		Double size = sizeP.getValue();
		sizeP.setValue(--size);
	}
}