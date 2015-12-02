package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;

/**
 * @author Inan
 *
 */
public class DamageHealth extends AActionOneActor {

	@SuppressWarnings("unchecked")
	@Override
	public void run(Parameters parameters, ActorGroups actorGroup, Actor actor) {
		
		Double decrement = 1.0;

		Property<Double> health = (Property<Double>) actor.getProperty("health");
		health.setValue(health.getValue() - decrement);

		actorGroup.addActor(actor);
	}
}
