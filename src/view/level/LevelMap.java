package view.level;

import java.util.Date;
import java.util.ResourceBundle;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import util.Sprite;
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
		controller = new AuthoringController();
		mapArea.setOnDragEntered(event -> dragIntoMap(event));
		mapArea.setOnDragExited(event -> dragOutsideMap(event));
		mapArea.setOnDragOver(event -> dragAroundMap(event));
		mapArea.setOnDragDropped(event -> dragDrop(event));
	}

	private void dragDrop(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasString()) {
			String actor = db.getString();
			ActorPropertyMap map = controller.getAuthoringActorConstructor().getActorPropertyMap(actor);

			// map.addProperty("xLocation", "" + (event.getSceneX() -
			// this.getPane().getLayoutX()));
			// map.addProperty("yLocation", "" + (event.getSceneY() -
			// this.getPane().getLayoutY()));

			map.addProperty("xLocation", "" + (event.getX()));
			map.addProperty("yLocation", "" + (event.getY())); // TODO Change
																// this

			// map.addProperty("xLocation", "" + (event.getSceneX()));
			// map.addProperty("yLocation", "" + (event.getSceneY() - 50));
			// //TODO Change this
			String uniqueID = new Date().toString();
			controller.getLevelConstructor().getActorGroupsConstructor().updateActor(uniqueID, map);

			Actor a = controller.getLevelConstructor().getActorGroupsConstructor().getActor(actor, uniqueID);

			String img = (String) a.getProperties().getComponents().get("image").getValue();
			Image image = new Image(getClass().getClassLoader().getResourceAsStream(img));
			Sprite newSp = new Sprite(image);

			addActor(newSp, (double) a.getProperties().getComponents().get("xLocation").getValue(),
					(double) a.getProperties().getComponents().get("yLocation").getValue());

			success = true;
		}
		event.setDropCompleted(success);
		event.consume();
	}

	private void dragIntoMap(DragEvent event) {
		if (event.getGestureSource() != mapArea && event.getDragboard().hasString()) {
			background.setBlendMode(BlendMode.MULTIPLY);
		}
		event.consume();
	}

	private void dragOutsideMap(DragEvent event) {
		if (event.getGestureSource() != mapArea && event.getDragboard().hasString()) {
			background.setBlendMode(null);
		}
		event.consume();
	}

	private void dragAroundMap(DragEvent event) {
		if (event.getGestureSource() != mapArea && event.getDragboard().hasString()) {
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
