package authoring.model.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * A node in the interaction trees (the self-trigger and external trigger trees).
 * Contains a String representing an Actor, Trigger, or Action.
 *
 */
public class InteractionTreeNode {
	
	private List<InteractionTreeNode> children;
	private String value;
	
	public InteractionTreeNode () {
		this(null);
	}
	
	public InteractionTreeNode (String value) {
		this.value = value;
		this.children = new ArrayList<InteractionTreeNode>();
	}

	public InteractionTreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	public int getChildCount() {
		return children.size();
	}
	
	public List<InteractionTreeNode> children () {
		return Collections.unmodifiableList(children);
	}
	
	public InteractionTreeNode getChildWithValue (String value) {
		for (InteractionTreeNode child : children) {
			if (child.getValue().equals(value)) {
				return child;
			}
		}
		return null;
	}

	public void addChild (InteractionTreeNode n) {
		children.add(n);
	}
	public void remove(int index) {
		children.remove(index);
	}

	public void remove(InteractionTreeNode node) {
		children.remove(node);
	}
	
	public String getValue () {
		return value;
	}
	
	public void printGraph() {
		System.out.println(value);
		for(InteractionTreeNode child: children) {
			child.printGraph();
		}
	}

	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
}