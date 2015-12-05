package view.interactions;

import authoring.controller.AuthoringController;
import authoring.controller.parameters.ParameterData;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class TriggerParametersView extends ParametersView {
	private static final String TRIGGER_TITLE = "Trigger";
//	private ResourceBundle triggerNames = ResourceBundle.getBundle("authoring/files/triggers");
	
	public TriggerParametersView(InteractionData cell, GridPane pane, AuthoringController controller) {
		super(cell, pane, controller);
		this.title = TRIGGER_TITLE;
		init();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ComboBox<String> makeComboBox() {
		ComboBox<String> options = new ComboBox<String>();
		// TODO: filter
//		triggerNames.keySet().stream().filter(e -> {return false;})
//									  .forEach(e -> {options.getItems().add(e);});
		return options;
	}

	@Override
	protected ListView<ParameterData> makeParamList() {
		// TODO Auto-generated method stub
		paramList = FXCollections.observableArrayList(new ParameterData("text", String.class.getName(), "0", ""));
		ListView<ParameterData> list = new ListView<ParameterData>(paramList);
		list.setCellFactory(new Callback<ListView<ParameterData>, ListCell<ParameterData>>() {
			@Override
			public ListCell<ParameterData> call(ListView<ParameterData> list) {
				return new ParameterCell(controller, cell.getActors());
			}
		});
		list.setFocusTraversable(false);
		return list;
	}

}
