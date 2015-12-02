package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.level.Level;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.triggers.ITriggerEvent;
import player.IPlayer;
import player.InputManager;

/**
 * The InteractionExecutor runs a single level for the engine.
 * Contains most of the state of the game.
 *
 */

public class InteractionExecutor {
	private static final String ACTOR_IDENTIFIER = "actor";
	private static final String TRIGGER_IDENTIFIER = "trigger";
	private static final String ACTION_IDENTIFIER = "action";

	private String currentLevelIdentifier;
	private InteractionTreeNode externalTriggerTree;
	private InteractionTreeNode selfTriggerTree;
	private ActorGroups currentActorMap;
	private ActorGroups nextActorMap;
	private InputManager inputMap;
	private Map<String,ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;

	private InteractionTreeNode triggerTree;
	private Map<String, NodeLambda<InteractionTreeNode,List>> lambdaMap;

	public InteractionExecutor () {
		this.currentLevelIdentifier = null;
		this.selfTriggerTree = new InteractionTreeNode();
		this.externalTriggerTree = new InteractionTreeNode();
		this.currentActorMap = new ActorGroups();
		this.inputMap = new InputManager();
		this.triggerMap = new HashMap<>();
		this.actionMap = new HashMap<>();
		this.nextActorMap = new ActorGroups();
		initLambdaMap();
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
//		runSelfTriggers();
//		runExternalTriggers();
		try {
			runTriggers();
		} catch (Exception e) {
			// TODO: throw an exception about the tree
		}
		currentActorMap = nextActorMap;
		//		return new EngineHeartbeat(this, (IPlayer p) -> {}); // example lambda body: { p.pause(); }
		return new EngineHeartbeat((IPlayer p) -> {});
	}

	private void runTriggers () {
		for (InteractionTreeNode node : triggerTree.children()) {
			lambdaMap.get(node.getIdentifier()).apply(node, Arrays.asList(node.getValue()));;
		}
	}

//	private void runSelfTriggers () {
//		//		System.out.println(selfTriggerTree.children());
//		for (InteractionTreeNode actorA : selfTriggerTree.children()) {
//			List<InteractionTreeNode> triggerNodes = actorA.children();
//			//			System.out.println(currentActorMap + " InteractionExecutor 67");
//			//			System.out.println(currentActorMap.getMap().keySet() + " InteractionExecutor 68");
//			//			System.out.println(actorA.getValue() + " InteractionExecutor 69");
//			for (Actor uniqueA : currentActorMap.getGroup(actorA.getValue())){
//				for (InteractionTreeNode trigger : triggerNodes) {
//					List<InteractionTreeNode> actionNodes = trigger.children();
//					ITriggerEvent selfTriggerEvent = triggerMap.get(trigger.getValue());
//					if (selfTriggerEvent.condition(inputMap, (Actor) uniqueA.getCopy())) {
//						performActions(parseActions(actionNodes), nextActorMap, (Actor) uniqueA.getCopy());
//					}
//				}
//			}
//		}	
//	}
//	private void runExternalTriggers () {
//		for (InteractionTreeNode actorA : externalTriggerTree.children()) {
//			List<InteractionTreeNode> actorBNodes = actorA.children();
//			for (InteractionTreeNode actorB : actorBNodes) {
//				List<InteractionTreeNode> triggerNodes = actorB.children();
//				for (Actor uniqueA : currentActorMap.getGroup(actorA.getValue())){
//					for (Actor uniqueB : currentActorMap.getGroup(actorB.getValue())) {
//						for (InteractionTreeNode trigger : triggerNodes) {
//							List<InteractionTreeNode> actionNodes = trigger.children();
//							ITriggerEvent triggerEvent = triggerMap.get(trigger.getValue());
//							if (triggerEvent.condition(inputMap, (Actor) uniqueA.getCopy(), (Actor) uniqueB.getCopy())) {
//								performActions(parseActions(actionNodes), nextActorMap, (Actor) uniqueA.getCopy(), (Actor) uniqueB.getCopy());
//							}
//						}
//					}
//				}
//			}
//		}
//	}

//	private List<IAction> parseActions(List<InteractionTreeNode> actionNodes) {
//		return actionNodes.stream()
//				.map(k -> { return actionMap.get(k.getValue());})
//				.collect(Collectors.toList());
//	}

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

//	public boolean performActions(List<IAction> actions, ActorGroups actorGroup, Actor... actors) {
//		Iterator<IAction> iterator = actions.iterator();
//		while (iterator.hasNext()) {
//			iterator.next().run(actorGroup, actors);
//		}
//		return actions.size() > 0;
//	}

	@SuppressWarnings("unchecked")
	private void initLambdaMap () {
		lambdaMap = new HashMap<String,NodeLambda<InteractionTreeNode,List>>();
		lambdaMap.put(ACTOR_IDENTIFIER, (node, list) -> {
			for(InteractionTreeNode child : node.children()){
				if (child.getIdentifier() == ACTOR_IDENTIFIER) {
					lambdaMap.get(child.getIdentifier()).apply(child, cloneListAndAdd(list, child.getValue()));
				} else {
					List<List<Actor>> comboList = new ArrayList<List<Actor>>();
					generateActorCombinations(list, comboList);
					for (List<Actor> combo : comboList) {
						lambdaMap.get(child.getIdentifier()).apply(child, combo);
					}
				}
			}
		});
		lambdaMap.put(TRIGGER_IDENTIFIER, (node, list) -> {
			ITriggerEvent triggerEvent = triggerMap.get(node.getValue());
			if (triggerEvent.condition(inputMap, (Actor[]) list.toArray())) {
				for (InteractionTreeNode child : node.children()) {
					lambdaMap.get(child.getIdentifier()).apply(child, list);
				}
			}
		});
		lambdaMap.put(ACTION_IDENTIFIER, (node, list) -> {
			IAction action = actionMap.get(node.getValue());
			action.run(nextActorMap, (Actor[]) list.toArray());
		});
	}
	private <T> List<T> cloneListAndAdd (List<T> list, T value) {
		List<T> actorList = new ArrayList<T>(list);
		actorList.add(value);
		return actorList;
	}
	
	private void generateActorCombinations(List<String> groups, List<List<Actor>> uniques){
		generateActorCombinations (groups, uniques, new ArrayList<Actor>());
	}
	
	private void generateActorCombinations (List<String> groups, List<List<Actor>> uniques, List<Actor> current) {
		int depth = current.size();
		if (depth == groups.size()) {
			uniques.add(current);
			return;
		}
		Bundle<Actor> currentGroup = currentActorMap.getGroup(groups.get(depth));
		for(Actor a : currentGroup) {
			generateActorCombinations(groups, uniques, cloneListAndAdd(current, (Actor) a.getCopy()));
		}
	}
	@FunctionalInterface
	interface NodeLambda <A, B> { 
		public void apply (A a, B b);
	}
}
