package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class Move extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {
		Double angle = ((Property<Double>) actor.getProperties().getComponents().get("angle")).getValue();
		Double speed = ((Property<Double>) actor.getProperties().getComponents().get("angle")).getValue();

		Property<Double> xP = (Property<Double>) actor.getProperties().getComponents().get("xLocation");
		Property<Double> yP = (Property<Double>) actor.getProperties().getComponents().get("yLocation");
		Double x = xP.getValue();
		Double y = yP.getValue();
		
		x = Math.cos(Math.toRadians(angle)) * speed; 
		y = Math.cos(Math.toRadians(angle)) * speed; 
		
		xP.setValue(x);
		yP.setValue(y);

		System.out.println(this.getClass().getName()+ " Moved Actor!");
	}
}
