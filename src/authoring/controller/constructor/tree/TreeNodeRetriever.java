package authoring.controller.constructor.tree;

import java.util.ArrayList;
import java.util.List;

import authoring.model.tree.InteractionTreeNode;

public class TreeNodeRetriever {
	
	public static List<String> getSelfTriggerList(InteractionTreeNode node, String actor) {
		return getStringList(node.getChildWithValue(actor).children());
	}
	
	public static List<String> getSelfTriggerActions(InteractionTreeNode node, String actor, String trigger) {
		return getStringList(node.getChildWithValue(actor).getChildWithValue(trigger).children());
	}
	
	public static List<String> getEventTrigger(InteractionTreeNode node, String aActor, String bActor) {
		return getStringList(node.getChildWithValue(aActor).getChildWithValue(bActor).children());
	}
	
	public static List<String> getEventTriggerActions(InteractionTreeNode node, String aActor, String bActor, String trigger) {
		return getStringList(node.getChildWithValue(aActor).getChildWithValue(bActor).getChildWithValue(trigger).children());
	}
	
	private static List<String> getStringList(List<InteractionTreeNode> nodes) {
		List<String> ret = new ArrayList<String>();
		for(InteractionTreeNode node : nodes) {
			ret.add(node.getValue());
		}
		return ret;
	}
}
