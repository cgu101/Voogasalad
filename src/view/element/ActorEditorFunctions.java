package view.element;

import java.util.ArrayList;

import authoring.controller.AuthoringController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import view.actor.TriggerCell;

public class ActorEditorFunctions {
	private AuthoringController controller;
	
	ActorEditorFunctions(AuthoringController ac) {
		controller = ac;
	}
	
	public ListView<String> makeExternalTriggerEditor(String leftItem, String rightItem) {
		ObservableList<String> triggers = FXCollections.observableArrayList(new ArrayList<String>());
		triggers.addAll(controller.getAuthoringActorConstructor().getEventTriggerList(leftItem, rightItem));
		ListView<String> list = new ListView<String>(triggers);
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new TriggerCell(controller, leftItem, rightItem);
			}
		});
		GridPane.setColumnSpan(list, 3);
		list.setFocusTraversable(false);
		return list;
	}

}
