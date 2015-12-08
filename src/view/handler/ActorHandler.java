package view.handler;

import java.util.ArrayList;
import java.util.List;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.map.Map;
import view.map.MapViewManager;
import view.map.MapZoomSlider;
import view.map.MiniMap;
import view.visual.AbstractVisual;

/**
 * This class essentially creates a) the context menu of an actor's imageview to
 * enable on-screen editing, with the assistance of the ActorEditingMenu and 
 * b) maintains a list of current ActorViews
 * 
 * @author Bridget
 *
 */
public class ActorHandler extends AbstractVisual {
	private MapViewManager viewManager;
	private ToolBar myToolbar;
	private Map map;
	private MiniMap theMiniMap;
	private MapZoomSlider theZoomSlider;
	private List<ActorView> myAVs;
	private ActorEditingToolbar myEditMenu;
	private AuthoringController myAC;

	public ActorHandler(Group layout, ToolBar tb, Map map, MiniMap miniMap, MapZoomSlider zoomSlider, AuthoringController ac) {
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
			System.out.println("HELLO");
		} else {
			// TODO: delete Actor
			Alert alert = new Alert(AlertType.ERROR, myResources.getString("whileeditingerror"), ButtonType.OK);
			alert.showAndWait();
		}
	}

	public void addActor(ActorView av, double x, double y) {
		if (!checkOutOfBounds(av, x, y)) {
			if (!myAVs.contains(av)) {
				myAVs.add(av);
				ImageView image = av.getImageView();
				ContextMenu cm = makeContextMenu(av);
				image.setOnContextMenuRequested(e -> {
					if (!myEditMenu.isEditing()) {
						cm.show(image, e.getScreenX(), e.getScreenY());
					}
				});
			}
			addToScale(av, x, y);
			viewManager.addElements(av.getImageView());
		}
	}
	
	private void addToScale(ActorView a, double x, double y) {
		double scale = theZoomSlider.getSlider().valueProperty().doubleValue();
		scale = 100 / (scale + 100);
		double xOffset = theMiniMap.getRectangleX();
		double yOffset = theMiniMap.getRectangleY();
		
		double scaleOffset = Double.parseDouble(myResources.getString("minimapscaleratio"));
		double newX = xOffset*scaleOffset + x * scale;
		double newY = yOffset*scaleOffset + y * scale;
		a.restoreXY(newX, newY);
	}

	private ContextMenu makeContextMenu(ActorView a) {
		ContextMenu cm = new ContextMenu();
		MenuItem moveActor = makeMenuItem(myResources.getString("move"), event -> moveActor(a));
		MenuItem copyActor = makeMenuItem(myResources.getString("copy"), event -> copyActor(a));
		MenuItem rotateActor = makeMenuItem(myResources.getString("rotate"), event -> rotateActor(a));
		MenuItem resizeActor = makeMenuItem(myResources.getString("resize"), event -> resizeActor(a));
		MenuItem deleteActor = makeMenuItem(myResources.getString("delete"), event -> removeActor(a));
		MenuItem editParam = makeMenuItem(myResources.getString("editparams"), event -> editParams());
		MenuItem editShip = makeMenuItem(myResources.getString("editship"), event -> editShip());

		cm.getItems().addAll(moveActor, copyActor, rotateActor, resizeActor, deleteActor, editParam, editShip);

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
		r.setCursor(Cursor.CLOSED_HAND);
		Dragboard db = r.startDragAndDrop(TransferMode.MOVE);
		ClipboardContent content = new ClipboardContent();
		content.putString(a.getXCoor() + " " + a.getYCoor());
		db.setContent(content);
		db.setDragView(a.getImageView().getImage(), a.getImageView().getImage().getWidth() / 2,
				a.getImageView().getImage().getHeight() / 2);
	}

	private void dropMoveDrag(DragEvent event, ActorView a, Rectangle r) {
		r.setCursor(Cursor.DEFAULT);
		viewManager.removeElements(r);
		if (!checkOutOfBounds(a, event.getX(), event.getY())) {
			a.restoreXY(event.getX(), event.getY());
		}
		viewManager.addElements(a.getImageView(), r);
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
		Node currNode = a.getImageView();
		double heading = currNode.getRotate();
		int rotateInc = Integer.parseInt(myResources.getString("rotateIncrement"));
		myEditMenu.makeRotateActorToolbar(a, heading, r, e -> rotateRight(a, rotateInc), e -> rotateRight(a, -1 * rotateInc));
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
		myEditMenu.makeResizeActorToolbar(a, r, e -> increaseActorSize(a, growInc), e -> increaseActorSize(a, -1 * growInc));
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

	protected void removeActor(ActorView a) {
		viewManager.removeElements(a.getImageView()); 
	}

	public void removeActor(Node element) {
		viewManager.removeElements(element);
	}

	private void editParams() {

	}

	private void editShip() {

	}
	
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
		myEditMenu.setEditing(false);;
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
}
