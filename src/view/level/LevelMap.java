package view.level;

import java.util.Date;
import java.util.Deque;

import authoring.controller.AuthoringController;
import authoring.model.Anscestral;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import authoring.model.level.Level;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import network.framework.GameWindow;
import network.framework.format.Mail;
import view.map.Map;
import view.screen.AbstractScreen;

public class LevelMap extends Map implements LevelInterface {

	private static final String LEVEL_IDENTIFIER = "Level";
	private static final String SPLASH_IDENTIFIER = "Splash";

	/**
	 * Visual Variables
	 */
	private Tab myTab;
	private ScrollPane sp;
	private String myString;

	/**
	 * Identifiers and Data
	 */
	private String myTitle;
	private Deque<String> myPath;
	private Level myLevel;

	private LevelType type;

	public LevelMap(GridPane pane, Level l, AbstractScreen screen) {
		super(pane);
		findResources();

		myString = myResources.getString("tabName");
		myTab = new Tab(l.getUniqueID());
		myTitle = l.getUniqueID();

		myTab.setContent(pane);
		myTab.setId(l.getUniqueID());
		myTab.setContextMenu(createContextMenu());
		
		mapScrollableArea.setOnDragEntered(event -> startDrag(event));

		mapScrollableArea.setOnDragExited(event -> exitDrag(event));

		mapScrollableArea.setOnDragOver(event -> dragAroundMap(event));
		mapScrollableArea.setOnDragDropped(event -> dragFinished(event));
		myLevel = l;

		setLevelType();

		if (type == LevelType.SPLASH) {
			this.removeMiniMap();
			this.removeMap();
		}
	}

	private void setLevelType() {
		// TODO Auto-generated method stub
		if (myLevel.getUniqueID().startsWith(LEVEL_IDENTIFIER)) {
			type = LevelType.LEVEL;
		} else if (myLevel.getUniqueID().startsWith(SPLASH_IDENTIFIER)) {
			type = LevelType.SPLASH;
		} else {
			type = null;
		}
	}
	
	private void dragFinished(DragEvent event) {
		Dragboard db = event.getDragboard();

		boolean success = false;
		if (db.hasString()) {
			String actor = db.getString();
			ActorPropertyMap map = controller.getAuthoringActorConstructor().getActorPropertyMap(actor);

			map.addProperty("xLocation", "" + (event.getX()));
			map.addProperty("yLocation", "" + (event.getY()));

			String uniqueID = new Date().toString();
			controller.getLevelConstructor().getActorGroupsConstructor().updateActor(uniqueID, map);
			Actor a = controller.getLevelConstructor().getActorGroupsConstructor().getActor(actor, uniqueID);

			addActor(a, map, actor, (double) a.getProperties().getComponents().get("xLocation").getValue(),
					(double) a.getProperties().getComponents().get("yLocation").getValue());
			success = true;
			// gameWindow.getClient().send("New Drop Event");
		}
		event.setDropCompleted(success);
		event.consume();
	}

	private void createMap(ActorPropertyMap apm) {
		// TODO?
		apm.addProperty("xLocation", "0.0");
		apm.addProperty("yLocation", "0.0");
		apm.addProperty("Rotation", "0.0");
	}

	private void startDrag(DragEvent event) {
		if (event.getGestureSource() != mapScrollableArea && event.getDragboard().hasString()) {
			background.setBlendMode(BlendMode.MULTIPLY);
		}
		event.consume();
	}

	private void exitDrag(DragEvent event) {
		if (event.getGestureSource() != mapScrollableArea && event.getDragboard().hasString()) {
			background.setBlendMode(null);
		}
		event.consume();
	}

	private void dragAroundMap(DragEvent event) {
		if (event.getGestureSource() != mapScrollableArea && event.getDragboard().hasString()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
		event.consume();
	}
	
	private ContextMenu createContextMenu() {
		ContextMenu cm = new ContextMenu();
		MenuItem rename = makeMenuItem(myResources.getString("rename"), e -> renameLevel());
		MenuItem delete = makeMenuItem(myResources.getString("delete"), e -> deleteLevel());
		MenuItem copy = makeMenuItem(myResources.getString("copy"), e -> copyLevel());
		cm.getItems().addAll(rename, copy, delete);
		return cm;
	}
	
	private void renameLevel() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setContentText(myResources.getString("nameLevelInstru"));
		dialog.setTitle(myResources.getString("rename"));
		dialog.showAndWait();
		
		String s = dialog.getEditor().getText();
		myTab.setText(s);
	}
	
	private void deleteLevel() {
		//TODO:
	}
	
	private void copyLevel() {
		//TODO:
	}
	
	private MenuItem makeMenuItem(String title, EventHandler<ActionEvent> handler) {
		MenuItem mi = new MenuItem(title);
		mi.setOnAction(handler);
		return mi;
	}

	public Tab getTab() {
		return myTab;
	}

	public AuthoringController getController() {
		return this.controller;
	}

	// @Override
	// public String makeTitle(int i) {
	// return (i + 1) + myString;
	// }

	@Override
	public String getTitle() {
		return myTitle;
	}

	public GridPane getPane() {
		return this.pane;
	}

	@Override
	public void redraw(Level modelLevel) {
		// TODO Auto-generated method stub
		System.out.println("I am redrawing");
	}

	@Override
	public Level buildLevel() {
		// TODO Auto-generated method stub
		return myLevel;
	}

	@Override
	public Deque<String> getAnscestralPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(Mail mail) {
		// TODO Auto-generated method stub

	}

	@Override
	public Anscestral getChild(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public LevelType getType() {
		return type;
	}

}
