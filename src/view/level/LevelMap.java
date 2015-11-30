package view.level;

import java.util.Date;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import view.element.Map;
import view.screen.AbstractScreen;

public class LevelMap extends Map implements LevelInterface {
	private Tab myTab;
	private ScrollPane sp;
	private String myString;
	private AuthoringController controller;

	public LevelMap(GridPane pane, int i, AbstractScreen screen) {
		super(pane);
		findResources();
		myString = myResources.getString("tabName");
		myTab = new Tab(makeTitle(i));
		myTab.setContent(pane);
		myTab.setId(Integer.toString(i));

		mapScrollableArea.setOnDragEntered(event -> startDrag(event));

		mapScrollableArea.setOnDragExited(event -> exitDrag(event));

		mapScrollableArea.setOnDragOver(event -> dragAroundMap(event));
		mapScrollableArea.setOnDragDropped(event -> dragFinished(event));

		// mapArea.addEventHandler(MouseEvent.DRAG_DETECTED, event -> {
		// System.out.println("Here");
		// Dragboard db = mapArea.startDragAndDrop(TransferMode.ANY);
		// db.setDragView(new Image("rcd.jpg")); // <- method only works in drag
		// detected event??
		// // so won't work on dragOver. :(
		// });
	}

	private void dragFinished(DragEvent event) {
		Dragboard db = event.getDragboard();

		boolean success = false;
		if (db.hasString()) {
			String actor = db.getString();
			ActorPropertyMap map = controller.getAuthoringActorConstructor().getActorPropertyMap(actor);

			map.addProperty("xLocation", "" + (event.getX()));
			map.addProperty("yLocation", "" + (event.getY())); // TODO Change
																// this

			String uniqueID = new Date().toString();
			controller.getLevelConstructor().getActorGroupsConstructor().updateActor(uniqueID, map);
			Actor a = controller.getLevelConstructor().getActorGroupsConstructor().getActor(actor, uniqueID);

			addActor(a, (double) a.getProperties().getComponents().get("xLocation").getValue(),
					(double) a.getProperties().getComponents().get("yLocation").getValue());
			success = true;
		}
		event.setDropCompleted(success);
		event.consume();
		System.out.println(myTab.getTabPane().getBoundsInParent());

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

	public Tab getTab() {
		return myTab;
	}

	public AuthoringController getController() {
		return controller;
	}

	@Override
	public String makeTitle(int i) {
		return myString + (i + 1);
	}
}
