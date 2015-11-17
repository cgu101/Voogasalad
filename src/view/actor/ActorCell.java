package view.actor;

import authoring.controller.AuthoringController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ActorCell extends ListCell<String> {

	private AuthoringController controller;

	public ActorCell(AuthoringController controller) {
		this.controller = controller;
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		HBox box = new HBox(5);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else if (item != null) {
			box.setAlignment(Pos.CENTER_LEFT);
			box.getChildren().add(makeImage(item));
			box.getChildren().add(new Label(item));
			setGraphic(box);
		}
	}

	private ImageView makeImage(String item) {
		ImageView output = new ImageView(new Image(getClass().getClassLoader()
				.getResourceAsStream(controller.getAuthoringConfigManager().getDefaultPropertyValue(item, "image"))));
		output.setFitHeight(25);
		output.setPreserveRatio(true);
		output.setSmooth(true);
		output.setCache(true);
		return output;
	}
}
