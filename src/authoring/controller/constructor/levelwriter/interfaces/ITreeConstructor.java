package authoring.controller.constructor.levelwriter.interfaces;

import java.util.List;

import authoring.model.tree.InteractionTreeNode;

public interface ITreeConstructor {
	
	/**
	 * 
	 * @return
	 */
	public InteractionTreeNode getRootTree();
	
	/**
	 * 
	 * @param actors
	 * @param triggers
	 * @param actions
	 */
	public void addTreeNode(List<String> actors, List<String> triggers, List<String> actions);
	
	/**
	 * 
	 * @param actors
	 * @param triggers
	 * @param actions
	 */
	public void deleteTreeNode(List<String> actors, List<String> triggers, List<String> actions);
	
	/**
	 * 
	 * @param actors
	 * @param triggers
	 * @return
	 */
	public List<String> getActionList(List<String> actors, List<String> triggers);
	
}
