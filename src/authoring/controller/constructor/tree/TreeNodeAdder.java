package authoring.controller.constructor.tree;

import java.util.List;

import authoring.model.tree.InteractionTreeNode;

public class TreeNodeAdder {
	
	public static void addSelfTrigger(InteractionTreeNode node, String actor, String trigger) {
		addActor(node, actor);
		addTreeNode(node.getChildWithValue(actor), trigger);
	}
	
	public static void addSelfTriggerActions(InteractionTreeNode node, String actor, String trigger, List<String> actions) {
		addActor(node, actor);
		addTreeNode(node.getChildWithValue(actor), trigger);
		for(String s: actions) {
			addTreeNode(node.getChildWithValue(actor).getChildWithValue(trigger), s);
		}
	}
	
	public static void addEventTrigger(InteractionTreeNode node, String aActor, String bActor, String trigger) {
		addActor(node, aActor, bActor);
		addTreeNode(node.getChildWithValue(aActor).getChildWithValue(bActor), trigger);	
	}
	
	public static void addEventTriggerActions(InteractionTreeNode node, String aActor, String bActor, String trigger, List<String> actions) {
		addActor(node, aActor, bActor);
		addTreeNode(node.getChildWithValue(aActor).getChildWithValue(bActor), trigger);
		for(String s: actions) {
			addTreeNode(node.getChildWithValue(aActor).getChildWithValue(bActor).getChildWithValue(trigger), s);
		}	
	}

	private static InteractionTreeNode addTreeNode(InteractionTreeNode node, String value) {
		if(node.getChildWithValue(value) == null) {
			node.addChild(new InteractionTreeNode(value));
		}
		return node.getChildWithValue(value);
	}
	
	private static void addActor(InteractionTreeNode node, String...actors) {
		for(String s: actors) {
			node = addTreeNode(node, s);
		}
	}

}
