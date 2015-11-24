package view.element;

import authoring.model.actors.Actor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import util.Sprite;

/**
 * The MapActorManager class is responsible for adding and removing all Nodes that
 * are displayed within the Map's ScrollPane. Actors and backgrounds are added
 * through separate methods.
 * @author Daniel
 * @modified Bridget
 * 
 */
public class MapActorManager {
	private Group mapLayout;
	private Node currNode;
	private boolean moveActor;
	
	public MapActorManager(Group layout) {
		mapLayout = layout;
		currNode = null;
		moveActor = false;
	}	
	
	public void addActor(Actor actor, double x, double y) {		
		String img = (String) actor.getProperties().getComponents().get("image").getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(img));
		Sprite newSp = new Sprite(image);
		currNode = newSp;

		ContextMenu cm = makeContextMenu();
		newSp.setOnContextMenuRequested(e -> {
			currNode = newSp;
			cm.show(newSp, e.getScreenX(), e.getScreenY());
		});
		
		newSp.setTranslateX(x);
		newSp.setTranslateY(y);
		mapLayout.getChildren().add(newSp);
	}
	
	private ContextMenu makeContextMenu() {
		ContextMenu cm = new ContextMenu();
		MenuItem moveActor = makeMenuItem("Move Actor", event -> moveActor());
		MenuItem copyActor = makeMenuItem("Copy Actor", event -> copyActor());
		MenuItem deleteActor = makeMenuItem("Delete Actor", event -> removeActor());
		MenuItem editParam = makeMenuItem("Edit Parameters", event -> editParams());
		
		cm.getItems().addAll(moveActor, copyActor, deleteActor, editParam);

		return cm;
	}
	
	private void moveActor() {
		moveActor = true;
	}

	private void dragDrop(MouseEvent e) {
		if (moveActor) {
			double x = e.getX();
			double y = e.getY();
			
			currNode.setTranslateX(x);
			currNode.setTranslateY(y);
			
			// TODO: edit properties file
			moveActor = false;
		}
	}

	private void copyActor() {
		// TODO: 
		// create a copy of neighbor and set it as currNode
		// add currNode to list of nodes
		// moveNode = true;
	}
	
	private void editParams() {
		// TODO:
		// create a pop up dialog that a) contains current parameters
		// b) allows user to edit and change parameters (which are then saved)
		// *** map actor manager needs access to the actual actors, not just their imageviews.
	}

	private void removeActor() {
		mapLayout.getChildren().remove(currNode);
	}
	
	public void removeActor(Node actor) {
		mapLayout.getChildren().remove(actor);
	}
	
	private MenuItem makeMenuItem(String title, EventHandler<ActionEvent> handler) {
		MenuItem res = new MenuItem(title);
		res.setOnAction(handler);
		return res;
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
		background.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> dragDrop(e));
	}
}
