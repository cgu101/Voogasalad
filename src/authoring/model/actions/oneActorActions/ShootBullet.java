package authoring.model.actions.oneActorActions;

import java.sql.Timestamp;
import java.util.Calendar;
import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 *
 */
public class ShootBullet extends AActionOneActor{
	@SuppressWarnings("unchecked")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		Property<Double> angle = (Property<Double>) actor.getProperties().getComponents().get("angle");
		Property<Double> x = (Property<Double>) actor.getProperties().getComponents().get("xLocation");
		Property<Double> y = (Property<Double>) actor.getProperties().getComponents().get("yLocation");
		Property<Double> size = (Property<Double>) actor.getProperties().getComponents().get("size");

		Actor bullet = createBullet(angle, x, y, size);
		ActorGroups actorGroup = state.getActorMap();
		
		actorGroup.addActor(bullet);
		actorGroup.addActor(actor);
		
		actorGroup.createActor(bullet);
		
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
		
		Timestamp currentTimeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		String timeString = currentTimeStamp.toString();
		Actor bullet = new Actor(propBundle, "bullet" + timeString);
		return bullet;
	}
}