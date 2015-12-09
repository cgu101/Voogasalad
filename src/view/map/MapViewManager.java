package view.map;

import javafx.scene.Group;
import javafx.scene.Node;
import view.visual.AbstractVisual;

/**
 * The MapViewManager class is responsible for adding and removing all Nodes
 * that are displayed within the Map's ScrollPane.
 * 
 * @author Daniel, Bridget
 * 
 */
public class MapViewManager extends AbstractVisual {
	private Group mapLayout;

	public MapViewManager(Group layout) {// , AuthoringController ac) {
		mapLayout = layout;
	}

	public void removeElements(Node... nodes) {
		for (Node n : nodes) {
			mapLayout.getChildren().remove(n);
		}
	}

	public void addElements(Node... nodes) {
		for (Node n : nodes) {
			mapLayout.getChildren().add(n);
		}
	}

	public void removeAll() {
		Node background = mapLayout.getChildren().get(0);
		mapLayout.getChildren().clear();
		mapLayout.getChildren().add(background);
	}

	/**
	 * Removes the current background and adds the specified background as the
	 * new background.
	 * 
	 * @param background
	 *            - the new background to be set
	 */
	public void updateBackground(Node background) { 
		if (!mapLayout.getChildren().isEmpty()) {
			mapLayout.getChildren().remove(0);
		}
		mapLayout.getChildren().add(0, background);
	}
}
