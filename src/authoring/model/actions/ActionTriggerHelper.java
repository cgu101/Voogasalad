package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.properties.Property;

public final class ActionTriggerHelper {
	
	@SuppressWarnings("unchecked")
	public static Double distance(Actor a, Actor b) {
		Double xA = ((Property<Double>) a.getProperties().getComponents().get("xLocation")).getValue();
		Double yA = ((Property<Double>) a.getProperties().getComponents().get("yLocation")).getValue();
		Double xB = ((Property<Double>) b.getProperties().getComponents().get("xLocation")).getValue();
		Double yB = ((Property<Double>) b.getProperties().getComponents().get("yLocation")).getValue();
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}

	@SuppressWarnings("unchecked")
	public static boolean collision(Actor a, Actor b) {
		Double sizeA = ((Property<Double>) a.getProperties().getComponents().get("size")).getValue();
		Double sizeB = ((Property<Double>) b.getProperties().getComponents().get("size")).getValue();

		double radiusSum = sizeA + sizeB;
		double distance = ActionTriggerHelper.distance(a, b);

		if (Double.compare(distance, radiusSum) <= 0) {
			return true;
		}
		return false;
	}
}