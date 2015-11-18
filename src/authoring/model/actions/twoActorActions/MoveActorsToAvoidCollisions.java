package authoring.model.actions.twoActorActions;

import authoring.model.actions.AActionTwoActors;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.triggers.externalconditions.CircleCollision;

public class MoveActorsToAvoidCollisions extends AActionTwoActors {

	@Override
	@SuppressWarnings("unchecked")
	public void run(ActorGroups actorGroup, Actor a, Actor b) {
		Actor aCopy = (Actor) a.getCopy();
		Actor bCopy = (Actor) b.getCopy();

		if (checkIfCollided(actorGroup, aCopy, bCopy)) {
			Double sizeA = ((Property<Double>) aCopy.getProperties().getComponents().get("size")).getValue();
			Double sizeB = ((Property<Double>) bCopy.getProperties().getComponents().get("size")).getValue();
			Double difference = (sizeA + sizeB) - distance(aCopy, bCopy);
			move(aCopy, bCopy, difference);
		}

		actorGroup.addActor(aCopy);
		actorGroup.addActor(bCopy);
	}

	@SuppressWarnings("unchecked")
	public void move(Actor a, Actor b, Double difference) {
		Double xA = ((Property<Double>) a.getProperties().getComponents().get("xLocation")).getValue();
		Double yA = ((Property<Double>) a.getProperties().getComponents().get("yLocation")).getValue();
		Double xB = ((Property<Double>) b.getProperties().getComponents().get("xLocation")).getValue();
		Double yB = ((Property<Double>) b.getProperties().getComponents().get("yLocation")).getValue();

		Double slope = (yB - yA) / (xB - xA);
		Double angleAB = Math.atan(slope);

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

		((Property<Double>) a.getProperties().getComponents().get("xLocation")).setValue(xA);
		((Property<Double>) a.getProperties().getComponents().get("yLocation")).setValue(yA);
		((Property<Double>) b.getProperties().getComponents().get("xLocation")).setValue(xB);
		((Property<Double>) b.getProperties().getComponents().get("yLocation")).setValue(yB);
	}

	private boolean checkIfCollided(ActorGroups actorGroup, Actor a, Actor b) {
		CircleCollision circleCollision = new CircleCollision();
		return circleCollision.condition(null, actorGroup, null, new Actor[] { a, b });
	}
}
