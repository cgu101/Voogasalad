package authoring.controller.constructor;

import java.util.List;

import authoring.model.tree.InteractionTreeNode;

public interface ITreeConstructor {
	
	public InteractionTreeNode getRootTree();
	public void addTreeNode(List<String> actors, List<String> triggers, List<String> actions);
	public void deleteTreeNode(List<String> actors, List<String> triggers, List<String> actions);
	public List<String> getActionList(List<String> actors, List<String> triggers);
	
}
