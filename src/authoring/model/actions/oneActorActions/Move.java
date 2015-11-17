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

		Double angle = ((Property<Double>) actor.getProperty("angle")).getValue();
		Double speed = ((Property<Double>) actor.getProperty("speed")).getValue();
		Double x = ((Property<Double>) actor.getProperty("xlocation")).getValue();
		Double y = ((Property<Double>) actor.getProperty("ylocation")).getValue();
		
		x = x + Math.cos(Math.toRadians(angle)) * speed; 
		y = y + Math.cos(Math.toRadians(angle)) * speed;

		
		Actor futureActor = (Actor) actor.getCopy();
		((Property<Double>)futureActor.getProperty("xlocation")).setValue(x);
		((Property<Double>)futureActor.getProperty("ylocation")).setValue(y);
		actorGroup.addActor(futureActor);

		System.out.println(this.getClass().getName()+ " Moved Actor!");
	}
}
