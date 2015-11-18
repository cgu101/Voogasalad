package authoring.model.actions.twoActorActions;

import authoring.model.actions.AActionTwoActors;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class SwapDirections extends AActionTwoActors{

	@Override
	@SuppressWarnings("unchecked")
	public void run(ActorGroups actorGroup, Actor a, Actor b) {
		Property<Double> a_angleP = (Property<Double>) a.getProperties().getComponents().get("angle");
		Property<Double> b_angleP = (Property<Double>) b.getProperties().getComponents().get("angle");
		Property<Double> a_xP = (Property<Double>) a.getProperties().getComponents().get("xLocation");
		Property<Double> a_yP = (Property<Double>) a.getProperties().getComponents().get("yLocation");
		Property<Double> b_xP = (Property<Double>) b.getProperties().getComponents().get("xLocation");
		Property<Double> b_yP = (Property<Double>) b.getProperties().getComponents().get("yLocation");
		
		Double xA = a_xP.getValue();
		Double yA = a_yP.getValue();
		Double xB = b_xP.getValue();
		Double yB = b_yP.getValue();
		Double sizeA = ((Property<Double>) a.getProperties().getComponents().get("size")).getValue();
		Double sizeB = ((Property<Double>) b.getProperties().getComponents().get("size")).getValue();
		
		double radiusSum = sizeA + sizeB;
		double distance = distance(a, b);
		double difference = radiusSum - distance;
		
		double slope = (yB - yA) / (xB - xA);
		double angleAB = Math.atan(slope);
		
		if (xA < xB) {
			xA = xA - (difference/2 + 1) * Math.cos(angleAB);
			xB = xB + (difference/2 + 1) * Math.cos(angleAB);
		} else {
			xA = xA + (difference/2 + 1) * Math.cos(angleAB);
			xB = xB - (difference/2 + 1) * Math.cos(angleAB);
		}
		
		if (yA < yB) {
			yA = yA - (difference/2 + 1) * Math.abs(Math.sin(angleAB));
			yB = yB + (difference/2 + 1) * Math.abs(Math.sin(angleAB));
		} else {
			yA = yA + (difference/2 + 1) * Math.abs(Math.sin(angleAB));
			yB = yB - (difference/2 + 1) * Math.abs(Math.sin(angleAB));
		}

		a_xP.setValue(xA);
		a_yP.setValue(yA);
		b_xP.setValue(xB);
		b_yP.setValue(yB);
		
		Double temp = a_angleP.getValue();
		a_angleP.setValue(b_angleP.getValue());
		b_angleP.setValue(temp);
		
		actorGroup.addActor(a);
		actorGroup.addActor(b);
	}
	
	@SuppressWarnings("unchecked")
	public double distance(Actor actorA, Actor actorB) {
		Double xA = ((Property<Double>) actorA.getProperties().getComponents().get("xLocation")).getValue();
		Double yA = ((Property<Double>) actorA.getProperties().getComponents().get("yLocation")).getValue();
		Double xB = ((Property<Double>) actorB.getProperties().getComponents().get("xLocation")).getValue();
		Double yB = ((Property<Double>) actorB.getProperties().getComponents().get("yLocation")).getValue();
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}

}
