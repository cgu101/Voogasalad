package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.level.Level;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.triggers.ITriggerEvent;
import player.InputManager;
import player.IPlayer;

// runs the interaction tree
public class InteractionExecutor {
	private InteractionTreeNode externalTriggerTree;
	private InteractionTreeNode selfTriggerTree;
	private ActorGroups currentActorMap;
	private ActorGroups nextActorMap;
	private InputManager inputMap;
	private Map<String,ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;
	
	public InteractionExecutor () {
		this.selfTriggerTree = new InteractionTreeNode();
		this.externalTriggerTree = new InteractionTreeNode();
		this.currentActorMap = new ActorGroups();
		this.inputMap = new InputManager();
		this.triggerMap = new HashMap<>();
		this.actionMap = new HashMap<>();
		this.nextActorMap = new ActorGroups();
	}
	
	// TODO: take in a single object and extract all of the needed information
	public InteractionExecutor (Level level, InputManager inputMap) {
		this.selfTriggerTree = level.getSelfTriggerTree();
		this.externalTriggerTree = level.getInteractionTree();
		this.currentActorMap = level.getActorGroups();
		// TODO: input map
		this.inputMap = inputMap;
		
		this.triggerMap = level.getTriggerMap();
		this.actionMap = level.getActionMap();
		
		this.nextActorMap = new ActorGroups(currentActorMap);
	}
	
	public EngineHeartbeat run () {
		nextActorMap = new ActorGroups(currentActorMap);
		runSelfTriggers();
		runExternalTriggers();
		currentActorMap = nextActorMap;
		return new EngineHeartbeat(this, (IPlayer p) -> {}); // example lambda body: { p.pause(); }
	}
	
	private void runSelfTriggers () {
		for (InteractionTreeNode actorA : selfTriggerTree.children()) {
			List<InteractionTreeNode> triggerNodes = actorA.children();
			for (Actor uniqueA : currentActorMap.getGroup(actorA.getValue())){
				for (InteractionTreeNode trigger : triggerNodes) {
					List<InteractionTreeNode> actionNodes = trigger.children();
					ITriggerEvent selfTriggerEvent = triggerMap.get(trigger.getValue());
					selfTriggerEvent.condition(parseActions(actionNodes), nextActorMap, inputMap, uniqueA);
				}
			}
		}	
	}
	private void runExternalTriggers () {
		for (InteractionTreeNode actorA : externalTriggerTree.children()) {
			List<InteractionTreeNode> actorBNodes = actorA.children();
			for (InteractionTreeNode actorB : actorBNodes) {
				List<InteractionTreeNode> triggerNodes = actorB.children();
				for (Actor uniqueA : currentActorMap.getGroup(actorA.getValue())){
					for (Actor uniqueB : currentActorMap.getGroup(actorB.getValue())) {
						for (InteractionTreeNode trigger : triggerNodes) {
							List<InteractionTreeNode> actionNodes = trigger.children();
							ITriggerEvent triggerEvent = triggerMap.get(trigger.getValue());
							triggerEvent.condition(parseActions(actionNodes), nextActorMap, inputMap, uniqueA, uniqueB);
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
	public ActorGroups getActors () {
		return new ActorGroups(currentActorMap);
	}
	// TODO
	public void setActors (ActorGroups actors) {
		this.currentActorMap = actors;
	}
}
