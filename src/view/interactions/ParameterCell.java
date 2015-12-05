package view.interactions;

import authoring.controller.AuthoringController;
import authoring.controller.parameters.ParameterData;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import view.actor.AbstractListCell;

public class ParameterCell extends AbstractListCell {
	private ParameterData data;
	private AuthoringController controller;
	private String actor;

	public ParameterCell (ParameterData data, AuthoringController controller, String actor) {
		this.data = data;
		this.controller = controller;
		this.actor = actor;
		findResources();
	}

	private TextField makeTextField(String item) {
		TextField field = new TextField(item);
		field.setMaxWidth(Double.parseDouble(myResources.getString("width")));
		field.setFont(textFont);
		return field;
	}
	private ComboBox<String> makeComboBox () {
		ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems()
			.addAll(controller.getAuthoringActorConstructor().getPropertyList(actor));
		return comboBox;
	}

	@Override
	protected void makeCell(String item) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(new Text(data.getText()));
		if (data.getType().equals(Double.class.getName())) {
			box.getChildren()
					.add(makeTextField(""));
		} else {
			box.getChildren()
					.add(makeComboBox());
		}
		setGraphic(box);
	}
	public String getType () {
		return data.getType();
	}

}


