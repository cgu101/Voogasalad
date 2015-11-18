package view.actor;

import java.util.List;

import org.controlsfx.control.CheckComboBox;

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

public class SelfTriggerCell extends AbstractListCell {
	private AuthoringController controller;
	private ListView<String> list;
	private String actor;

	public SelfTriggerCell(AuthoringController controller, String actor, ListView<String> list) {
		this.controller = controller;
		this.actor = actor;
		this.list = list;
	}

	private HBox makeNameField(String item) {
		String triggerName = item.substring(item.lastIndexOf('.') + 1);
		Text text = new Text(triggerName + " -> ");
		text.setFont(textFont);
		HBox box = new HBox();
		box.getChildren().add(text);
		return box;
	}

	@Override
	protected void makeCell(String item) {
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(makeNameField(item));
		List<String> events = controller.getAuthoringActorConstructor().getActionList(actor, null);
		setGraphic(box);
	}

	protected CheckComboBox<String> makeSelector() {
		CheckComboBox<String> selector = new CheckComboBox<String>();
		return selector;
	}

}
