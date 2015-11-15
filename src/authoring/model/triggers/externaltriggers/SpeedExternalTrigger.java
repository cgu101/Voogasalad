package authoring.model.triggers.externaltriggers;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;

public class SpeedExternalTrigger extends AExternalTrigger {

	public SpeedExternalTrigger(Bundle<IAction> actions, List<Actor> actors) {
		super(actions, actors);
	}

	@Override
	public boolean condition() {
		for (Actor a : actors) {
			Property<Integer> speed = (Property<Integer>) a.getProperties().getComponents().get("speed");
			Integer s = speed.getValue();
			if (s < 25)
				return false;
		}
		return performActions();
	}

}
