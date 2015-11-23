package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class IncreaseSpeed extends AActionOneActor {

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {

		Double increment = 1.0;

		Property<Double> speed = (Property<Double>) actor.getProperty("speed");
		speed.setValue(speed.getValue() + increment);

		actorGroup.addActor(actor);
	}
}