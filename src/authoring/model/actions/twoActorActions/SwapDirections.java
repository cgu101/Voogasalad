package authoring.model.actions.twoActorActions;

import authoring.model.actions.AActionTwoActors;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.triggers.externalconditions.CircleCollision;

/**
 * @author Inan
 *
 */
public class SwapDirections extends AActionTwoActors {

	@Override
	@SuppressWarnings("unchecked")
	public void run(ActorGroups actorGroup, Actor a, Actor b) {

		Actor aCopy = (Actor) a.getCopy();
		Actor bCopy = (Actor) b.getCopy();

		Property<Double> a_angleP = (Property<Double>) aCopy.getProperties().getComponents().get("angle");
		Property<Double> b_angleP = (Property<Double>) bCopy.getProperties().getComponents().get("angle");

		a_angleP.setValue(((Property<Double>) b.getProperties().getComponents().get("angle")).getValue());
		b_angleP.setValue(((Property<Double>) a.getProperties().getComponents().get("angle")).getValue());

		MoveActorsToAvoidCollisions adjust = new MoveActorsToAvoidCollisions();
		adjust.run(actorGroup, aCopy, bCopy);
		
		actorGroup.addActor(aCopy);
		actorGroup.addActor(bCopy);
	}

}
