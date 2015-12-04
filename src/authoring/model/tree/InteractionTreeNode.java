package authoring.model.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import voogasalad.util.reflection.Reflection;
/**
 * A node in the interaction trees (the self-trigger and external trigger trees).
 * Contains a String representing an Actor, Trigger, or Action.
 *
 */
public class InteractionTreeNode implements Serializable {
	
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

	public InteractionTreeNode addChild (InteractionTreeNode n) {
		children.add(n);
		return n;
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
		return getClass().getSimpleName();
	}
	
//	public static void main(String...args) {
//		
//		InteractionTreeNode a = (InteractionTreeNode) Reflection.createInstance(ActorTreeNode.class.getName(), "a");
//		
////		InteractionTreeNode a = new ActorTreeNode("hey");
//		System.out.println(a.getIdentifier());
//		
//	}
}