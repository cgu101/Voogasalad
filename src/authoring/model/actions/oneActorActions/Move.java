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
		Double x = ((Property<Double>) actor.getProperty("xLocation")).getValue();
		Double y = ((Property<Double>) actor.getProperty("yLocation")).getValue();
		
		x = x + Math.cos(Math.toRadians(angle)) * speed; 
		y = y + Math.sin(Math.toRadians(angle)) * speed;

		
		Actor futureActor = (Actor) actor.getCopy();
		((Property<Double>)futureActor.getProperty("xLocation")).setValue(x);
		((Property<Double>)futureActor.getProperty("yLocation")).setValue(y);
		actorGroup.addActor(futureActor);

		System.out.println(this.getClass().getName()+ " Moved Actor!");
	}
}
