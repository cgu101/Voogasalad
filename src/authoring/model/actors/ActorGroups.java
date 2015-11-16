package authoring.model.actors;

import java.util.HashMap;
import java.util.Map;

import authoring.model.bundles.Group;

public class ActorGroups {
	private Map<String,Group<Actor>> actorMap;

	public ActorGroups () {
		actorMap = new HashMap<String,Group<Actor>>();
	}
	public Group<Actor> getGroup (String groupName) {
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
		getGroup(groupName).remove(actor);
	}

	public Group<Actor> addGroup (String groupName) {
		actorMap.put(groupName, new Group<Actor>());
		return actorMap.get(groupName);
	}

}
