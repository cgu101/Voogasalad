package authoring.model.actions.oneActorActions;

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

		Actor bullet = createBullet(angle, x, y);
		ActorGroups actorGroup = state.getActorMap();
		actorGroup.addActor(bullet);
		
		
		actorGroup.createActor(bullet);
		
		//actorGroup.addActor(actor);
		//^I think we need to add this line.  If this is the only action called on this actor, 
		// then the actor will disappear and only the bullet will persist in the next iteration
		// of the game.
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