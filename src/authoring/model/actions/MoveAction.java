package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.properties.Property;

public class MoveAction extends IAction{

	@Override
	@SuppressWarnings("unchecked")
	public void run(Actor... actors) {
		Actor actor = actors[0];
		Property<Integer> health = (Property<Integer>) actor.getProperties().getComponents().get("health");
		Integer h = health.getValue();
		health.setValue(++h);
		System.out.println("Health: "+health.getValue());
	}
}
