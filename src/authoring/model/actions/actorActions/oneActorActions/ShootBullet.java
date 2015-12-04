package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;

/**
 * @author Inan and Tyler
 *
 */
public class ShootBullet<V> extends AOneActorAction<V>{
	private static int bulletCount = 0;


	@SuppressWarnings("unchecked")
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor actor) {
		Property<Double> angle = (Property<Double>) actor.getProperties().getComponents().get("angle");
		Property<Double> x = (Property<Double>) actor.getProperties().getComponents().get("xLocation");
		Property<Double> y = (Property<Double>) actor.getProperties().getComponents().get("yLocation");
		Property<Double> size = (Property<Double>) actor.getProperties().getComponents().get("size");

		Actor bullet = createBullet(angle, x, y, size);
		actorGroups.createActor(bullet);
	}


	private Actor createBullet(Property<Double> angle, Property<Double> x, Property<Double> y, Property<Double> size) {
		Property<Double> sizeB = new Property<Double>("size", 1.0);
		Property<String> imageB = new Property<String>("image", "rcd.jpg");
		Property<String> groupIDB = new Property<String>("groupID", "bullet");
		Property<Double> angleB = new Property<Double>(angle.getUniqueID(), angle.getValue());
		
		Double dist = sizeB.getValue() + size.getValue() + 0.01;
		Property<Double> xB = new Property<Double>(x.getUniqueID(), x.getValue() + Math.cos(Math.toRadians(angle.getValue()))*dist);
		Property<Double> yB = new Property<Double>(y.getUniqueID(), y.getValue() + Math.sin(Math.toRadians(angle.getValue()))*dist);
		Property<Double> speedB = new Property<Double>("speed", 50.0);
		
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

		Actor bullet = new Actor(propBundle, "bullet" + bulletCount++);
		return bullet;
	}

}