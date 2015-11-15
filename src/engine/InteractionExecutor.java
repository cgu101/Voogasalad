package engine;

import java.util.List;
import java.util.Map;

import authoring.model.InteractionTreeNode;
import authoring.model.actors.Actor;
import voogasalad.util.reflection.Reflection;

// runs the interaction tree
public class InteractionExecutor {
	private InteractionTreeNode interactionTree;
	private InteractionTreeNode selfTriggerTree;
	
	// selfTriggers and interactions should be kept in different trees
	public InteractionExecutor (InteractionTreeNode selfTriggerTree, InteractionTreeNode interactionTree) {
		this.selfTriggerTree = selfTriggerTree;
		this.interactionTree = interactionTree;
	}
	public void run (Map<String,List<Actor>> actorMap) {
		runSelfTriggers(actorMap);
		runInteractions(actorMap);
	}
	
	// actors will be found in a map of lists (maybe)
	private void runSelfTriggers (Map<String,List<Actor>> actorMap) {
		for (InteractionTreeNode actorA : selfTriggerTree.children()) {
			List<InteractionTreeNode> triggerNodes = actorA.children();
			for (Actor uniqueA : actorMap.get(actorA.getValue())){
				for (InteractionTreeNode trigger : triggerNodes) {
					List<InteractionTreeNode> actionNodes = trigger.children();
					if (findAndApply(trigger.getValue(), uniqueA)) {
						for (InteractionTreeNode action : actionNodes) {
							//TODO:
							// findAndApply(action.getValue(), uniqueA, uniqueB);
						}
					}
				}
			}
		}	
	}
	private void runInteractions (Map<String, List<Actor>> actorMap) {
		for (InteractionTreeNode actorA : interactionTree.children()) {
			List<InteractionTreeNode> actorBNodes = actorA.children();
			for (InteractionTreeNode actorB : actorBNodes) {
				List<InteractionTreeNode> triggerNodes = actorB.children();
				for (Actor uniqueA : actorMap.get(actorA.getValue())){
					for (Actor uniqueB : actorMap.get(actorB.getValue())) {
						for (InteractionTreeNode trigger : triggerNodes) {
							List<InteractionTreeNode> actionNodes = trigger.children();
							if (findAndApply(trigger.getValue(), uniqueA, uniqueB)) {
								for (InteractionTreeNode action : actionNodes) {
									//TODO:
									// findAndApply(action.getValue(), uniqueA, uniqueB);
								}
							}
						}
					}
				}	
			}
		}
	}

	private boolean findAndApply (String key, Object... args) {
		// TODO: figure out how triggers and actions run
		// note: currently the trigger and action classes are undefined, let alone an 'evaluate' method
		try {
			return (Boolean) Reflection.callMethod(key, "evaluate", args);
		} catch (Exception e) {
			return false;
		}
	}

}
