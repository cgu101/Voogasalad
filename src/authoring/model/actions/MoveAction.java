package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class MoveAction extends AActionOneActor{

	@SuppressWarnings("unchecked")
	@Override
	public void run(ActorGroups actorGroup, Actor actor) {
		Property<Integer> health = (Property<Integer>) actor.getProperties().getComponents().get("health");
		Integer h = health.getValue();
		health.setValue(++h);
		System.out.println("Health: "+health.getValue());
	}

	@Override
	public String getUniqueID() {
		return this.getClass().getName();
	}
}
