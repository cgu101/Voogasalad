package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class RotateClockwise extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {

		Double angle = ((Property<Double>) actor.getProperty("angle")).getValue();
		
		Actor futureActor = (Actor) actor.getCopy();
		((Property<Double>)futureActor.getProperty("angle")).setValue((angle-1) % 360);
		actorGroup.addActor(futureActor);
	}
}
