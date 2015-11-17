package view.actor;

import authoring.controller.AuthoringController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PropertyCell extends ListCell<String> {
	private AuthoringController controller;
	private ListView<String> list;
	private String actor;

	public PropertyCell(AuthoringController controller, String actor, ListView<String> list) {
		this.controller = controller;
		this.actor = actor;
		this.list = list;
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
			box.getChildren().add(new Text("="));
			box.getChildren()
					.add(makeTextField(controller.getAuthoringConfigManager().getDefaultPropertyValue(actor, item),
							e -> editProperty(item)));
			setGraphic(box);
		}
	}

	private void editProperty(String item) {
		// TO DO: edit actors;
		list.refresh();
	}

	private TextField makeTextField(String item, EventHandler<ActionEvent> e) {
		TextField field = new TextField(item);
		field.setOnAction(e);
		return field;
	}

}
