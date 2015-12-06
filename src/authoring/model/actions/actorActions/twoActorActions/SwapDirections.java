package authoring.model.actions.actorActions.twoActorActions;

import java.util.Map;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.ActionTriggerHelper;
import authoring.model.actions.actorActions.ATwoActorActions;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class SwapDirections<V> extends ATwoActorActions<V> {


	@SuppressWarnings("unchecked")
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor a, Actor b) {
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
