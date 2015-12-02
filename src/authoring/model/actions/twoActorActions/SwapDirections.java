package authoring.model.actions.twoActorActions;

import authoring.model.actions.AActionTwoActors;
import authoring.model.actions.ActionTriggerHelper;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class SwapDirections extends AActionTwoActors {

	@Override
	@SuppressWarnings("unchecked")
	public void run(ActorGroups actorGroup, Actor a, Actor b) {

		Property<Double> a_angleP = (Property<Double>) a.getProperties().getComponents().get("angle");
		Property<Double> b_angleP = (Property<Double>) b.getProperties().getComponents().get("angle");

		Double temp = a_angleP.getValue();
		a_angleP.setValue(b_angleP.getValue());
		b_angleP.setValue(temp);

		new MoveActorsToAvoidCollisions().run(actorGroup, a, b);
		
		if (ActionTriggerHelper.collision(a, b)) {
			System.out.println("STILL COLLIDING AFTER ADJUSTMENT");
		}
		
		actorGroup.addActor(a);
		actorGroup.addActor(b);
	}

}
