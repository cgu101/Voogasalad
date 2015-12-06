package authoring.model.actions.actorActions.oneActorActions;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.ActionTriggerHelper;
import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actions.actorActions.twoActorActions.MoveActorsToAvoidCollisions;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class SplitAndReduceSize<V> extends AOneActorAction<V>{

	@SuppressWarnings("unchecked")
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor actor) {
//	Create a copy of the property Bundle and create 2 new actors with these properties
		Double size = ((Property<Double>)actor.getProperty(ActorProperties.SIZE.getKey())).getValue();
		Double health = ((Property<Double>)actor.getProperty(ActorProperties.HEALTH.getKey())).getValue();
		Double angle = ((Property<Double>)actor.getProperty(ActorProperties.ANGLE.getKey())).getValue();
		
		Actor futureActor1 = new Actor(actor.getProperties(), ActionTriggerHelper.createActorID());
		Actor futureActor2 = new Actor(actor.getProperties(), ActionTriggerHelper.createActorID());
		
		((Property<Double>)futureActor1.getProperty(ActorProperties.SIZE.getKey())).setValue(size/2);
		((Property<Double>)futureActor2.getProperty(ActorProperties.SIZE.getKey())).setValue(size/2);
		((Property<Double>)futureActor1.getProperty(ActorProperties.HEALTH.getKey())).setValue(health/2);
		((Property<Double>)futureActor2.getProperty(ActorProperties.HEALTH.getKey())).setValue(health/2);
		((Property<Double>)futureActor1.getProperty(ActorProperties.ANGLE.getKey())).setValue(angle+45);
		((Property<Double>)futureActor2.getProperty(ActorProperties.ANGLE.getKey())).setValue(angle-45);
		new MoveActorsToAvoidCollisions().move(futureActor1, futureActor2, size);
		

		actorGroups.killActor(actor);
		actorGroups.createActor(futureActor1);
		actorGroups.createActor(futureActor2);
	}
}