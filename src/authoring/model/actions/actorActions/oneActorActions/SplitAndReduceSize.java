package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actions.actorActions.twoActorActions.MoveActorsToAvoidCollisions;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import engine.State;

/**
 * @author Inan
 *
 */
public class SplitAndReduceSize<V> extends AOneActorAction<V>{

	@SuppressWarnings("unchecked")
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor actor) {
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
		

		actorGroups.killActor(actor);
		actorGroups.createActor(futureActor1);
		actorGroups.createActor(futureActor2);
	}
}