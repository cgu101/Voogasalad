package view.level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import authoring.controller.AuthoringController;
import authoring.model.Anscestral;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import authoring.model.bundles.Bundle;
import authoring.model.level.Level;
import authoring.model.properties.Property;
import authoring.model.tree.InteractionTreeNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
import network.framework.format.Request;
import network.instances.DataDecorator;
import view.map.Map;
import view.screen.AbstractScreen;

public class LevelMap extends Map implements Anscestral {

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
	private Deque<String> anscestors;
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
		
		/**
		 * Add level triggers, ...
		 */
		// TODO: Chris: might mess with networking:
		
		this.controller.getLevelConstructor().buildLevel(myLevel);
		this.controller.getLevelConstructor().buildConstructor(myLevel);

		setLevelType();

		if (type == LevelType.SPLASH) {
			this.removeMiniMap();
		}
	}

	private void setLevelType() {
		if (myLevel.getProperty(myResources.getString("type")).getValue().equals(LEVEL_IDENTIFIER)) {
			type = LevelType.LEVEL;
		} else if (myLevel.getProperty(myResources.getString("type")).getValue().equals(SPLASH_IDENTIFIER)) {
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
		cm.getItems().addAll(rename);
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

	public String getTitle() {
		return myTitle;
	}

	public GridPane getPane() {
		return this.pane;
	}

	public void redraw(Level modelLevel) {
		clearActors();
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for (Entry<String,Bundle<Actor>> b : myLevel.getActorGroups().getMap().entrySet()) {
			for (Actor actor : b.getValue().getComponents().values()) {
				ActorPropertyMap map = controller.getAuthoringActorConstructor().getActorPropertyMap(b.getKey());
				addActor(actor, map, b.getKey(), (double) actor.getProperties().getComponents().get("xLocation").getValue(),
						(double) actor.getProperties().getComponents().get("yLocation").getValue());
			}
		}
	}
	// used in loading
	public void buildLevel() {
		redraw(myLevel);
	}
	
//	@Override
//	public void addActor(Actor element, ActorPropertyMap map, String actorType, double x, double y) {
//		super.addActor(element, map, actorType, x, y);
//	}
//	
//	@Override
//	public void removeActor(Node element) {
//		super.removeActor(element);
//	}

	@Override
	public Deque<String> getAnscestralPath() {
		return anscestors;
	}

	@Override
	public void process(Mail mail) {
		if(mail.getData() instanceof InteractionTreeNode ) {
			InteractionTreeNode toReplace = (InteractionTreeNode) mail.getData();
			myLevel.overWriteTree(toReplace);
			System.out.println("Just replaced the node");
		} else {
			System.out.println("SOMETHING BAD HAPPENED AND TREENODE WAS NOT A TREENODE");
		}
	}
	
	@Override
	public Anscestral getChild(String id) {
		return (Anscestral) getActorHandler();
	}
	
	public void setDeque(Deque<String> anscestors) {
		this.anscestors = new ArrayDeque<String>(anscestors);
		this.anscestors.add(myLevel.getUniqueID());
		getActorHandler().setDeque(this.anscestors);
	}

	public LevelType getType() {
		return type;
	}

	public void updateLevelProperty(Property<String> property) {
		myLevel.getPropertyBundle().add(property);
	}
	
	private void initializeTimer() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
				DataDecorator dataMail = new DataDecorator(Request.NODE, myLevel.getRootTree(), new ArrayDeque<String>(anscestors));
				GameWindow.getInstance().send(dataMail);
		    }
	
		}, 0, 5000);
	}

}
