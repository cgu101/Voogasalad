package authoring.model.actions.oneActorActions;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan and Tyler
 * @param <V>
 *
 */
public class ShootBullet<V> extends AOneActorAction {

	@SuppressWarnings("unchecked")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		Property<Double> angle = (Property<Double>) actor.getProperty(ActorProperties.ANGLE.getKey());
		Property<Double> x = (Property<Double>) actor.getProperty(ActorProperties.X_LOCATION.getKey());
		Property<Double> y = (Property<Double>) actor.getProperty(ActorProperties.Y_LOCATION.getKey());
		Property<Double> size = (Property<Double>) actor.getProperty(ActorProperties.SIZE.getKey());
		
		Actor bullet = createBullet(angle, x, y, size);
		state.getActorMap().createActor(bullet);
	}


	private Actor createBullet(Property<Double> angle, Property<Double> x, Property<Double> y, Property<Double> size) {
		Property<Double> sizeB = new Property<Double>(size.getUniqueID(), 1.0);
		Property<Double> angleB = new Property<Double>(angle.getUniqueID(), angle.getValue());
		Property<String> imageB = new Property<String>(ActorProperties.IMAGE.getKey(), "bullets.png");
		Property<String> groupIDB = new Property<String>(ActorProperties.GROUP_ID.getKey(), "bullet");
		
		Double dist = sizeB.getValue() + size.getValue() + 0.01;
		Property<Double> xB = new Property<Double>(x.getUniqueID(), x.getValue() + Math.cos(Math.toRadians(angle.getValue()))*dist);
		Property<Double> yB = new Property<Double>(y.getUniqueID(), y.getValue() + Math.sin(Math.toRadians(angle.getValue()))*dist);
		Property<Double> speedB = new Property<Double>(ActorProperties.SPEED.getKey(), 50.0);
		Property<Double> distanceB = new Property<Double>(ActorProperties.DISTANCE_TRAVELED.getKey(), 0.0);
		
//		System.out.println("MegaMan: (" + x.getValue() + " , " + y.getValue() + ")");
//		System.out.println(" Bullet: (" + xB.getValue() + " , " + yB.getValue() + ")");

		Bundle<Property<?>> propBundle = new Bundle<Property<?>>();
		propBundle.add(angleB);
		propBundle.add(xB);
		propBundle.add(yB);
		propBundle.add(imageB);
		propBundle.add(speedB);
		propBundle.add(sizeB);
		propBundle.add(groupIDB);
		propBundle.add(distanceB);
		
		Timestamp currentTimeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		String timeString = currentTimeStamp.toString();
		Actor bullet = new Actor(propBundle, "bullet" + timeString);
		return bullet;
	}
	

}