package view.actor;

import java.util.List;

import org.controlsfx.control.CheckComboBox;

import authoring.controller.AuthoringController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SelfTriggerCell extends AbstractListCell {
	private AuthoringController controller;
	private ListView<String> list;
	private String actor;

	public SelfTriggerCell(AuthoringController controller, String actor, ListView<String> list) {
		findResources();
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
		actions.addAll(controller.getAuthoringActorConstructor().getActionList(actor));
		CheckComboBox<String> selector = new CheckComboBox<String>(actions);
		selector.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) {
				List<String> actions = selector.getCheckModel().getCheckedItems();
				controller.getLevelConstructor().getTreeConstructor().removeSelfTrigger(actor, item);
				controller.getLevelConstructor().getTreeConstructor().addSelfTriggerActions(actor, item, actions);
			}
		});
		selector.setMaxWidth(Double.parseDouble(myResources.getString("checkwidth")));
		return selector;
	}

}
