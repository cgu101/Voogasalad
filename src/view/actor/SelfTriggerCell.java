package view.actor;

import java.util.List;

import org.controlsfx.control.CheckComboBox;

import authoring.controller.AuthoringController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class SelfTriggerCell extends AbstractListCell {
	private AuthoringController controller;
	private ListView<String> list;
	private String actor;

	public SelfTriggerCell(AuthoringController controller, String actor, ListView<String> list) {
		this.controller = controller;
		this.actor = actor;
		this.list = list;
	}

	private Text makeNameField(String item) {
		String triggerName = item.substring(item.lastIndexOf('.') + 1);
		Text text = new Text(triggerName + " -> ");
		text.setFont(textFont);
		return text;
	}

	@Override
	protected void makeCell(String item) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(makeNameField(item));
		box.getChildren().add(makeSelector(item));
		setGraphic(box);
	}

	protected CheckComboBox<String> makeSelector(String item) {
		final ObservableList<String> actions = FXCollections.observableArrayList();
		actions.addAll(controller.getAuthoringActorConstructor().getActionList(actor, null));
		CheckComboBox<String> selector = new CheckComboBox<String>(actions);
		return selector;
	}

}
