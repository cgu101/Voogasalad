package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class DamageHealth extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {		
		Property<Double> health = (Property<Double>) actor.getProperty("health");
		Double h = health.getValue();
		
		Actor futureActor = (Actor) actor.getCopy();
		((Property<Double>) futureActor.getProperty("health")).setValue(--h);
		actorGroup.addActor(futureActor);
	}
}
