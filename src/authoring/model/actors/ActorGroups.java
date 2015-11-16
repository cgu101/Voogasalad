package authoring.model.actors;

import java.util.HashMap;
import java.util.Map;

import authoring.model.bundles.Group;

public class ActorGroups {
	private Map<String,Group<Actor>> actorMap;

	public ActorGroups () {
		actorMap = new HashMap<String,Group<Actor>>();
	}
	
	public ActorGroups (ActorGroups actorMap) {
		this.actorMap = new HashMap<String, Group<Actor>>();
		
	}
	
	public Group<Actor> getGroup (String groupName) {
		return actorMap.get(groupName);
	}
	public void addToGroup (String groupName, Actor actor) {
		getGroup(groupName).add(actor);
	}
	public void removeFromGroup (String groupName, Actor actor) {
		getGroup(groupName).remove(actor);
	}
	
	private Map<String,Group<Actor>> getMap () {
		return actorMap;
	}
}
