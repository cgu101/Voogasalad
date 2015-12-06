package authoring.model.actions.oneActorActions;

import authoring.files.properties.ActorProperties;
import authoring.model.ActionTriggerHelper;
import authoring.model.actions.AOneActorAction;
import authoring.model.actions.twoActorActions.MoveActorsToAvoidCollisions;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 *
 */
public class SplitAndReduceSize extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -6897408900633928326L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		Double size = actor.getPropertyValue(ActorProperties.SIZE.getKey());
		Double health = actor.getPropertyValue(ActorProperties.HEALTH.getKey());
		Double angle = actor.getPropertyValue(ActorProperties.ANGLE.getKey());
		
		Actor futureActor1 = new Actor(actor.getProperties(), ActionTriggerHelper.createActorID());
		Actor futureActor2 = new Actor(actor.getProperties(), ActionTriggerHelper.createActorID());
		futureActor1.setProperty(ActorProperties.SIZE.getKey(), size/2);
		futureActor2.setProperty(ActorProperties.SIZE.getKey(), size/2);
		futureActor1.setProperty(ActorProperties.HEALTH.getKey(), health/2);
		futureActor2.setProperty(ActorProperties.HEALTH.getKey(), health/2);
		futureActor1.setProperty(ActorProperties.ANGLE.getKey(), angle+45);
		futureActor2.setProperty(ActorProperties.ANGLE.getKey(), angle-45);
		new MoveActorsToAvoidCollisions().move(futureActor1, futureActor2, size);
		
		ActorGroups actorGroups = state.getActorMap();
		actorGroups.killActor(actor);
		actorGroups.createActor(futureActor1);
		actorGroups.createActor(futureActor2);
	}
}