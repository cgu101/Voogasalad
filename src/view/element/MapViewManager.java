package view.element;

import javafx.scene.Group;
import javafx.scene.Node;
import view.visual.AbstractVisual;

/**
 * The MapViewManager class is responsible for adding and removing all Nodes that
 * are displayed within the Map's ScrollPane. 
 * 
 * @author Daniel, Bridget
 * 
 */
public class MapViewManager extends AbstractVisual {
	private Group mapLayout;

	public MapViewManager(Group layout) {//, AuthoringController ac) {
		mapLayout = layout;
	}	

	public void addActor(Node sp, double x, double y, double imageWidth, double imageHeight) {		
		setOnGraphWithOffset(sp, x, y, imageWidth/2, imageHeight/2);
		mapLayout.getChildren().add(sp);
	} 
	
	// pass in the actual mouse drop coordinate. translates so that the display drops in the center.
	protected void setOnGraphWithOffset(Node node, double x, double y, double xOffset, double yOffset) {
		node.setTranslateX(x - xOffset);
		node.setTranslateY(y - yOffset);
	}

	protected void setOnGraph(Node n, double x, double y) {
		n.setTranslateX(x);
		n.setTranslateY(y);
	}

	protected void rotateActor(Node node, double absoluteHeading) {
		node.setRotate(absoluteHeading);
	}
	
	protected double getRotation(Node node) {
		return node.getRotate();
	}

	public void removeActor(Node actor) {
		mapLayout.getChildren().remove(actor);
	}
	
	protected void removeElements(Node... nodes) {
		for (Node n : nodes) {
			mapLayout.getChildren().remove(n);
		}
	}
	
	protected void addElements(Node... nodes) {
		for (Node n : nodes) {
			mapLayout.getChildren().add(n);
		}
	}

	/**
	 * Removes the current background and adds the specified background as the new
	 * background.
	 * @param background - the new background to be set
	 */
	public void updateBackground(Node background) {
		if (!mapLayout.getChildren().isEmpty()) {
			mapLayout.getChildren().remove(0);
		}
		mapLayout.getChildren().add(0, background);
	}
}
