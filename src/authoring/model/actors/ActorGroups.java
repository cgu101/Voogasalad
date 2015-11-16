package authoring.model.actors;

import java.util.HashMap;
import java.util.Map;

import authoring.model.bundles.Bundle;

public class ActorGroups {
	private Map<String,Bundle<Actor>> actorMap;

	public ActorGroups () {
		actorMap = new HashMap<String,Bundle<Actor>>();
	}
	public Bundle<Actor> getGroup (String groupName) {
		return actorMap.get(groupName);
	}

	public void addToGroup (String groupName, Actor actor) {
		if(actorMap.containsKey(groupName)){
			getGroup(groupName).add(actor);
		}else{
			addGroup(groupName).add(actor);
		}
	}

	public void removeFromGroup (String groupName, Actor actor) {
		getGroup(groupName).remove(actor.getUniqueID());
	}

	public Bundle<Actor> addGroup (String groupName) {
		actorMap.put(groupName, new Bundle<Actor>());
		return actorMap.get(groupName);
	}

}
