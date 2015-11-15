package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.bundles.Identifiable;

public abstract class IAction implements Identifiable {
	public abstract void run(Actor... a);
	public String getUniqueID (){
		return "";
	}
}
