package view.actor;

import authoring.controller.AuthoringController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
/**
 * @author David
 * 
 * This class is designed to be used with the ActorEditor
 * This allows for modification of properties
 * 
 */
public class PropertyCell extends AbstractListCell {
	private AuthoringController controller;
	private ListView<String> list;
	private String actor;

	public PropertyCell(AuthoringController controller, String actor, ListView<String> list) {
		this.controller = controller;
		this.actor = actor;
		this.list = list;
		findResources();
	}

	private void editProperty(String item) {
		// TO DO: edit actors;
		list.refresh();
	}

	private TextField makeTextField(String item, EventHandler<ActionEvent> e) {
		TextField field = new TextField(item);
		field.setOnAction(e);
		field.setMaxWidth(Double.parseDouble(myResources.getString("width")));
		field.setFont(textFont);
		return field;
	}

	@Override
	protected void makeCell(String item) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(makeTextField(item, null));
		box.getChildren().add(new Text("="));
		box.getChildren()
				.add(makeTextField(controller.getAuthoringActorConstructor().getDefaultPropertyValue(actor, item),
						e -> editProperty(item)));
		setGraphic(box);
	}

}
