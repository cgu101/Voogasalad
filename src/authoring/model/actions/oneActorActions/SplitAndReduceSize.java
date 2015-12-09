package authoring.model.actions.oneActorActions;

import authoring.files.properties.ActorProperties;
import authoring.model.ActionTriggerHelper;
import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

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
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		Double size = actor.getPropertyValue(ActorProperties.SIZE.getKey());
		Double health = actor.getPropertyValue(ActorProperties.HEALTH.getKey());
		Double angle = actor.getPropertyValue(ActorProperties.ANGLE.getKey());
		Double width = actor.getPropertyValue(ActorProperties.WIDTH.getKey());
		Double height = actor.getPropertyValue(ActorProperties.HEIGHT.getKey());
		

		
		Actor futureActor1 = new Actor(new Bundle(actor.getProperties()), ActionTriggerHelper.createActorID() + "1");
		Actor futureActor2 = new Actor(new Bundle(actor.getProperties()), ActionTriggerHelper.createActorID() + "2");
		futureActor1.setProperty(ActorProperties.SIZE.getKey(), size / 2);
		futureActor2.setProperty(ActorProperties.SIZE.getKey(), size / 2);
		futureActor1.setProperty(ActorProperties.HEALTH.getKey(), health / 2);
		futureActor2.setProperty(ActorProperties.HEALTH.getKey(), health / 2);
		futureActor1.setProperty(ActorProperties.ANGLE.getKey(), angle + 45);
		futureActor2.setProperty(ActorProperties.ANGLE.getKey(), angle - 45);
		futureActor1.setProperty(ActorProperties.WIDTH.getKey(), width / 2);
		futureActor2.setProperty(ActorProperties.WIDTH.getKey(), width / 2);
		futureActor1.setProperty(ActorProperties.HEIGHT.getKey(), height / 2);
		futureActor2.setProperty(ActorProperties.HEIGHT.getKey(), height / 2);
		
		ActionTriggerHelper.moveToAvoidCollision(futureActor1, futureActor2);
		


		ActorGroups actorGroups = state.getActorMap();
		actorGroups.killActor(actor);
		actorGroups.createActor(futureActor1);
		actorGroups.createActor(futureActor2);
	}
	
}