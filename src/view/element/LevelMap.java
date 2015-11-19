package view.element;

import java.util.Date;
import java.util.ResourceBundle;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import util.Sprite;
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

//				map.addProperty("xLocation", "" + (event.getSceneX() - this.getPane().getLayoutX()));
//				map.addProperty("yLocation", "" + (event.getSceneY() - this.getPane().getLayoutY()));
				
				map.addProperty("xLocation", "" + (event.getX()));
				map.addProperty("yLocation", "" + (event.getY())); //TODO Change this
				
//				map.addProperty("xLocation", "" + (event.getSceneX()));
//				map.addProperty("yLocation", "" + (event.getSceneY() - 50)); //TODO Change this
				String uniqueID = new Date().toString();
				controller.getLevelConstructor().getActorGroupsConstructor().updateActor(uniqueID, map);

				Actor a = controller.getLevelConstructor().getActorGroupsConstructor().getActor(actor, uniqueID);

				String img = (String) a.getProperties().getComponents().get("image").getValue();
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(img));
				Sprite newSp = new Sprite(image);

				addActor(newSp, (double)a.getProperties().getComponents().get("xLocation").getValue(),
						(double)a.getProperties().getComponents().get("xLocation").getValue());

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
