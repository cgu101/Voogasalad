package authoring.model.actions.twoActorActions;

import authoring.model.actions.AActionTwoActors;
import authoring.model.actions.ActionTriggerHelper;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.triggers.externalconditions.CircleCollision;

/**
 * @author Tyler
 *
 */
public class MoveActorsToAvoidCollisions extends AActionTwoActors {

	@Override
	@SuppressWarnings("unchecked")
	public void run(ActorGroups actorGroup, Actor a, Actor b) {

		if (checkIfCollided(actorGroup, a, b)) {
			Double sizeA = ((Property<Double>) a.getProperties().getComponents().get("size")).getValue();
			Double sizeB = ((Property<Double>) b.getProperties().getComponents().get("size")).getValue();
			Double difference = (sizeA + sizeB) - ActionTriggerHelper.distance(a, b);
			move(a, b, difference);
		}

		actorGroup.addActor(a);
		actorGroup.addActor(b);
	}

	@SuppressWarnings("unchecked")
	public void move(Actor a, Actor b, Double difference) {
		Double xA = ((Property<Double>) a.getProperties().getComponents().get("xLocation")).getValue();
		Double yA = ((Property<Double>) a.getProperties().getComponents().get("yLocation")).getValue();
		Double xB = ((Property<Double>) b.getProperties().getComponents().get("xLocation")).getValue();
		Double yB = ((Property<Double>) b.getProperties().getComponents().get("yLocation")).getValue();

//		System.out.println("A_start: (" + xA + ", " + yA + ")");
//		System.out.println("B_start: (" + xB + ", " + yB + ")");
		
		Double angleAB;
		if (xA == xB && yA == yB) {
			angleAB = 0.0;
		} else {
		Double slope = (yB - yA) / (xB - xA);
		angleAB = Math.atan(slope);
		}

		if (xA < xB) {
			xA = xA - (difference / 2 + 1) * Math.cos(angleAB);
			xB = xB + (difference / 2 + 1) * Math.cos(angleAB);
		} else {
			xA = xA + (difference / 2 + 1) * Math.cos(angleAB);
			xB = xB - (difference / 2 + 1) * Math.cos(angleAB);
		}

		if (yA < yB) {
			yA = yA - (difference / 2 + 1) * Math.abs(Math.sin(angleAB));
			yB = yB + (difference / 2 + 1) * Math.abs(Math.sin(angleAB));
		} else {
			yA = yA + (difference / 2 + 1) * Math.abs(Math.sin(angleAB));
			yB = yB - (difference / 2 + 1) * Math.abs(Math.sin(angleAB));
		}
		
//		System.out.println("A_end: (" + xA + ", " + yA + ")");
//		System.out.println("B_end: (" + xB + ", " + yB + ")");
//
//		System.out.println("NOW THE DISTANCE BETWEEN ACTORS IS: " + distance(a,b));
		
		((Property<Double>) a.getProperties().getComponents().get("xLocation")).setValue(xA);
		((Property<Double>) a.getProperties().getComponents().get("yLocation")).setValue(yA);
		((Property<Double>) b.getProperties().getComponents().get("xLocation")).setValue(xB);
		((Property<Double>) b.getProperties().getComponents().get("yLocation")).setValue(yB);
	}

	private boolean checkIfCollided(ActorGroups actorGroup, Actor a, Actor b) {
		CircleCollision circleCollision = new CircleCollision();
		return circleCollision.condition(null, new Actor[] { a, b });
	}
}
