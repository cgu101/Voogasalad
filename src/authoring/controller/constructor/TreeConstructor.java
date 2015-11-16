package authoring.controller.constructor;

import java.util.List;
import java.util.Map;

import authoring.model.actions.IAction;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.triggers.ITriggerEvent;

public class TreeConstructor {
	
	private InteractionTreeNode selfTriggerTree;
	private InteractionTreeNode eventTriggerTree;
	private Map<String, ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;
	
	public void addSelfTrigger(String actor, String trigger, List<String> actions) {
		addSelfTrigger(trigger);
		// Build the tree
	}
	
	public void addEventTrigger(String aActor, String bActor, String trigger, List<String> actions) {
		addEventTrigger(trigger);
		// Build the tree
		
	}
	
	private void addSelfTrigger(String trigger) {
		// Use reflection to create an instance of the tree
	}
	
	private void addEventTrigger(String trigger) {
		
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
