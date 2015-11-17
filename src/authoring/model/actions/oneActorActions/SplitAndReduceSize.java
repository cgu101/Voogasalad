package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class SplitAndReduceSize extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {
//		Create a copy of the property Bundle and create 2 new actors with these properties
		Double size = ((Property<Double>)actor.getProperty("size")).getValue();
		
		Actor futureActor1 = new Actor(actor.getProperties(), actor.getUniqueID()+".baby1");
		Actor futureActor2 = new Actor(actor.getProperties(), actor.getUniqueID()+".baby2");
		
		((Property<Double>)futureActor1.getProperty("size")).setValue(--size);
		((Property<Double>)futureActor2.getProperty("size")).setValue(--size);
		
		System.out.println("Change implementation to change the ID of the new children. "
							+ "And change some property so that the children are not the exact same (change size and angle?)");
		
		actorGroup.removeActor(futureActor2);
		actorGroup.addActor(futureActor1);
		actorGroup.addActor(futureActor2);
		
	}
}