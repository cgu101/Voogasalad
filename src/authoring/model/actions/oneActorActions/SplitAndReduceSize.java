package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actions.twoActorActions.MoveActorsToAvoidCollisions;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 *
 */
public class SplitAndReduceSize extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
//		Create a copy of the property Bundle and create 2 new actors with these properties
		Double size = ((Property<Double>)actor.getProperty("size")).getValue();
		Double health = ((Property<Double>)actor.getProperty("health")).getValue();
		Double angle = ((Property<Double>)actor.getProperty("angle")).getValue();
		
		Actor futureActor1 = new Actor(actor.getProperties(), actor.getUniqueID()+".baby1");
		Actor futureActor2 = new Actor(actor.getProperties(), actor.getUniqueID()+".baby2");
		
		((Property<Double>)futureActor1.getProperty("size")).setValue(size/2);
		((Property<Double>)futureActor2.getProperty("size")).setValue(size/2);
		((Property<Double>)futureActor1.getProperty("health")).setValue(health/2);
		((Property<Double>)futureActor2.getProperty("health")).setValue(health/2);
		((Property<Double>)futureActor1.getProperty("angle")).setValue(angle+45);
		((Property<Double>)futureActor2.getProperty("angle")).setValue(angle-45);
		
		new MoveActorsToAvoidCollisions().move(futureActor1, futureActor2, size);
		ActorGroups actorGroup = state.getActorMap();
//		System.out.println("Change implementation to change the ID of the new children. "
//							+ "And change some property so that the children are not the exact same (change size and angle?)");
		
		
		// TODO: Change angles?  So that new actors aren't headed in the exact same direction side by side.
		actorGroup.removeActor(futureActor2);		
		actorGroup.addActor(futureActor1);
		actorGroup.addActor(futureActor2);
		
		
		actorGroup.killActor(actor);
		actorGroup.createActor(futureActor1);
		actorGroup.createActor(futureActor2);
	}
}