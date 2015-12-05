package view.interactions;

import java.util.ResourceBundle;

import authoring.controller.AuthoringController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class TriggerParametersView extends ParametersView {
	private static final String TRIGGER_TITLE = "Trigger";
	private ResourceBundle triggerNames = ResourceBundle.getBundle("authoring/files/triggers");
	
	public TriggerParametersView(InteractionEditor cell, AuthoringController controller) {
		super(cell, controller);
		title = TRIGGER_TITLE;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ComboBox<String> makeComboBox() {
		ComboBox<String> options = new ComboBox<String>();
		// TODO: filter
		triggerNames.keySet().stream().filter(e -> {return false;})
									  .forEach(e -> {options.getItems().add(e);});
		return options;
	}

	@Override
	protected ListView<ParameterCell> makeParamList() {
		// TODO Auto-generated method stub
		return null;
	}

}
