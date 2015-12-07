package authoring.model;

import java.sql.Timestamp;
import java.util.Calendar;
import authoring.files.properties.ActorProperties;
import authoring.model.actors.Actor;

/**
 * @author Inan
 *
 */
public final class ActionTriggerHelper {
	public static Double distance(Actor a, Actor b) {
		Double xA = a.getPropertyValue(ActorProperties.X_LOCATION.getKey());
		Double yA = a.getPropertyValue(ActorProperties.Y_LOCATION.getKey());
		Double xB = b.getPropertyValue(ActorProperties.X_LOCATION.getKey());
		Double yB = b.getPropertyValue(ActorProperties.Y_LOCATION.getKey());
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}

	public static boolean collision(Actor a, Actor b) {
		Double distance = distance(a, b);
		Double sizeA = a.getPropertyValue(ActorProperties.SIZE.getKey());
		Double sizeB = b.getPropertyValue(ActorProperties.SIZE.getKey());
		Double radiusSum = sizeA + sizeB;
		return Double.compare(distance, radiusSum) <= 0;
	}
	
	public static String createActorID(){
		Timestamp currentTimeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		return currentTimeStamp.toString();
	}

	public static void moveToAvoidCollision(Actor a, Actor b) {
		Double sizeA = a.getPropertyValue(ActorProperties.SIZE.getKey());
		Double sizeB = b.getPropertyValue(ActorProperties.SIZE.getKey());
		Double difference = (sizeA + sizeB) - distance(a, b);
		
		Double xA = a.getPropertyValue(ActorProperties.X_LOCATION.getKey());
		Double yA = a.getPropertyValue(ActorProperties.Y_LOCATION.getKey());
		Double xB = b.getPropertyValue(ActorProperties.X_LOCATION.getKey());
		Double yB = b.getPropertyValue(ActorProperties.Y_LOCATION.getKey());

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
		
		a.setProperty(ActorProperties.X_LOCATION.getKey(), xA);
		a.setProperty(ActorProperties.Y_LOCATION.getKey(), yA);
		b.setProperty(ActorProperties.X_LOCATION.getKey(), xB);
		b.setProperty(ActorProperties.Y_LOCATION.getKey(), yB);
	}
}
