package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class Split extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {
//		Create a copy of the property Bundle and create 2 new actors with these properties
//		Bundle<Property<?>> copy1 = new Bundle<Property<?>>(actor.getProperties());
//		Bundle<Property<?>> copy2 = new Bundle<Property<?>>(actor.getProperties());
	}

	@Override
	public String getUniqueID() {
		return this.getClass().getName();
	}
}