package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.bundles.Identifiable;

public interface IAction extends Identifiable {
	public void run(ActorGroups actorGroup, Actor... a);
}


/*
 * 	Need to make the IAction class a funtional interface with
 * 	Only ONE UNIMPLEMENTED method.
 */

///public abstract class IAction implements Identifiable {
//	public abstract void run(Actor... a);
//	public String getUniqueID (){
//		return "";
//	}
//}
