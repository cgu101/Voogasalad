package view.element;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
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
 * I am going to refactor the mess that is MapActorManager partially into this class. Wait and behold.
 * 
 * @author Bridget
 *
 */
public class ActorHandler extends AbstractVisual {
	private Actor currActor;
	private Node currNode;
	private AuthoringController myController;
	private MapViewManager viewManager;
	private ToolBar myToolbar;
	private Label defaultLabel;
	
	public ActorHandler(Group layout, AuthoringController ac, ToolBar tb) {
		myController = ac;
		viewManager = new MapViewManager(layout);
		currActor = null;
		currNode = null;
		myToolbar = tb;
		findResources();
		defaultLabel = makeLabel(myResources.getString("defaultPrompt"));
		restoreToolbar();
	}
	
	public void addActor(Actor a, double x, double y) {
		currActor = a;
		Image image = getImageFromActor(a);
		Sprite sp = new Sprite(image);
		currNode = sp;
		ContextMenu cm = makeContextMenu();
		sp.setOnContextMenuRequested(e -> {
			currActor = a;
			currNode = sp;
			cm.show(sp, e.getScreenX(), e.getScreenY());
		});
		viewManager.addActor(sp, x, y, image.getWidth(), image.getHeight());
	}
	
	private Image getImageFromActor(Actor a) {
		String img = (String) currActor.getProperties().getComponents().get("image").getValue();
		return new Image(getClass().getClassLoader().getResourceAsStream(img));
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

	private MenuItem makeMenuItem(String title, EventHandler<ActionEvent> handler) {
		MenuItem res = new MenuItem(title);
		res.setOnAction(handler);
		return res;
	}

	private void moveActor(double origX, double origY) {
		Rectangle r = makeFilterRectangle(e -> dragDrop(e));
		Button undo = makeButton("Return to original position", e -> moveActorWithoutOffset(origX, origY));
		Pane spacer = makeSpacer();
		Button finish = makeFinishButton(r); 
		replaceToolbar(makeLabel("Click on new location for actor"), spacer, undo, finish);
	}
	
	private void dragDrop(MouseEvent e) {
		Image image = getImageFromActor(currActor);
		viewManager.setOnGraphWithOffset(currNode, e.getX(), e.getY(), image.getWidth()/2, image.getHeight()/2);
		currActor.setProperty("xLocation", "" + (e.getX()));
		currActor.setProperty("yLocation", "" + (e.getY()));
	}
	
	private void moveActorWithoutOffset(double x, double y) {
		Image image = getImageFromActor(currActor);
		viewManager.setOnGraph(currNode, x, y);
		currActor.setProperty("xLocation", "" + (x + image.getWidth()/2));
		currActor.setProperty("yLocation", "" + (y + image.getHeight()/2));
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
		Button finish = makeFinishButton();
		Pane spacer = makeSpacer();
//		EventHandler<ActionEvent> finishHandle = event -> { TODO:?
//			viewManager.removeElements(hello); 
//			currNode.removeEventHandler(RotateEvent.ANY, e -> rotateActor(e));
//		};
		replaceToolbar(makeLabel("Scroll or input"), enterVal, enterVal2, spacer, reset, finish);
	}
	
	private void rotateActor(RotateEvent r) {
		// TODO:
		double initialHeading = currNode.getRotate();
		viewManager.rotateActor(currNode, initialHeading + r.getAngle()); 
	}

	private void rotateDialog(boolean absolute) {
		double initialHeading = 0;

		if (!absolute) {
			initialHeading = viewManager.getRotation(currNode);
		}

		TextInputDialog popup = new TextInputDialog();
		popup.setTitle("Rotation");
		popup.setHeaderText("Enter angle to rotate turtle clockwise (enter a negative number to rotate counterclockwise)");
		popup.showAndWait();

		String newVal = popup.getEditor().getText();
		try {
			double degrees = Double.parseDouble(newVal);
			System.out.println("OK");
			viewManager.rotateActor(currNode, degrees + initialHeading);
//			currActor.setProperty("Rotation", "" + (degrees + initialHeading));
		} catch (Exception e) {
			e.printStackTrace();
			Alert error = new Alert(AlertType.ERROR, "You did not input a valid number", ButtonType.OK);
			error.showAndWait();
		}
	}
	
	private void removeActor() {
		// TODO: tell backend
		removeActor(currNode);
		currNode = null;
		currActor = null;
	}
	
	protected void removeActor(Node node) {
		viewManager.removeActor(node); // TODO: 
	}
	
	private void editParams() {
		
	}
	
	private void editShip() {
		
	}
	
//	private void makeToolbar(ToolBar hello, String desc, EventHandler<ActionEvent> finishHandler, Node... options) {
//		Label instru = makeLabel(desc);
//		Button exit = makeButton("Finished", finishHandler);
//
//		hello.getItems().addAll(instru, exit);
//		for (int i = 0; i < options.length; i++) {
//			hello.getItems().add(1 + i, options[i]);
//		}
//	}
	
	private Rectangle makeFilterRectangle(EventHandler<MouseEvent> rectHandler) {
		// TODO: size & dimension of Rectangle
		Rectangle rect = new Rectangle(700, 700); 
		rect.setFill(Color.rgb(255, 0, 0, 0.2));
		rect.addEventHandler(MouseEvent.MOUSE_CLICKED, rectHandler);
		viewManager.addElements(rect);
		return rect; 
	}
	
	private Label makeLabel(String desc) {
		Label l = new Label(desc);
		l.setFont(textFont);
		return l;
	}
	
	private Button makeButton(String title, EventHandler<ActionEvent> handler) {
		Button b = new Button(title);
		b.setOnAction(handler);
		b.setFont(textFont);
		return b;
	}
	
	private Button makeFinishButton(Node... elementsToRemove) {
		return makeButton("Finish", e -> {
			viewManager.removeElements(elementsToRemove);
			restoreToolbar();
		});
	}
	
	private Pane makeSpacer() {
		Pane spacer = new Pane(); 
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		return spacer;
	}
	
	private void replaceToolbar(Node... nodes) {
		myToolbar.getItems().clear();
		for (Node n : nodes) {
			myToolbar.getItems().add(n);
		}
	}

	private void restoreToolbar() {  
		myToolbar.getItems().clear();
		myToolbar.getItems().add(defaultLabel);
	}

	// TODO:
		// input is rgb color code
		// idea is to highlight an actor when an actor is selected - through a double click method?
		// and through a second double click, actor is deselected.
//		private Rectangle selectActor(int red, int green, int blue) {
//			Image image = getImageFromActor(currActor);
//			Rectangle rect = new Rectangle(image.getWidth() + 10, image.getHeight() + 10);
//			rect.setFill(Color.rgb(255, 0, 0, 0.2));
//			return rect;
//		}

	protected void updateBackground(Node n) {
		viewManager.updateBackground(n);
	}
}
