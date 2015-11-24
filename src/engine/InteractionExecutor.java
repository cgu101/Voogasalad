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

/**
 * The InteractionExecutor runs a single level for the engine.
 * Contains most of the state of the game.
 *
 */

public class InteractionExecutor {
	private String currentLevelIdentifier;
	private InteractionTreeNode externalTriggerTree;
	private InteractionTreeNode selfTriggerTree;
	private ActorGroups currentActorMap;
	private ActorGroups nextActorMap;
	private InputManager inputMap;
	private Map<String,ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;
	
	public InteractionExecutor () {
		this.currentLevelIdentifier = null;
		this.selfTriggerTree = new InteractionTreeNode();
		this.externalTriggerTree = new InteractionTreeNode();
		this.currentActorMap = new ActorGroups();
		this.inputMap = new InputManager();
		this.triggerMap = new HashMap<>();
		this.actionMap = new HashMap<>();
		this.nextActorMap = new ActorGroups();
	}
	
	public InteractionExecutor (Level level, InputManager inputMap) {
		this();
		this.inputMap = inputMap;
		
		if (level != null) {
			this.currentLevelIdentifier = level.getUniqueID();
			this.selfTriggerTree = level.getSelfTriggerTree();
			this.externalTriggerTree = level.getInteractionTree();
			this.currentActorMap = level.getActorGroups();

			this.triggerMap = level.getTriggerMap();
			this.actionMap = level.getActionMap();

			this.nextActorMap = new ActorGroups(currentActorMap);
		}
	}
	/**
	 * Runs a single step of the level. Resolves all self-triggers before external triggers.
	 * @return A {@link EngineHeartbeat} that allows the engine to communicate with the player controller.
	 */
	public EngineHeartbeat run () {
		nextActorMap = new ActorGroups(currentActorMap);
		runSelfTriggers();
		runExternalTriggers();
		currentActorMap = nextActorMap;
//		return new EngineHeartbeat(this, (IPlayer p) -> {}); // example lambda body: { p.pause(); }
		return new EngineHeartbeat((IPlayer p) -> {});
	}
	
	private void runSelfTriggers () {
//		System.out.println(selfTriggerTree.children());
		for (InteractionTreeNode actorA : selfTriggerTree.children()) {
			List<InteractionTreeNode> triggerNodes = actorA.children();
//			System.out.println(currentActorMap + " InteractionExecutor 67");
//			System.out.println(currentActorMap.getMap().keySet() + " InteractionExecutor 68");
//			System.out.println(actorA.getValue() + " InteractionExecutor 69");
			for (Actor uniqueA : currentActorMap.getGroup(actorA.getValue())){
				for (InteractionTreeNode trigger : triggerNodes) {
					List<InteractionTreeNode> actionNodes = trigger.children();
					ITriggerEvent selfTriggerEvent = triggerMap.get(trigger.getValue());
					selfTriggerEvent.performActions(parseActions(actionNodes), nextActorMap, inputMap, (Actor) uniqueA.getCopy());
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
							triggerEvent.performActions(parseActions(actionNodes), nextActorMap, inputMap, (Actor) uniqueA.getCopy(), (Actor) uniqueB.getCopy());
						}
					}
				}
			}
		}
	}

	private List<IAction> parseActions(List<InteractionTreeNode> actionNodes) {
		return actionNodes.stream()
						  .map(k -> { return actionMap.get(k.getValue());})
						  .collect(Collectors.toList());
	}
	
	public ActorGroups getActors () {
		//System.out.println(currentActorMap.getMap() + " InteractionExecutor");
		return currentActorMap;
	}
	public void setActors (ActorGroups actors) {
		this.currentActorMap = actors;
	}
	/**
	 * 
	 * @return The ID of the current level as a String.
	 */
	public String getLevelID () {
		return currentLevelIdentifier;
	}
}
