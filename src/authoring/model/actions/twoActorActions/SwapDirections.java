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

		if (checkIfCollided(actorGroup, aCopy, bCopy)) {
			Double sizeA = ((Property<Double>) aCopy.getProperties().getComponents().get("size")).getValue();
			Double sizeB = ((Property<Double>) bCopy.getProperties().getComponents().get("size")).getValue();
			Double difference = (sizeA + sizeB) - distance(aCopy, bCopy);
			moveActorsToAvoidCollision(aCopy, bCopy, difference);
		}

		Property<Double> a_angleP = (Property<Double>) aCopy.getProperties().getComponents().get("angle");
		Property<Double> b_angleP = (Property<Double>) bCopy.getProperties().getComponents().get("angle");

		a_angleP.setValue(((Property<Double>) b.getProperties().getComponents().get("angle")).getValue());
		b_angleP.setValue(((Property<Double>) a.getProperties().getComponents().get("angle")).getValue());

		actorGroup.addActor(aCopy);
		actorGroup.addActor(bCopy);
	}

	private boolean checkIfCollided(ActorGroups actorGroup, Actor a, Actor b) {
		CircleCollision circleCollision = new CircleCollision();
		return circleCollision.condition(null, actorGroup, null, new Actor[] { a, b });
	}

	@SuppressWarnings("unchecked")
	private void moveActorsToAvoidCollision(Actor a, Actor b, Double difference) {
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

	@SuppressWarnings("unchecked")
	public Double distance(Actor actorA, Actor actorB) {
		Double xA = ((Property<Double>) actorA.getProperties().getComponents().get("xLocation")).getValue();
		Double yA = ((Property<Double>) actorA.getProperties().getComponents().get("yLocation")).getValue();
		Double xB = ((Property<Double>) actorB.getProperties().getComponents().get("xLocation")).getValue();
		Double yB = ((Property<Double>) actorB.getProperties().getComponents().get("yLocation")).getValue();
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}
}
