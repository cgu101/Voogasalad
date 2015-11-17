package authoring.controller.constructor.tree;

import java.util.List;

import authoring.model.tree.InteractionTreeNode;

public class TreeNodeAdder {
	
	public static void addSelfTrigger(InteractionTreeNode node, String actor, String trigger) {
		//addSelfTriggerNode(actor, trigger);
	}
	
	public static void addSelfTriggerActions(InteractionTreeNode node, String actor, String trigger, List<String> actions) {
		//addSelfTriggerActionsNode(actor, trigger, actions);
	}
	
	public static void addEventTrigger(InteractionTreeNode node, String aActor, String bActor, String trigger) {
		//addEventTriggerNode(aActor, bActor, trigger);
	}
	
	public static void addEventTriggerActions(InteractionTreeNode node, String aActor, String bActor, String trigger, List<String> actions) {
		//addEventTriggerActionsNode(aActor, bActor, trigger, actions);
	}
	
	private void addSelfTriggerNode(String a, String trigger, List<String> actions) {
//		InteractionTreeNode ret = getTreeNode(selfTriggerTree, a);
//		addTriggerNode(ret, trigger, actions);
	}
	
	private void addEventTriggerNode(String a, String b, String trigger, List<String> actions) {
//		InteractionTreeNode ret = getTreeNode(eventTriggerTree, a);
//		ret = getTreeNode(ret, b);			
//		addTriggerNode(ret, trigger, actions);
	}
		
	private void addTriggerNode(InteractionTreeNode node, String trigger, List<String> actions) {
//		InteractionTreeNode ret = getTreeNode(node, trigger);		
//		for(String s: actions) {
//			if(ret.getChildWithValue(s) == null) {
//				ret.addChild(new InteractionTreeNode(s));
//			}
//		}
	}
	
	private InteractionTreeNode getTreeNode(InteractionTreeNode node, String value) {
//		InteractionTreeNode ret;
//		if((ret = node.getChildWithValue(value)) == null) {
//			ret = new InteractionTreeNode(value);
//			node.addChild(ret);
//		}		
//		return ret;
		return null;
	}

}
