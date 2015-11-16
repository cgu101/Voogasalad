package authoring.model.actions.twoActorActions;

import authoring.model.actions.AActionTwoActors;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class ChangeDirections extends AActionTwoActors{

	@Override
	public void run(ActorGroups actorGroup, Actor a, Actor b) {
		Double a_xP = ((Property<Double>) a.getProperties().getComponents().get("xlocation")).getValue();
		Double a_yP = ((Property<Double>) a.getProperties().getComponents().get("ylocation")).getValue();
		
		Double b_xP = ((Property<Double>) b.getProperties().getComponents().get("xlocation")).getValue();
		Double b_yP = ((Property<Double>) b.getProperties().getComponents().get("ylocation")).getValue();
		
		
	}
	
	@Override
	public String getUniqueID() {
		return this.getClass().getName();
	}

}
