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
	}
	
	public void addSelfTrigger(String actor, String trigger, List<String> actions) {
		addTriggerToMap(trigger);
		addActionsToMap(actions);
		addSelfTriggerNode(actor, trigger, actions);
	}
	
	public void addEventTrigger(String aActor, String bActor, String trigger, List<String> actions) {
		addTriggerToMap(trigger);
		addActionsToMap(actions);
		addEventTriggerNode(aActor, bActor, trigger, actions);
	}
	
	public void removeSelfTrigger(String actor, String trigger, List<String> actions) {
		removeTriggerFromMap(trigger);
		removeActionsFromMap(actions);
		removeSelfTriggerNode(actor, trigger, actions);
	}
	
	public void removeEventTrigger(String aActor, String bActor, String trigger, List<String> actions) {
		removeTriggerFromMap(trigger);
		removeActionsFromMap(actions);
		removeEventTriggerNode(aActor, bActor, trigger, actions);
	}
	
	private void addSelfTriggerNode(String a, String trigger, List<String> actions) {
		InteractionTreeNode ret = getTreeNode(selfTriggerTree, a);
		addTriggerNode(ret, trigger, actions);
	}
	
	private void addEventTriggerNode(String a, String b, String trigger, List<String> actions) {
		InteractionTreeNode ret = getTreeNode(eventTriggerTree, a);
		ret = getTreeNode(ret, b);			
		addTriggerNode(ret, trigger, actions);
	}
		
	private void addTriggerNode(InteractionTreeNode node, String trigger, List<String> actions) {
		InteractionTreeNode ret = getTreeNode(node, trigger);		
		for(String s: actions) {
			if(ret.getChildWithValue(s) == null) {
				ret.addChild(new InteractionTreeNode(s));
			}
		}
	}
	
	private InteractionTreeNode getTreeNode(InteractionTreeNode node, String value) {
		InteractionTreeNode ret;
		if((ret = node.getChildWithValue(value)) == null) {
			ret = new InteractionTreeNode(value);
			node.addChild(ret);
		}		
		return ret;
	}
	
	private void removeSelfTriggerNode(String a, String trigger, List<String> actions) {
		// Remove the node
	}
	
	private void removeEventTriggerNode(String a, String b, String trigger, List<String> actions) {
		// Remove the node
	}
	
	
	private void addTriggerToMap(String trigger) {
		if(!triggerMap.containsKey(trigger)) {
			triggerMap.put(trigger, (ITriggerEvent) Reflection.createInstance(trigger));
		}
	}
	
	private void addActionsToMap(List<String> actions) {
		for(String s: actions) {
			if(!actionMap.containsKey(s)) {
					actionMap.put(s, (IAction) Reflection.createInstance(s));
			}
		}
	}
	
	private void removeTriggerFromMap(String trigger) {
		triggerMap.remove(trigger);
	}
	
	private void removeActionsFromMap(List<String> actions) {
		for(String s: actions) {
			triggerMap.remove(s);
		}
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
