package engine;

import java.util.Observable;

import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import player.controller.PlayerController;
/**
 * A save-state of a game. 
 * Contains a property bundle for metadata and a ActorGroups containing the state of actors.
 * @author Austin
 */
public class State extends Observable {
	private ActorGroups myActorMap;
	private Bundle<Property<?>> myPropertyBundle;
	private Instruction<PlayerController> instruction;
	
	public State () {
		myPropertyBundle = new Bundle<Property<?>>();
		myActorMap = new ActorGroups();
	}
	public State (Bundle<Property<?>> propertyBundle, ActorGroups actorMap) {
		myPropertyBundle = propertyBundle;
		myActorMap = actorMap;
	}
	public State(State state) {
		myPropertyBundle = new Bundle<Property<?>>(state.getPropertyBundle());
		myActorMap = new ActorGroups(state.getActorMap());
	}

	public ActorGroups getActorMap() {
		return myActorMap;
	}
	protected void setActorMap(ActorGroups map) {
		this.myActorMap = map;
	}
	
	/**
	 * @param key
	 * @return property value from property bundle
	 */
	public Property<?> getProperty (String key) {
		return myPropertyBundle.get(key);
	}
	public Bundle<Property<?>> getPropertyBundle () {
		return myPropertyBundle;
	}
	protected void merge(State nextState) {
		this.myPropertyBundle = nextState.getPropertyBundle();
		this.myActorMap = nextState.getActorMap();
	}
	
	public void areThereNewOrDeadActors(){
		if(myActorMap.areThereNewOrDeadActors()){
			setChanged();
			notifyObservers();
		}
	}
	public void setInstruction(Instruction<PlayerController> instruction) {
		this.instruction = instruction;
	}
	@FunctionalInterface
	public interface Instruction <A> { 
		public void apply (A a);
	}
	public Instruction<PlayerController> getInstruction () {
		return instruction;
	}
}
