package view.actor;

import authoring.controller.AuthoringController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PropertyCell extends ListCell<String> {
	private AuthoringController controller;

	public PropertyCell(AuthoringController controller) {
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
			box.getChildren().add(makeTextField(item, null));
			box.getChildren().add(new Label(item));
			setGraphic(box);
		}
	}

	private TextField makeTextField(String item, EventHandler e) {
		TextField field = new TextField(item);
		// name.setOnAction(e -> {
		// item.getName().setValue(name.getText());
		// refresh();
		// });
		return field;
	}

}
