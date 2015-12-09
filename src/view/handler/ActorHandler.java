package view.handler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import authoring.controller.AuthoringController;
import authoring.model.Anscestral;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import authoring.model.properties.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import network.framework.GameWindow;
import network.framework.format.Mail;
import network.framework.format.Request;
import network.instances.DataDecorator;
import view.map.Map;
import view.map.MapViewManager;
import view.map.MapZoomSlider;
import view.map.MiniMap;
import view.visual.AbstractVisual;

/**
 * This class essentially creates a) the context menu of an actor's imageview to
 * enable on-screen editing, with the assistance of the ActorEditingMenu and b)
 * maintains a list of current ActorViews
 * 
 * @author Bridget
 *
 */
public class ActorHandler extends AbstractVisual implements Anscestral {
	private MapViewManager viewManager;
	private ToolBar myToolbar;
	private Map map;
	private MiniMap theMiniMap;
	private MapZoomSlider theZoomSlider;
	private List<ActorView> myAVs;
	private ActorEditingToolbar myEditMenu;
	private AuthoringController myAC;
	private Deque<String> anscestors;

	public ActorHandler(Group layout, ToolBar tb, Map map, MiniMap miniMap, MapZoomSlider zoomSlider,
			AuthoringController ac) {
		viewManager = new MapViewManager(layout);
		myToolbar = tb;
		this.map = map;
		theMiniMap = miniMap;
		theZoomSlider = zoomSlider;
		myAC = ac;
		findResources();
		myEditMenu = new ActorEditingToolbar(myToolbar, map, viewManager);
		myAVs = new ArrayList<ActorView>();
	}

	public void addActor(Actor a, ActorPropertyMap map, String actorType, double x, double y) {
		if (!myEditMenu.isEditing()) {
			ActorView av = new ActorView(a, map, actorType, x, y, myAC);
			addActor(av, x, y);
		} else {
			myAC.getLevelConstructor().getActorGroupsConstructor().deleteActor(a.getGroupName(), a.getUniqueID());
			Alert alert = new Alert(AlertType.ERROR, myResources.getString("whileeditingerror"), ButtonType.OK);
			alert.showAndWait();
		}
	}

	public void addActor(ActorView av, double x, double y) {
		if (!checkOutOfBounds(av, x, y)) {
			if (!myAVs.contains(av)) {
				myAVs.add(av);
				ImageView image = av.getSprite();
				ContextMenu cm = makeContextMenu(av);
				image.setOnContextMenuRequested(e -> {
					if (!myEditMenu.isEditing()) {
						cm.show(image, e.getScreenX(), e.getScreenY());
					}
				});
			}
			addToScale(av, x, y);
			viewManager.addElements(av.getSprite());
			// TODO send the actor here
			ActorSerializable actor = new ActorSerializable(av.getActor(), av.getMap(), av.getType(), x, y);
			DataDecorator dataMail = new DataDecorator(Request.ADD, actor, anscestors);
			GameWindow.getInstance().send(dataMail);
		}
	}

	private void addActor(ActorSerializable av) {
		ActorView actor = new ActorView(av.a, av.map, av.actorType, av.x, av.y, myAC);
		if (!checkOutOfBounds(actor, av.x, av.y)) {
			if (!myAVs.contains(av)) {
				myAVs.add(actor);
				ImageView image = actor.getSprite();
				ContextMenu cm = makeContextMenu(actor);
				image.setOnContextMenuRequested(e -> {
					if (!myEditMenu.isEditing()) {
						cm.show(image, e.getScreenX(), e.getScreenY());
					}
				});
			}
			addToScale(actor, av.x, av.y);
			viewManager.addElements(actor.getSprite());
		}
	}

	private void addToScale(ActorView a, double x, double y) {
		double scale = theZoomSlider.getSlider().valueProperty().doubleValue();
		scale = 100 / (scale + 100);
		double xOffset = theMiniMap.getRectangleX();
		double yOffset = theMiniMap.getRectangleY();

		double scaleOffset = Double.parseDouble(myResources.getString("minimapscaleratio"));
		double newX = xOffset * scaleOffset + x * scale;
		double newY = yOffset * scaleOffset + y * scale;
		a.restoreXY(newX, newY);
	}

