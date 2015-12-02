package authoring.controller.constructor;

import java.util.ArrayList;
import java.util.List;

import authoring.model.tree.ActorTreeNode;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.tree.TriggerTreeNode;
import voogasalad.util.reflection.Reflection;

public class TreeConstructor implements ITreeConstructor {

	private InteractionTreeNode rootTree;

	/**
	 * Empty constructor for TreeConstructor.
	 */
	public TreeConstructor() {
		rootTree = new InteractionTreeNode();
	}
	
	@Override
	public InteractionTreeNode getRootTree() {
		return rootTree;
	}
	
	@Override
	public void addTreeNode(List<String> actors, List<String> triggers, List<String> actions) {
		executeBaseNode(actors, triggers, actions, true, (node, values)->{
			for(String action : actions) {
				if(node.getChildWithValue(action) == null) {
					node.addChild(new InteractionTreeNode(action));
				}
			}
			return actions;
		});
		rootTree.printGraph();
	}
	
	@Override
	public void deleteTreeNode(List<String> actors, List<String> triggers, List<String> actions) {
		executeBaseNode(actors, triggers, actions, false, (node, values)->{
			if(actions == null) {
				List<InteractionTreeNode> children = new ArrayList<InteractionTreeNode>(node.children());
				for(InteractionTreeNode child : children) {
					node.remove(child);
				}
			} else {
				for(String s : values) {
					node.remove(node.getChildWithValue(s));
				}
			}
			return null;
		});	
		rootTree.printGraph();
	}

	@Override
	public List<String> getActionList(List<String> actors, List<String> triggers) {
		return executeBaseNode(actors, triggers, null, false, (node, values)->{
			List<InteractionTreeNode> children = new ArrayList<InteractionTreeNode>(node.children());
			List<String> ret = new ArrayList<String>();
			for(InteractionTreeNode child : children) {
				ret.add(child.getValue());
			}
			return ret;
		});
	}
	
	private List<String> executeBaseNode(List<String> actors, List<String> triggers, List<String> actions, Boolean createNew, NodeExecuter executer) {
		InteractionTreeNode node = getBaseNode(rootTree, actors, createNew, ActorTreeNode.class.getName());		
		if(node != null) {
			node = getBaseNode(node, triggers, createNew, TriggerTreeNode.class.getName()); 
		}
		return node != null ? executer.executeNode(node, actions) : null;
	}
	
	private InteractionTreeNode getBaseNode(InteractionTreeNode root, List<String> childNodes, Boolean createNew, String clazz) {
		InteractionTreeNode iterator = root;
		for(String node : childNodes) {
			if(iterator.getChildWithValue(node) != null) {
				iterator = iterator.getChildWithValue(node);
			} else {
				if(createNew) {
					iterator = iterator.addChild((InteractionTreeNode) Reflection.createInstance(clazz, node));
				} else {
					return null;
				}
			}
		}	
		return iterator;
	}
	
	private interface NodeExecuter {	
		List<String> executeNode(InteractionTreeNode node, List<String> values);
	}
	
	/**
	 * This method will add a list of actions to the trigger mapped to the actor. 
	 * 
	 * @param actor
	 * @param trigger
	 * @param actions
	 */
//	public void addSelfTriggerActions(String actor, String trigger, List<String> actions) {
////		addTriggerToMap(trigger);
////		addActionsToMap(actions);
//		TreeNodeAdder.addSelfTriggerActions(selfTriggerTree, actor, trigger, actions);
//	}

	/**
	 * This method will add a list of actions for the trigger mapped to actors a and b. 
	 * 
	 * @param aActor
	 * @param bActor
	 * @param trigger
	 * @param actions
	 */
//	public void addEventTriggerActions(String aActor, String bActor, String trigger, List<String> actions) {
////		addTriggerToMap(trigger);
////		addActionsToMap(actions);
//		TreeNodeAdder.addEventTriggerActions(eventTriggerTree, aActor, bActor, trigger, actions);
//	}

	/**
	 * This method remvoes the self trigger for actor. 
	 * 
	 * @param actor
	 * @param trigger
	 */
//	public void removeSelfTrigger(String actor, String trigger) {
//		TreeNodeDeleter.removeSelfTrigger(selfTriggerTree, actor, trigger);
//	}
	
	/**
	 * This method remvoes a node from the . 
	 * 
	 * @param actor
	 * @param trigger
	 */
//	public void removeSelfTrigger(String actor, String trigger) {
//		TreeNodeDeleter.removeSelfTrigger(selfTriggerTree, actor, trigger);
//	}
	
	/**
	 * This method will remove the event trigger node for the mappage of Actor a to Actor b.
	 * 
	 * @param aActor
	 * @param bActor
	 * @param trigger
	 */
//	public void removeEventTrigger(String aActor, String bActor, String trigger) {
//		TreeNodeDeleter.removeEventTrigger(eventTriggerTree, aActor, bActor, trigger);
//	}
	
	/**
	 * This method will return all actions mapped to the given self trigger for the actor. 
	 * 
	 * @param actor
	 * @param trigger
	 * @return
	 */
//	public List<String> getSelfTriggerActions(String actor, String trigger) {
//		return TreeNodeRetriever.getSelfTriggerActions(selfTriggerTree, actor, trigger);
//	}

	/**
	 * This method will return all actions mapped to the given event triggers between two actors. 
	 * 
	 * @param node
	 * @param aActor
	 * @param bActor
	 * @param trigger
	 * @return List<String>
	 */
//	public List<String> getEventTriggerActions(InteractionTreeNode node, String aActor, String bActor, String trigger) {
//		return TreeNodeRetriever.getEventTriggerActions(eventTriggerTree, aActor, bActor, trigger);
//	}
	
	/**
	 * Returns the root-tree
	 * 
	 * @return InteractionTreeNode
	 */
	
	
	/**
	 * Returns the event-trigger tree. 
	 * 
	 * @return InteractionTreeNode
	 */
//	public InteractionTreeNode getInteractionTree() {
//		return eventTriggerTree;
//	}
	
//	/**
//	 * Returns the self-trigger tree. 
//	 * 
//	 * @return InteractionTreeNode
//	 */
//	public InteractionTreeNode getSelfTriggerTree() {
//		System.out.println(selfTriggerTree.children() + " TreeConstructor 93");
//		return selfTriggerTree;
//	}
	
	// TODO Remove these from this class, move them to another one
	
//	/**
//	 * Returns the trigger map. 
//	 * 
//	 * @return Map<String, ITriggerEvent>
//	 */
//	public Map<String, ITriggerEvent> getTriggerMap() {
//		return triggerMap;
//	}
//	
//	/**
//	 * Returns the action map. 
//	 * 
//	 * @return Map<String, IAction> 
//	 */
//	public Map<String, IAction> getActionMap() {
//		return actionMap;
//	}
//
//	private void addTriggerToMap(String trigger) {
//		if (!triggerMap.containsKey(trigger)) {
//			triggerMap.put(trigger, (ITriggerEvent) Reflection.createInstance(trigger));
//		}
//	}
//
//	private void addActionsToMap(List<String> actions) {
//		for (String s : actions) {
//			if (!actionMap.containsKey(s)) {
//				actionMap.put(s, (IAction) Reflection.createInstance(s));
//			}
//		}
//	}
}
