package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

public class DecreaseSpeed extends AActionOneActor {

	@SuppressWarnings("unchecked")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {

		Double increment = 0.5;

		Property<Double> speed = (Property<Double>) actor.getProperty("speed");
		speed.setValue(speed.getValue() - increment);
		
		double minSpeed = 0;
		if (speed.getValue() < minSpeed) {
			speed.setValue(minSpeed);
		}
		
		ActorGroups actorGroup = state.getActorMap();
		actorGroup.addActor(actor);
	}
}