	private ContextMenu makeContextMenu(ActorView a) {
		ContextMenu cm = new ContextMenu();
		MenuItem moveActor = makeMenuItem(myResources.getString("move"), event -> moveActor(a));
		MenuItem copyActor = makeMenuItem(myResources.getString("copy"), event -> copyActor(a));
		MenuItem rotateActor = makeMenuItem(myResources.getString("rotate"), event -> rotateActor(a));
		MenuItem resizeActor = makeMenuItem(myResources.getString("resize"), event -> resizeActor(a));
		MenuItem deleteActor = makeMenuItem(myResources.getString("delete"), event -> removeActor(a));
		MenuItem editParam = makeMenuItem(myResources.getString("editparams"), event -> editParams(a));
		//		MenuItem editShip = makeMenuItem(myResources.getString("editship"), event -> editShip());

		cm.getItems().addAll(moveActor, copyActor, rotateActor, resizeActor, editParam, deleteActor); //, editParam, editShip);

		return cm;
	}

	private MenuItem makeMenuItem(String title, EventHandler<ActionEvent> handler) {
		MenuItem res = new MenuItem(title);
		res.setOnAction(handler);
		return res;
	}

	private void moveActor(ActorView a) {
		map.setPanEnabled(false);
		double origX = a.getXCoor();
		double origY = a.getYCoor();
		Rectangle r = makeFilterRectangle();
		r.setOnDragDetected(e -> startMoveDrag(e, a, r));
		r.setOnDragOver(e -> duringMoveDrag(e));
		r.setOnDragDone(e -> endMoveDrag(e, a, r));
		r.setOnDragDropped(e -> dropMoveDrag(e, a, r));

		myEditMenu.makeMoveActorToolbar(a, r, e -> a.restoreXY(origX, origY));
	}

	private void duringMoveDrag(DragEvent e) {
		e.acceptTransferModes(TransferMode.MOVE);
	}

	private void startMoveDrag(MouseEvent e, ActorView a, Rectangle r) {
		removeActor(a);

		// TODO Send to network to remove the actor
		r.setCursor(Cursor.CLOSED_HAND);
		Dragboard db = r.startDragAndDrop(TransferMode.MOVE);
		ClipboardContent content = new ClipboardContent();
		content.putString(a.getXCoor() + " " + a.getYCoor());
		db.setContent(content);
		db.setDragView(a.getSprite().getCroppedImage(), a.getSprite().getCroppedImage().getWidth() / 2,
				a.getSprite().getCroppedImage().getHeight() / 2);
	}

	private void dropMoveDrag(DragEvent event, ActorView a, Rectangle r) {
		r.setCursor(Cursor.DEFAULT);
		viewManager.removeElements(r);
		if (!checkOutOfBounds(a, event.getX(), event.getY())) {
			a.restoreXY(event.getX(), event.getY());
		}
		viewManager.addElements(a.getSprite(), r);
		event.setDropCompleted(true);
		event.consume();
	}

	private void endMoveDrag(DragEvent e, ActorView a, Rectangle r) {
		e.getDragboard().clear();
		r.setCursor(Cursor.DEFAULT);
		e.consume();
	}

	private void copyActor(ActorView a) {
		ActorView aCopy = new ActorView(a);
		double offset = Double.parseDouble(myResources.getString("copyoffset"));
		addActor(aCopy, aCopy.getXCoor() + offset, aCopy.getYCoor() + offset);
		moveActor(aCopy);
	}

	private void rotateActor(ActorView a) {
		map.setPanEnabled(false);
		Rectangle r = makeFilterRectangle();
		r.setOnScroll(e -> handleScroll(e, a));
		Node currNode = a.getSprite();
		double heading = currNode.getRotate();
		int rotateInc = Integer.parseInt(myResources.getString("rotateIncrement"));
		myEditMenu.makeRotateActorToolbar(a, heading, r, e -> rotateRight(a, rotateInc),
				e -> rotateRight(a, -1 * rotateInc));
	}

	private void handleScroll(ScrollEvent e, ActorView a) {
		a.setRotation(a.getRotation() - e.getDeltaY());
	}

	private void rotateRight(ActorView a, double i) {
		a.setRotation(a.getRotation() + i);
	}

	private void resizeActor(ActorView a) {
		map.setPanEnabled(false);
		Rectangle r = makeFilterRectangle();
		r.setOnScroll(e -> handleResize(e, a));
		int growInc = Integer.parseInt(myResources.getString("growIncrement"));
		myEditMenu.makeResizeActorToolbar(a, r, e -> increaseActorSize(a, growInc),
				e -> increaseActorSize(a, -1 * growInc));
	}

	private void handleResize(ScrollEvent e, ActorView a) {
		increaseActorSize(a, e.getDeltaY());
	}

