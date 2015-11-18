package authoring.controller.constructor.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import authoring.model.tree.InteractionTreeNode;

public class TreeNodeDeleter {
	
//	public static void removeActor(InteractionTreeNode node, String actor) {
//		deleteNodes(node, Arrays.asList(new String[] {actor}));
//	}
//	
//	public static void removeActor(InteractionTreeNode node, String aActor, String bActor) {
//		deleteNodes(node.getChildWithValue(aActor), Arrays.asList(new String[] {bActor}));
//	}
	
	public static void removeSelfTrigger(InteractionTreeNode node, String actor, String trigger) {
		if(node.getChildWithValue(actor) != null) {
			deleteNodes(node.getChildWithValue(actor), Arrays.asList(new String[] {trigger}));
		}
	}
	
//	public static void removeSelfTriggerActions(InteractionTreeNode node, String actor, String trigger, List<String> actions) {
//		deleteNodes(node.getChildWithValue(actor).getChildWithValue(trigger), actions);
//	}
	
	public static void removeEventTrigger(InteractionTreeNode node, String aActor, String bActor, String trigger) {
		if(node.getChildWithValue(aActor) != null) {
			if(node.getChildWithValue(aActor).getChildWithValue(bActor) != null) {
				deleteNodes(node.getChildWithValue(aActor).getChildWithValue(bActor), Arrays.asList(new String[] {trigger}));
			}
		}
		
	}
	
//	public static void removeEventTriggerActions(InteractionTreeNode node, String aActor, String bActor, String trigger, List<String> actions) {
//		deleteNodes(node.getChildWithValue(aActor).getChildWithValue(bActor).getChildWithValue(trigger), actions);
//	}
	
	private static void deleteNodes(InteractionTreeNode node, List<String> values) {
		List<InteractionTreeNode> toDelete = new ArrayList<InteractionTreeNode>();
		for(String s: values) {
			toDelete.add(node.getChildWithValue(s));
		}
		
		for(InteractionTreeNode t: toDelete) {
			node.remove(t);
		}
	}
	
	private static void deleteEmptyNode(InteractionTreeNode node, String value) {
		if(node.getChildWithValue(value).children().isEmpty()) {
			deleteNodes(node, Arrays.asList(new String[]{value}));
		}
	}
}
