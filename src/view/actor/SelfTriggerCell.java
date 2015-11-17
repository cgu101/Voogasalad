package view.actor;

import authoring.controller.AuthoringController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SelfTriggerCell extends ListCell<String> {
	private AuthoringController controller;
	private ListView<String> list;
	private String actor;

	public SelfTriggerCell(AuthoringController controller, String actor, ListView<String> list) {
		this.controller = controller;
		this.actor = actor;
		this.list = list;
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		VBox box = new VBox(5);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else if (item != null) {
			box.setAlignment(Pos.CENTER_LEFT);
			box.getChildren().add(makeNameField(item, null));
			setGraphic(box);
		}
	}

	private HBox makeNameField(String item, EventHandler<ActionEvent> e) {
		TextField field = new TextField(item);
		field.setOnAction(e);
		HBox box = new HBox(5);
		box.getChildren().add(field);
		box.getChildren().add(new Text("->"));
		return box;
	}

}
