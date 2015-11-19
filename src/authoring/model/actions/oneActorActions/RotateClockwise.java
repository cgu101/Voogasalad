package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class RotateClockwise extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {

		Double rotation = 1.0;
		
		Property<Double> angle = (Property<Double>) actor.getProperty("angle");
		angle.setValue((angle.getValue() - rotation) % 360);
		
		actorGroup.addActor(actor);
	}
}
