package engine;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import authoring.model.InteractionTreeNode;
import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.triggers.ITriggerEvent;
import player.InputManager;

// runs the interaction tree
public class InteractionExecutor {
	private InteractionTreeNode interactionTree;
	private InteractionTreeNode selfTriggerTree;
	private ActorGroups actorMap;
	private InputManager inputMap;
	private Map<String,ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;
	
	// selfTriggers and interactions should be kept in different trees
	// TODO: take in a single object and extract all of the needed information
	public InteractionExecutor (InteractionTreeNode selfTriggerTree, InteractionTreeNode interactionTree,
			ActorGroups actorMap, InputManager inputMap, 
			Map<String,ITriggerEvent> triggerMap, Map<String,IAction> actionMap) {
		this.selfTriggerTree = selfTriggerTree;
		this.interactionTree = interactionTree;
		this.actorMap = actorMap;
		this.triggerMap = triggerMap;
		this.actionMap = actionMap;
	}
	public void run () {
		runSelfTriggers();
		runInteractions();
	}
	
	private void runSelfTriggers () {
		for (InteractionTreeNode actorA : selfTriggerTree.children()) {
			List<InteractionTreeNode> triggerNodes = actorA.children();
			for (Actor uniqueA : actorMap.getGroup(actorA.getValue())){
				for (InteractionTreeNode trigger : triggerNodes) {
					List<InteractionTreeNode> actionNodes = trigger.children();
					ITriggerEvent selfTriggerEvent = triggerMap.get(trigger.getValue());
//					selfTriggerEvent.condition(parseActions(actionNodes), actorMap, inputMap, uniqueA);
				}
			}
		}	
	}
	private void runInteractions () {
		for (InteractionTreeNode actorA : interactionTree.children()) {
			List<InteractionTreeNode> actorBNodes = actorA.children();
			for (InteractionTreeNode actorB : actorBNodes) {
				List<InteractionTreeNode> triggerNodes = actorB.children();
				for (Actor uniqueA : actorMap.getGroup(actorA.getValue())){
					for (Actor uniqueB : actorMap.getGroup(actorB.getValue())) {
						for (InteractionTreeNode trigger : triggerNodes) {
							List<InteractionTreeNode> actionNodes = trigger.children();
							ITriggerEvent triggerEvent = triggerMap.get(trigger.getValue());
//							triggerEvent.condition(parseActions(actionNodes), actorMap, inputMap, uniqueA, uniqueB);
						}
					}
				}	
			}
		}
	}

	private List<IAction> parseActions(List<InteractionTreeNode> actionNodes) {
		return actionNodes.stream()
						  .map(k -> { return actionMap.get(k);})
						  .collect(Collectors.toList());
	}
}
