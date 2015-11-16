package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class ShootBullet extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {
		Property<Double> angle = (Property<Double>) actor.getProperties().getComponents().get("angle");
		Property<Double> x = (Property<Double>) actor.getProperties().getComponents().get("xlocation");
		Property<Double> y = (Property<Double>) actor.getProperties().getComponents().get("ylocation");

		Actor bullet = createBullet(angle, x, y);
		actorGroup.addToGroup("bullet", bullet);
	}



	private Actor createBullet(Property<Double> angle, Property<Double> x, Property<Double> y) {
		Property<Double> angleB = new Property<Double>(angle.getUniqueID(), angle.getValue());
		Property<Double> xB = new Property<Double>(x.getUniqueID(), x.getValue());
		Property<Double> yB = new Property<Double>(y.getUniqueID(), y.getValue());

		Bundle<Property<?>> propBundle = new Bundle<Property<?>>();
		propBundle.add(angleB);
		propBundle.add(xB);
		propBundle.add(yB);

		Actor bullet = new Actor(propBundle, "bullet");
		return bullet;
	}
}