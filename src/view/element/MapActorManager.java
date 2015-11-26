package view.element;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.Sprite;
import view.visual.AbstractVisual;

/**
 * The MapActorManager class is responsible for adding and removing all Nodes that
 * are displayed within the Map's ScrollPane. Actors and backgrounds are added
 * through separate methods.
 * @author Daniel, Bridget
 * 
 */
public class MapActorManager extends AbstractVisual {
	private Group mapLayout;
	private Node currNode;
	private Actor currActor;
	private AuthoringController controller;
	
	public MapActorManager(Group layout, AuthoringController ac) {
		mapLayout = layout;
		currNode = null;
		currActor = null;
		controller = ac;
	}	
	
	public void addActor(Actor actor, double x, double y) {		
		currActor = actor;
		String img = (String) currActor.getProperties().getComponents().get("image").getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(img));
		Sprite sp = new Sprite(image);
		currNode = sp;
		
		ContextMenu cm = makeContextMenu();
		sp.setOnContextMenuRequested(e -> {
			currActor = actor;
			currNode = sp;
			cm.show(sp, e.getScreenX(), e.getScreenY());
		});
		
		sp.setTranslateX(x);
		sp.setTranslateY(y);
		mapLayout.getChildren().add(sp);
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
		// TODO: make rect & hello length of top of background image & 
		// make hello float at top of screen

		double origX = currNode.getTranslateX();
		double origY = currNode.getTranslateY();
		Rectangle rect = new Rectangle(700, 700); // TODO: make it the size of the background image
		rect.setFill(Color.rgb(255, 0, 0, 0.3));
		rect.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> dragDrop(e));

		ToolBar hello = new ToolBar();
		hello.setMinWidth(700);
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		Label instru = makeLabel("Click on new location for actor");
		Button undo = makeButton("Return to original position", e -> revertToPosition(origX, origY));
		Button exitFilter = makeButton("Finished", e -> removeElements(rect, hello)); 
		hello.getItems().addAll(instru, spacer, undo, exitFilter);
		
		mapLayout.getChildren().addAll(rect, hello);
	}
	
	private void removeElements(Node... nodes) {
		for (Node n : nodes) {
			mapLayout.getChildren().remove(n);
		}
	}
	
	private void revertToPosition(double x, double y) {
		currNode.setTranslateX(x);
		currNode.setTranslateY(y);
	}
	
	private Label makeLabel(String text) {
		Label l = new Label(text);
		l.setFont(textFont);
		return l;
	}
	
	private Button makeButton(String title, EventHandler<ActionEvent> handler) {
		Button b = new Button(title);
		b.setOnAction(handler);
		b.setFont(textFont);
		return b;
	}

	private void dragDrop(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();

		currNode.setTranslateX(x);
		currNode.setTranslateY(y);
	}

	private void copyActor() {
		// TODO: 
		// refactor with moveActor()
		Actor a = new Actor(currActor);
		addActor(a, 0, 0);
		
		Rectangle rect = new Rectangle(700, 700); // TODO: make it the size of the background image
		rect.setFill(Color.rgb(255, 0, 0, 0.3));
		rect.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> dragDrop(e));
		
		ToolBar hello = new ToolBar();
		hello.setMinWidth(700);
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		Label instru = makeLabel("Click on location for new actor");
		Button exitFilter = makeButton("Finished", e -> removeElements(rect, hello)); 
		hello.getItems().addAll(instru, spacer, exitFilter);
		
		mapLayout.getChildren().addAll(rect, hello);
	}
	
	private void editParams() {
		// TODO:
		// create a pop up dialog that a) contains current parameters
		// b) allows user to edit and change parameters (which are then saved)
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
	}
}
