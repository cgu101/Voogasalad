package view.element;

import javafx.scene.Group;
import javafx.scene.Node;

/**
 * The MapActorManager class is responsible for adding and removing all Nodes that
 * are displayed within the Map's ScrollPane. Actors and backgrounds are added
 * through separate methods.
 * @author Daniel
 *
 */
public class MapActorManager {
	private Group mapLayout;
	
	public MapActorManager(Group layout) {
		mapLayout = layout;
	}
	
	
	public void addActor(Node actor, double x, double y) {
		actor.setTranslateX(x);
		actor.setTranslateY(y);
		mapLayout.getChildren().add(actor);
	}
	
	public void removeActor(Node actor) {
		mapLayout.getChildren().remove(actor);
	}
	
	/**
	 * Removes the current background and adds the specified background as the new
	 * background.
	 * @param background - the new background to be set
	 */
	public void updateBackground(Node background) {
		mapLayout.getChildren().remove(background);
		mapLayout.getChildren().add(0, background);
	}
}
