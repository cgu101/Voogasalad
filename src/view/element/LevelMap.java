package view.element;

import java.util.Date;

import authoring.controller.AuthoringController;
import authoring.model.actors.ActorPropertyMap;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import view.screen.AbstractScreen;

public class LevelMap extends Map {
	private Tab myTab;
	private ScrollPane sp;
	private AuthoringController controller;

	public LevelMap(GridPane pane, int i, AbstractScreen screen) {
		super(pane, screen);
		myTab = new Tab("Level " + (i + 1));
		myTab.setContent(pane);
		myTab.setId(Integer.toString(i));
		controller = new AuthoringController();
		mapArea.setOnDragEntered(event -> {
			if (event.getGestureSource() != mapArea && event.getDragboard().hasString()) {
				background.setBlendMode(BlendMode.MULTIPLY);
			}

			event.consume();
		});
		mapArea.setOnDragExited(event -> {
			if (event.getGestureSource() != mapArea && event.getDragboard().hasString()) {
				background.setBlendMode(null);
			}

			event.consume();
		});
		mapArea.setOnDragOver(event -> {
			if (event.getGestureSource() != mapArea && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.ANY);
			}
			event.consume();
		});
		mapArea.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				String actor = db.getString();
				ActorPropertyMap map = controller.getAuthoringActorConstructor().getActorPropertyMap(actor);

				map.addProperty("xLocation", "" + (event.getSceneX() - this.getPane().getLayoutX()));
				map.addProperty("yLocation", "" + (event.getSceneY() - this.getPane().getLayoutY()));
				controller.getLevelConstructor().getActorGroupsConstructor().updateActor(new Date().toString(), map);
				success = true;
			}
			event.setDropCompleted(success);
			event.consume();
		});
	}

	public Tab getTab() {
		return myTab;
	}

	public AuthoringController getController() {
		return controller;
	}

	// public LevelConstructor getLevelConstructor () {
	// return constructor;
	// }

}
