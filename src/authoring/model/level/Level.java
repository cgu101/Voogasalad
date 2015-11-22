package authoring.model.level;

import java.util.Map;

import authoring.controller.constructor.ActorGroupsConstructor;
import authoring.controller.constructor.TreeConstructor;
import authoring.model.actions.IAction;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Identifiable;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.triggers.ITriggerEvent;

public class Level implements Identifiable {
	
	private static final String ERROR_COPY = "Cannot copy levels";
	
	private InteractionTreeNode interactionTree;
	private InteractionTreeNode selfTriggerTree;
	private ActorGroups actorGroups;
	private Map<String,ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;
	private String uniqueID;
	
	public Level (String levelID) {
		this.uniqueID = levelID;
	}
	
	public void setTreeConstructorValues(TreeConstructor tree) {
		this.interactionTree = tree.getInteractionTree();
		this.selfTriggerTree = tree.getSelfTriggerTree();
		this.triggerMap = tree.getTriggerMap();
		this.actionMap = tree.getActionMap();
	}
	
	public void setActorGroupsValues(ActorGroupsConstructor actors) {
		this.actorGroups = actors.getActorGroups();
	}
	
	public InteractionTreeNode getInteractionTree() {
		return interactionTree;
	}
	
	public InteractionTreeNode getSelfTriggerTree() {
		return selfTriggerTree;
	}
	
	public ActorGroups getActorGroups() {
		return actorGroups;
	}
	
	public Map<String, ITriggerEvent> getTriggerMap() {
		return triggerMap;
	}

	public Map<String, IAction> getActionMap() {
		return actionMap;
	}

	@Override
	public String getUniqueID() {
		return uniqueID;
	}

	@Override
	public Identifiable getCopy() {
		return null;
	}

}
