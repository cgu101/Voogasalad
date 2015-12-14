// This entire file is part of my masterpiece.
// Connor Usry (cgu4)

package player.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import engine.IEngine;

public class PlayerStateUtility {

	Bundle<Property<?>> gamePropBundle; 
	ActorGroups actorMap;
	
	public PlayerStateUtility(IEngine myEngine) {
		actorMap = myEngine.getState().getActorMap();
		gamePropBundle = myEngine.getState().getPropertyBundle();
	}
	
	/**
	 * This method grabs the actors from the state returned by the engine.
	 *
	 * @return the list of Actors.
	 */
	public ArrayList<Actor> getIndividualActorsList() {
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for (Bundle<Actor> b : actorMap.getMap().values()) {
			actors.addAll(b.getComponents().values());
		}
		return actors;
	}
	
	/**
	 * This method grabs the list of Actor Groups from the state returned by the engine.
	 *
	 * @return the list of Actor Groups.
	 */
	public ArrayList<String> getActorGroups() {
		ArrayList<String> actorGroups = new ArrayList<String>();
		for(String s :actorMap.getMap().keySet()){
			actorGroups.add(s);
		}
		return actorGroups;
	}
	
	/**
	 * This method grabs the the Game Properties from the Game Engine.
	 * The keys are the property type, and the values are the property values.
	 * 
	 * @return The map of Properties.
	 */
	public Map<String, String> getGameProperties(){
		Map<String, String> properties = new HashMap<String, String>();
		for(Property<?> b : gamePropBundle){
			properties.put(b.getUniqueID(), b.getValue().toString());
		}
		return properties;
	}

	/**
	 * This method grabs a list of properties of the specified Actor 'a'.
	 *
	 * @param a The Actor you would like to retrieve the properties for.
	 * @return The list of Properties.
	 */
	public ArrayList<Property<?>> getProperties(Actor a){
		ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
		Bundle<Property<?>> propBundle = a.getProperties();
		for(Property<?> b : propBundle){
			properties.add((Property<?>) b.getValue());
		}
		return properties;
	}
	
	/**
	 * This method creates a Map the actor's Property Identifier to it's value
	 * The map is in <String, String> format to allow for easy GUI display.
	 *
	 * @param a The Actor you would like to retrieve the properties for.
	 * 
	 * @return The map of properties identifier -> value casted to Strings.
	 */
	public Map<String, String> getPropertyStringMapFromActorString(String a){
		Actor match = null;
		for(Actor actor : getIndividualActorsList()){
			if(actor.getUniqueID().equals(a)){
				match = actor;	
				break;
			}
		}
		
		return getPropertyStringMap(match);
	}
	
	/**
	 * This method grabs the actor's uniqueID 'a'.
	 *
	 * @param a The string uniqueID you would like to retrieve the Actor of.
	 * @return The Actor itself.
	 */
	public Actor getActorFromString(String a){
		ArrayList<Actor> actorList = getIndividualActorsList();
		for(Actor curr : actorList){
			if(curr.getUniqueID().equals(a)){
				return curr;
			}
		}
		return null;
	}
	
	/**
	 * This method creates a Map the actor's Property Identifier to it's value
	 * The map is in <String, String> format to allow for easy GUI display.
	 *
	 * @param a The Actor you would like to retrieve the properties for.
	 * @return The map of properties identifier -> value casted to Strings.
	 */
	private Map<String, String> getPropertyStringMap(Actor a){
		Map<String, String> propertyMap = new HashMap<String, String>();
		Bundle<Property<?>> b = a.getProperties();
		for(Property<?> prop : b){
			String identifier = prop.getUniqueID(); //health or whatever
			String value = String.valueOf(prop.getValue());
			propertyMap.put(identifier, value);
		}
		return propertyMap;
	}

}
