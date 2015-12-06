package authoring.model.actions.twoActorActions;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.ATwoActorAction;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

public class SetAngleTowards extends ATwoActorAction {
	/**
	 * Generated serial version iD
	 */
	private static final long serialVersionUID = -5660923299638060985L;

	/**
	 * Changes actor a's angle property so that it faces towards actor b
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor a, Actor b) {
		Double xA = a.getPropertyValue(ActorProperties.X_LOCATION.getKey());
		Double yA = a.getPropertyValue(ActorProperties.Y_LOCATION.getKey());
		Double xB = b.getPropertyValue(ActorProperties.X_LOCATION.getKey());
		Double yB = b.getPropertyValue(ActorProperties.Y_LOCATION.getKey());
		if (Double.compare(xA, xB) == 0 && Double.compare(yA, yB) == 0)
			return;
		Double slope = (yB - yA) / (xB - xA);
		Double angle = Math.toDegrees(Math.atan(slope));
		if (xA > xB)
			angle += 180;
		a.setProperty(ActorProperties.ANGLE.getKey(), angle);
	}
}
