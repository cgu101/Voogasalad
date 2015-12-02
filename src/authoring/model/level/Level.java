package authoring.model.level;

import java.util.Map;

import authoring.controller.constructor.ActorGroupsConstructor;
import authoring.controller.constructor.ITreeConstructor;
import authoring.controller.constructor.MapConstructor;
import authoring.model.actions.IAction;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Identifiable;
import authoring.model.tree.InteractionTreeNode;
import authoring.model.triggers.ITriggerEvent;

public class Level implements Identifiable {
	
	private static final String ERROR_COPY = "Cannot copy levels";
	
	// TODO replace these with: 
	private InteractionTreeNode interactionTree;
	private InteractionTreeNode selfTriggerTree;
	
	// TODO this
	private InteractionTreeNode rootTree;
	
	private ActorGroups actorGroups;
	private Map<String,ITriggerEvent> triggerMap;
	private Map<String,IAction> actionMap;
	private String uniqueID;
	
	public Level (String levelID) {
		this.uniqueID = levelID;
	}
	
	public void setMapConstructorValues(MapConstructor map) {
		this.triggerMap = map.getTriggerMap();
		this.actionMap = map.getActionMap();
	}
	
	public void setTreeConstructorValues(ITreeConstructor tree) {
		this.rootTree = tree.getRootTree();
	}
	
	public void setActorGroupsValues(ActorGroupsConstructor actors) {
		this.actorGroups = actors.getActorGroups();
	}
	
	
	// TODO replace the below with
	public InteractionTreeNode getInteractionTree() {
		return interactionTree;
	}
	
	public InteractionTreeNode getSelfTriggerTree() {
		return selfTriggerTree;
	}
	
	// TODO this
	public InteractionTreeNode getRootTree() {
		return rootTree;
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
