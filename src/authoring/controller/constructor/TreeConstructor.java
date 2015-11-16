package authoring.controller.constructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.actions.IAction;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.triggers.ITriggerEvent;
import voogasalad.util.reflection.Reflection;

public class TreeConstructor {
	
	private InteractionTreeNode selfTriggerTree;
	private InteractionTreeNode eventTriggerTree;
	private Map<String, ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;
	
	TreeConstructor() {
		selfTriggerTree = new InteractionTreeNode();
		eventTriggerTree = new InteractionTreeNode();
		triggerMap = new HashMap<String, ITriggerEvent>();
		actionMap = new HashMap<String, IAction>();
		
		// Load the initial configuration from the default values
		// Need to add methods to remove triggers and the likes
	}
	
	public void addSelfTrigger(String actor, String trigger, List<String> actions) {
		addSelfTrigger(trigger);
		// Build the tree
		
	}
	
	public void addEventTrigger(String aActor, String bActor, String trigger, List<String> actions) {
		addEventTrigger(trigger);
		// Build the tree	
		
	}
	
	private void addSelfTrigger(String trigger) {
		triggerMap.put(trigger, (ITriggerEvent) Reflection.createInstance(trigger));
	}
	
	private void addEventTrigger(String trigger) {
		actionMap.put(trigger, (IAction) Reflection.createInstance(trigger));
	}
	
	public InteractionTreeNode getInteractionTree() {
		return eventTriggerTree;
	}
	
	public InteractionTreeNode getSelfTriggerTree() {
		return selfTriggerTree;
	}
	
	public Map<String, ITriggerEvent> getTriggerMap() {
		return triggerMap;
	}

	public Map<String, IAction> getActionMap() {
		return actionMap;
	}	
}
