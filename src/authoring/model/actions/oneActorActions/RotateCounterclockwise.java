package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

public class RotateCounterclockwise extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {

		Double rotation = 20.0;
		
		Property<Double> angle = (Property<Double>) actor.getProperty("angle");
		angle.setValue((angle.getValue() + rotation) % 360);
		ActorGroups actorGroup = state.getActorMap();
		actorGroup.addActor(actor);
	}
}
