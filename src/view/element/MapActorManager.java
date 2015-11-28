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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
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
 * 
 * Note: not totally a visual but has access and modifies the levelmap -> abstract visual.
 * I don't know. Also this class is poorly organized. I will refactor at some point.
 * 
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
		Image image = getImageFromActor(actor);
		Sprite sp = new Sprite(image);
		currNode = sp;
		
		ContextMenu cm = makeContextMenu();
		sp.setOnContextMenuRequested(e -> {
			currActor = actor;
			currNode = sp;
			cm.show(sp, e.getScreenX(), e.getScreenY());
		});
		
		setOnGraphWithOffset(sp, x, y, image.getWidth()/2, image.getHeight()/2);
		mapLayout.getChildren().add(sp);
	} 
	
	private Image getImageFromActor(Actor a) {
		String img = (String) currActor.getProperties().getComponents().get("image").getValue();
		return new Image(getClass().getClassLoader().getResourceAsStream(img));
	}
	
	// pass in the actual mouse drop coordinate. translates so that the display drops in the center.
	private void setOnGraphWithOffset(Node node, double x, double y, double xOffset, double yOffset) {
		node.setTranslateX(x - xOffset);
		node.setTranslateY(y - yOffset);
	}
	
	private void setOnGraph(double x, double y) {
		currNode.setTranslateX(x);
		currNode.setTranslateY(y);
	}

	private ContextMenu makeContextMenu() {
		ContextMenu cm = new ContextMenu();
		MenuItem moveActor = makeMenuItem("Move Actor", event -> moveActor(currNode.getTranslateX(), currNode.getTranslateY()));
		MenuItem copyActor = makeMenuItem("Copy Actor", event -> copyActor());
		MenuItem rotateActor = makeMenuItem("Rotate Actor", event -> rotateActor());
		MenuItem deleteActor = makeMenuItem("Delete Actor", event -> removeActor());
		MenuItem editParam = makeMenuItem("Edit Parameters", event -> editParams());
		MenuItem editShip = makeMenuItem("Edit Relationship with Another Actor", event -> editShip());
		
		cm.getItems().addAll(moveActor, copyActor, rotateActor, deleteActor, editParam, editShip);

		return cm;
	}
		
	private void moveActor(double origX, double origY) {
		Button undo = makeButton("Return to original position", e -> setOnGraph(origX, origY));
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		ToolBar hello = new ToolBar();
		Rectangle r = makeFilterRectangle(e -> dragDrop(e));
		makeToolbar(hello, "Click on new location for actor", e -> removeElements(r, hello), spacer, undo);
		mapLayout.getChildren().addAll(r, hello);
	}
	
	private void removeElements(Node... nodes) {
		for (Node n : nodes) {
			mapLayout.getChildren().remove(n);
		}
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
		Image image = getImageFromActor(currActor);
		setOnGraphWithOffset(currNode, e.getX(), e.getY(), image.getWidth()/2, image.getHeight()/2);
	}

	private void copyActor() {
		Actor a = new Actor(currActor);
		Image image = getImageFromActor(a);
		addActor(a, image.getWidth()/2, image.getHeight()/2);
		moveActor(0, 0);
	}
	
	private void rotateActor() {
		double heading = currNode.getRotate();
		currNode.setOnRotate(e -> rotateActor(e));
		Button enterVal = makeButton("Actual Degree", e -> rotateDialog(true));
		Button enterVal2 = makeButton("Relative Degree", e -> rotateDialog(false));
		Button reset = makeButton("Return to original", e -> currNode.setRotate(heading));
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		ToolBar hello = new ToolBar();	
		EventHandler<ActionEvent> finishHandle = event -> {
			removeElements(hello); 
			currNode.removeEventHandler(RotateEvent.ANY, e -> rotateActor(e));
		};
		makeToolbar(hello, "Scroll or input", finishHandle, enterVal, enterVal2, spacer, reset);
		mapLayout.getChildren().addAll(hello);
	}
	
	private void rotateActor(RotateEvent r) {
		double initialHeading = currNode.getRotate();
		currNode.setRotate(r.getAngle() + initialHeading);
	}
	
	// input is rgb color code
	// idea is to highlight an actor when an actor is selected - through a double click method?
	// and through a second double click, actor is deselected.
	private Rectangle selectActor(int red, int green, int blue) {
		Image image = getImageFromActor(currActor);
		Rectangle rect = new Rectangle(image.getWidth() + 10, image.getHeight() + 10);
		rect.setFill(Color.rgb(255, 0, 0, 0.2));
		return rect;
	}
	
	private void rotateDialog(boolean absolute) {
		double initialHeading = 0;
		
		if (!absolute) {
			initialHeading = currNode.getRotate();
		}
		
		TextInputDialog popup = new TextInputDialog();
		popup.setTitle("Rotation");
		popup.setHeaderText("Enter angle to rotate turtle clockwise (enter a negative number to rotate counterclockwise)");
		popup.showAndWait();
		
		String newVal = popup.getEditor().getText();
		try {
			double degrees = Double.parseDouble(newVal);
			currNode.setRotate(degrees + initialHeading);
		} catch (Exception e) {
			// TODO: user inputs not a number, show error
		}
	}
	
	private Rectangle makeFilterRectangle(EventHandler<MouseEvent> rectHandler) {
		// TODO: make rect & hello length of top of background image & 
		// make hello float at top of screen

		Rectangle rect = new Rectangle(700, 700); 
		rect.setFill(Color.rgb(255, 0, 0, 0.2));
		rect.addEventHandler(MouseEvent.MOUSE_CLICKED, rectHandler);
		
		return rect; 
	}
	
//	private void makeToolbar(String desc, EventHandler<MouseEvent> rectHandler, Node... options) {
//		ToolBar hello = new ToolBar();
//		hello.setMinWidth(700);
//		Label instru = makeLabel(desc);
//		Button exitFilter = makeButton("Finished", null);
//		if (rectHandler != null) {
//			Rectangle rect = makeFilterRectangle(rectHandler);
//			exitFilter.setOnAction(e -> removeElements(rect, hello));
//			mapLayout.getChildren().addAll(rect, hello);
//		} else {
//			exitFilter.setOnAction(e -> removeElements(hello));
//			mapLayout.getChildren().addAll(hello);
//		}
//
//		hello.getItems().addAll(instru);
//		for (Node b : options) {
//			hello.getItems().add(b);
//		}	
//		hello.getItems().add(exitFilter);
//		
//	}
	
	private void makeToolbar(ToolBar hello, String desc, EventHandler<ActionEvent> finishHandler, Node... options) {
			hello.setMinWidth(700);
			Label instru = makeLabel(desc);
			Button exit = makeButton("Finished", finishHandler);

			hello.getItems().addAll(instru, exit);
			for (int i = 0; i < options.length; i++) {
				hello.getItems().add(1 + i, options[i]);
			}
		}
	
	private void editParams() {
		// TODO:
		// create a pop up dialog that a) contains current parameters
		// b) allows user to edit and change parameters (which are then saved)
	}
	
	// i ship it ... ahahahahaha
	// ahahahahahahahahahahahaha
	// ahahahahahahahahahahahaha
	// ahahahahahahahahahahahaha
	// ahahahahahahahahahahahaha
	// ahahahahahahahahahahahaha
	private void editShip() {
		// TODO:
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
