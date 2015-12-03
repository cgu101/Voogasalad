package player;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyCode;

public class InputTree {
	private static final int DEFAULT_SEQUENCE_CAPACITY = 10;
	
	private int sequenceCapacity = DEFAULT_SEQUENCE_CAPACITY;
	private InputTreeNode root;
	private List<String> keySequence;
	
	public InputTree () {
		this.root = new InputTreeNode(null, null);
	}
	public InputTree (int capacity) {
		this();
		sequenceCapacity = capacity;
	}
	public InputTreeNode getRoot() {
		return root;
	}
	class InputTreeNode {
		private String code;
		private String move;
	    private ArrayList<InputTreeNode> children;

	    InputTreeNode(String code, String move) {
	        this.code = code;
	        this.move = move;
	        this.children = new ArrayList<InputTreeNode>();
	    }
	}
	protected void addPressedKey (String code) {
		keySequence.add(code);
		if (keySequence.size() > sequenceCapacity) {
			keySequence.remove(0);
		}
	}
	protected void addPressedKey (KeyCode code) {
		addPressedKey(code.toString());
	}
	
	public void addMove (String[] codes, String move) {
		InputTreeNode currentNode = root;
		InputTreeNode nextNode = null;
		for (int i = 0; i < codes.length; i++) {
			for (InputTreeNode node : currentNode.children) {
				if (node.code.equals(codes[i])) {
					nextNode = node;
					break;
				}
			}
			if (nextNode == null) {
				nextNode = new InputTreeNode(codes[i], null);
				currentNode.children.add(nextNode);
			}
			currentNode = nextNode;
		}
		nextNode.move = move;
	}
	protected boolean checkSequenceKey (String key) {
		String currentKey = parseInput(keySequence, root);
		if (key != null && key == currentKey) {
			keySequence = new ArrayList<String>();
			return true;
		}
		return false;
	}
	private String parseInput (List<String> sequence, InputTreeNode currentNode) {
		String currentKey = sequence.remove(sequence.size() - 1);
		String ret = null;
		if (currentKey == null) return ret;
		if (currentNode.children.isEmpty()) ret = currentNode.move;
		for (InputTreeNode child : currentNode.children) {
			if (child.code.equals(currentKey)) {
				String move = parseInput(sequence, child);
				if (move != null) ret = move;
			}
		}
		sequence.add(currentKey);
		return ret;
	}
	// TODO: delete
	private String parseInput (){
		List<String> copy = new ArrayList<String>(keySequence);
		InputTreeNode currentNode = root;
		InputTreeNode nextNode = null;
		while (currentNode != null) {
			String currentKey = copy.remove(copy.size() - 1);
			if (currentKey == null) break;
			for (InputTreeNode node: currentNode.children) {
				if (node.code.equals(currentKey)) {
					nextNode = node;
					break;
				}
			}
			if (nextNode == null) {
				return currentNode.move;
			}
			currentNode = nextNode;
		}
		return null;
	}
	// for debugging
	public void printTree (InputTreeNode node) {
		if (node == null) {
			return;
		}
		String a = node.code;
		String b = node.move;
		System.out.println(a + " " + b);
		for (InputTreeNode n: node.children) {
			printTree(n);
		}
	}
}
