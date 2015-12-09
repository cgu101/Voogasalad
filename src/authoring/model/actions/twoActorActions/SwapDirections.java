package authoring.model.actions.twoActorActions;

import authoring.files.properties.ActorProperties;
import authoring.model.ActionTriggerHelper;
import authoring.model.actions.ATwoActorAction;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

/**
 * @author Inan
 *
 */
public class SwapDirections extends ATwoActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 4981001366273988038L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor a, Actor b) {
		Double angleA = a.getPropertyValue(ActorProperties.ANGLE.getKey());
		Double angleB = b.getPropertyValue(ActorProperties.ANGLE.getKey());
		a.setProperty(ActorProperties.ANGLE.getKey(), angleB);
		b.setProperty(ActorProperties.ANGLE.getKey(), angleA);
		ActionTriggerHelper.moveToAvoidCollision(a, b);
	}
}
