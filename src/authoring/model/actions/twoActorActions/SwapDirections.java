package authoring.model.actions.twoActorActions;

import authoring.files.properties.ActorProperties;
import authoring.model.ActionTriggerHelper;
import authoring.model.actions.ATwoActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

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
	public void run(Parameters parameters, State state, Actor a, Actor b) {
		Property<Double> a_angleP = (Property<Double>) a.getProperty(ActorProperties.ANGLE.getKey());
		Property<Double> b_angleP = (Property<Double>) b.getProperty(ActorProperties.ANGLE.getKey());

		Double temp = a_angleP.getValue();
		a_angleP.setValue(b_angleP.getValue());
		b_angleP.setValue(temp);

		new MoveActorsToAvoidCollisions().run(null, null, a, b);
		
		if (ActionTriggerHelper.collision(a, b)) {
			System.out.println("STILL COLLIDING AFTER ADJUSTMENT");
		}
	}

}
