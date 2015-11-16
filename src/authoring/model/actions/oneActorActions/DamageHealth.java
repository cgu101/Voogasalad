package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actions.ActorGroups;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class DamageHealth extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {
		Property<Integer> health = (Property<Integer>) actor.getProperties().getComponents().get("health");
		Integer h = health.getValue();
		health.setValue(--h);
	}

	@Override
	public String getUniqueID() {
		return this.getClass().getName();
	}
}
