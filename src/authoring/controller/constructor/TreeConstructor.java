package authoring.controller.constructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.controller.constructor.tree.TreeNodeAdder;
import authoring.controller.constructor.tree.TreeNodeDeleter;
import authoring.controller.constructor.tree.TreeNodeRetriever;
import authoring.model.actions.IAction;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.triggers.ITriggerEvent;
import voogasalad.util.reflection.Reflection;

public class TreeConstructor {
	
	private InteractionTreeNode selfTriggerTree;
	private InteractionTreeNode eventTriggerTree;
	private Map<String, ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;

	
	public TreeConstructor() {
		selfTriggerTree = new InteractionTreeNode();
		eventTriggerTree = new InteractionTreeNode();
		triggerMap = new HashMap<String, ITriggerEvent>();
		actionMap = new HashMap<String, IAction>();
	}
	
//	public void addSelfTrigger(String actor, String trigger) {
//		addTriggerToMap(trigger);
//		TreeNodeAdder.addSelfTrigger(selfTriggerTree, actor, trigger);
//	}
	
	public void addSelfTriggerActions(String actor, String trigger, List<String> actions) {
		addTriggerToMap(trigger);
		addActionsToMap(actions);
		TreeNodeAdder.addSelfTriggerActions(selfTriggerTree, actor, trigger, actions);
	}
	
//	public void addEventTrigger(String aActor, String bActor, String trigger) {
//		addTriggerToMap(trigger);
//		TreeNodeAdder.addEventTrigger(eventTriggerTree, aActor, bActor, trigger);
//	}
	
	public void addEventTriggerActions(String aActor, String bActor, String trigger, List<String> actions) {
		addTriggerToMap(trigger);
		addActionsToMap(actions);
		TreeNodeAdder.addEventTriggerActions(eventTriggerTree, aActor, bActor, trigger, actions);
	}
		
	public void removeSelfTrigger(String actor, String trigger) {
		TreeNodeDeleter.removeSelfTrigger(selfTriggerTree, actor, trigger);
	}
	
//	public void removeSelfTriggerActions(String actor, String trigger, List<String> actions) {
//		TreeNodeDeleter.removeSelfTriggerActions(selfTriggerTree, actor, trigger, actions);
//	}
	
	public void removeEventTrigger(String aActor, String bActor, String trigger) {
		TreeNodeDeleter.removeEventTrigger(eventTriggerTree, aActor, bActor, trigger);
	}
	
//	public void removeEventTriggerActions(String aActor, String bActor, String trigger, List<String> actions) {
//		TreeNodeDeleter.removeEventTriggerActions(eventTriggerTree, aActor, bActor, trigger, actions);
//	}
	
//	public List<String> getSelfTriggerList(String actor) {
//		return TreeNodeRetriever.getSelfTriggerList(selfTriggerTree, actor);
//	}
	
	public List<String> getSelfTriggerActions(String actor, String trigger) {
		return TreeNodeRetriever.getSelfTriggerActions(selfTriggerTree, actor, trigger);
	}
	
//	public List<String> getEventTrigger(String aActor, String bActor) {
//		return TreeNodeRetriever.getEventTrigger(eventTriggerTree, aActor, bActor);
//	}
	
	public List<String> getEventTriggerActions(InteractionTreeNode node, String aActor, String bActor, String trigger) {
		return TreeNodeRetriever.getEventTriggerActions(eventTriggerTree, aActor, bActor, trigger);
	}
	
	public InteractionTreeNode getInteractionTree() {
		return eventTriggerTree;
	}
	
	public InteractionTreeNode getSelfTriggerTree() {
		System.out.println(selfTriggerTree.children() + " TreeConstructor 93");
		return selfTriggerTree;
	}
	
	public Map<String, ITriggerEvent> getTriggerMap() {
		return triggerMap;
	}

	public Map<String, IAction> getActionMap() {
		return actionMap;
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
}
