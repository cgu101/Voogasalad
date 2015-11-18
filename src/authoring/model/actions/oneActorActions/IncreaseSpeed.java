package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class IncreaseSpeed extends AActionOneActor {

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {

		Double speed = ((Property<Double>) actor.getProperty("speed")).getValue();

		Actor futureActor = (Actor) actor.getCopy();
		((Property<Double>) futureActor.getProperty("speed")).setValue((speed + 1));
		actorGroup.addActor(futureActor);
	}
}
