package authoring.model.actors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import authoring.model.bundles.Bundle;

/**
 * @author Inan and Sung
 *
 */
public class ActorGroups implements Serializable {
	private Map<String,Bundle<Actor>> actorMap;
	
	private Map<String,Bundle<Actor>> newActors;
	private Map<String,Bundle<Actor>> deadActors;

	public ActorGroups () {
		actorMap = new HashMap<String,Bundle<Actor>>();
		newActors = new HashMap<String,Bundle<Actor>>();
		deadActors = new HashMap<String,Bundle<Actor>>();
	}

	public ActorGroups (ActorGroups oldActorMap) {
		this.actorMap = new HashMap<String, Bundle<Actor>>();
		for (Entry<String, Bundle<Actor>> k : oldActorMap.getMap().entrySet()) {
			this.actorMap.put(k.getKey(), new Bundle<Actor>(k.getValue()));
		}
		newActors = new HashMap<String,Bundle<Actor>>();
		deadActors = new HashMap<String,Bundle<Actor>>();
	}
	
	private void addActorToMap(Actor actor, Map<String, Bundle<Actor>> map){
		String groupName = actor.getGroupName();
		if(map.containsKey(groupName)){
			getGroupFromMap(groupName, map).add(actor);
		}else{
			addGroupToMap(groupName, map).add(actor);
		}
	}
	
	private Bundle<Actor> getGroupFromMap (String groupName, Map<String, Bundle<Actor>> map) {
		return map.get(groupName);
	}
	
	private Bundle<Actor> addGroupToMap (String groupName, Map <String, Bundle<Actor>> map) {
		if(!map.containsKey(groupName)) {
			map.put(groupName, new Bundle<Actor>());
		}
		return map.get(groupName);
	}
	
	public void killActor(Actor actor){
		addActorToMap(actor, deadActors);
	}
	
	public void createActor(Actor actor){
		addActorToMap(actor, newActors);
	}
	
	public void addActor (Actor actor) {
		addActorToMap(actor, actorMap);
	}
	
	public void removeActor (Actor actor) {
		String groupName = actor.getGroupName();
		getGroupFromMap(groupName, actorMap).remove(actor.getUniqueID());
	}
	
	public Map<String,Bundle<Actor>> getMap () {
		return actorMap;
	}
	
	public Bundle<Actor> addGroup(String groupName){
		return addGroupToMap(groupName, actorMap);
	}

	public Bundle<Actor> getGroup(String groupName) {
		return getGroupFromMap(groupName, actorMap);
	}
	
	public void cleanUpActors() {
		addActorsToActorMap(newActors);
		removeActorsFromActorMap(deadActors);
	}

	private void addActorsToActorMap(Map<String, Bundle<Actor>> map) {
		for (String key : map.keySet()) {
			for (Actor actor : map.get(key)) {
				addActor(actor);
			}
		}
	}
	
	private void removeActorsFromActorMap(Map<String, Bundle<Actor>> map) {
		for (String key : map.keySet()) {
			for (Actor actor : map.get(key)) {
				removeActor(actor);
			}
		}
	}
}
