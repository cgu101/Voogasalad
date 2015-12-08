package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.ActionMail;
import authoring.model.actors.ActionType;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;
import network.framework.format.Mail;
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;

public class NextLevel extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 4542051448186712167L;

	private String levelKey = PropertyKeyResource.getKey(PropertyKey.LEVEL_ID_KEY);
	private String nextLevelKey = PropertyKeyResource.getKey(PropertyKey.NEXT_LEVEL_KEY);

	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor a) {
//		ActionMail mail = new ActionMail(ActionType.NEXT_LEVEL, state);
//		a.updateObservers(mail);
		state.getPropertyBundle().add(new Property<String>(levelKey, nextLevelKey));
	}
}