	private void increaseActorSize(ActorView a, double increase) {
		a.addDimensions(increase);
		if (checkOutOfBounds(a, a.getXCoor(), a.getYCoor()) || a.getWidth() <= 0) {
			a.addDimensions(-1 * increase);
		}
	}

	private void removeActor(ActorView a) {
		myAVs.remove(a);
		myAC.getLevelConstructor().getActorGroupsConstructor().deleteActor(a.getActor().getGroupName(), a.getActor().getUniqueID());
		viewManager.removeElements(a.getSprite()); 
	}

	public void removeActor(Node element) {
		viewManager.removeElements(element);
	}

	public void clearMap() {
		myAVs.clear();
		viewManager.removeAll();
	}

	private void editParams(ActorView a) {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Actor: " + a.getActor().getUniqueID());
		dialog.setHeaderText("Edit Parameters");

		// Set the button types.
		ButtonType okayButton = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(okayButton);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		System.out.println("Old Actor:"+a.getActor().getGroupName()+ ":"+a.getXCoor()+","+a.getYCoor());
		int i = 0;
		for(Property<?> p : a.getActor().getProperties()){
			if(!p.getUniqueID().equals("groupID")&&!p.getUniqueID().equals("image")){
				TextField t = new TextField();
				t.insertText(0, p.getValue().toString());
				grid.add(new Label(p.getUniqueID()), 0, i);
				grid.add(t, 1, i);
				i++;
				t.textProperty().addListener((observable, oldValue, newValue) -> {
					try {
						double newVal = Double.parseDouble(newValue);
						p.setValue(newVal);
						updateNode(a, p.getUniqueID(), newVal);
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR, myResources.getString("doubleerror"), ButtonType.OK);
						alert.showAndWait();
					}
				});
			}
		}
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
	}

	private void updateNode(ActorView a, String uniqueID, double newVal) {

		// basically a switch/case tree but allows us to use resources file
		// sorry i couldn't think of a better thing.
		if (uniqueID.equals(myResources.getString("x"))) {
			a.setXCoor(newVal);
		} else if (uniqueID.equals(myResources.getString("y"))) {
			a.setYCoor(newVal);
		} else if (uniqueID.equals(myResources.getString("angle"))) {
			a.setRotation(newVal);
		} else if (uniqueID.equals(myResources.getString("width"))) {
			a.setWidth(newVal);
		} else if (uniqueID.equals(myResources.getString("height"))) {
			a.setHeight(newVal);
		}
	}

	//
	//	private void editShip() {
	//
	//	}

	private Rectangle makeFilterRectangle() {
		Rectangle rect = new Rectangle(map.getMapWidth(), map.getMapHeight());
		double opacity = Double.parseDouble(myResources.getString("opacity"));
		rect.setFill(Color.rgb(255, 0, 0, opacity));
		rect.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> finish(rect));
		viewManager.addElements(rect);
		myEditMenu.setEditing(true);
		return rect;
	}

	protected void finish(Node... elementsToRemove) {
		viewManager.removeElements(elementsToRemove);
		myEditMenu.restoreToolbar();
		map.setPanEnabled(true);
		myEditMenu.setEditing(false);
	}

	// returns true if out-of-bounds
	private boolean checkOutOfBounds(ActorView av, double x, double y) {
		map.getPane().getWidth();
		if (((x - av.getWidth() / 2) < 0) || ((x + av.getWidth() / 2) > map.getMapWidth())
				|| ((y - av.getHeight() / 2) < 0) || ((y + av.getHeight() / 2) > map.getMapHeight())) {
			Alert error = new Alert(AlertType.ERROR, myResources.getString("outofboundserror"), ButtonType.OK);
			error.showAndWait();
			return true;
		}
		return false;
	}

	public void updateBackground(ImageView n) {
		viewManager.updateBackground(n);
	}

	@Override
	public Deque<String> getAnscestralPath() {
		return anscestors;
	}

	@Override
	public void process(Mail mail) {
		ActorSerializable actor = (ActorSerializable) mail.getData();
		Request request = mail.getRequest();

		switch (request) {
		case ADD: {
			addActor(actor);
			break;
		}
		case DELETE: {
//			removeActor(actor);
			break;
		}
		case MODIFY: {
//			removeActor(actor);
//			addActor(actor);
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	public Anscestral getChild(String id) {
		// Doesn't have any children
		return null;
	}

	public void setDeque(Deque<String> anscestors) {
		this.anscestors = new ArrayDeque<String>(anscestors);
		this.anscestors.add(ActorHandler.class.getName());
	}

	public void updateObservers(Object o) {
		setChanged();
		notifyObservers(o);
	}
}
