package authoring.model.level;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import authoring.controller.constructor.levelwriter.ActorGroupsConstructor;
import authoring.controller.constructor.levelwriter.MapConstructor;
import authoring.controller.constructor.levelwriter.interfaces.ITreeConstructor;
import authoring.model.actions.IAction;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.bundles.Identifiable;
import authoring.model.properties.Property;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.triggers.ITriggerEvent;

public class Level implements Identifiable, Serializable {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -6630969237543157481L;
	
	// TODO this
	private InteractionTreeNode rootTree;
	
	private ActorGroups actorGroups;
	private Map<String,ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;
	private String uniqueID;
	private Bundle<Property<?>> propertyBundle;
	
	public Level (String levelID) {
		this.uniqueID = levelID;
		rootTree = new InteractionTreeNode();
		actorGroups = new ActorGroups();
		triggerMap = new HashMap<String, ITriggerEvent>();
		actionMap = new HashMap<String, IAction>();
		propertyBundle = new Bundle<Property<?>>();
	}
	
	public void setMapConstructorValues(MapConstructor map) {
		this.triggerMap = map.getTriggerMap();
		this.actionMap = map.getActionMap();
	}
	
	public void setTreeConstructorValues(ITreeConstructor tree) {
		this.rootTree = tree.getRootTree();
	}
	
	public Property<?> getProperty(String identifier) {
		return propertyBundle.get(identifier);
	}
	
	public void setProperty(Property<?> property) {
		this.propertyBundle.add(property);;
	}
	
	public Bundle<Property<?>> getPropertyBundle() {
		return propertyBundle;
	}
	
	public void setPropertyBundle(Bundle<Property<?>> bundle) {
		this.propertyBundle = bundle;
	}
	
	public void setActorGroupsValues(ActorGroupsConstructor actors) {
		this.actorGroups = actors.getActorGroups();
	}
	
	// TODO this
	public InteractionTreeNode getRootTree() {
		return rootTree;
	}
	
	public ActorGroups getActorGroups() {
		return actorGroups;
	}
	
	public Map<String, ITriggerEvent> getTriggerMap() {
		return triggerMap;
	}

	public Map<String, IAction> getActionMap() {
		return actionMap;
	}
	
	public void overWriteTree(InteractionTreeNode newRoot) {
		rootTree = newRoot;
	}

	@Override
	public String getUniqueID() {
		return uniqueID;
	}

	@Override
	public Identifiable getCopy() {
		return null;
	}

}
