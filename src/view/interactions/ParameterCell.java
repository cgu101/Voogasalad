package view.interactions;

import authoring.controller.AuthoringController;
import authoring.controller.parameters.ParameterData;
import authoring.model.properties.Property;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import view.actor.AbstractListCell;

public class ParameterCell extends AbstractListCell<ParameterData> {
	private AuthoringController controller;
	private String[] actors;
	private HBox box;

	public ParameterCell(AuthoringController controller, String[] actors) {
		this.controller = controller;
		this.actors = actors;
		findResources();
	}

	private TextField makeTextField(String item) {
		if (item == null)
			item = "";
		TextField field = new TextField(item);
		field.setMaxWidth(Double.parseDouble(myResources.getString("width")));
		field.setFont(textFont);
		field.setOnAction(e -> {
			getItem().setValue(field.getText());
		});
		return field;
	}

	private ComboBox<String> makeComboBox(String item) {
		ComboBox<String> comboBox = new ComboBox<String>();
		String actor = actors[Integer.parseInt(getItem().getActorIndex())];
		comboBox.getItems().addAll(controller.getAuthoringActorConstructor().getPropertyList(actor));
		if (item != null && !item.isEmpty()) {
			comboBox.setValue(item);
		} else {
			String val = comboBox.getItems().get(0);
			comboBox.setValue(val);
			getItem().setValue(val);
		}
		comboBox.setOnAction(e -> {
			getItem().setValue(comboBox.getValue());
		});
		return comboBox;
	}

	@Override
	protected void makeCell(ParameterData data) {
		box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(new Text(data.getText()));
		if (data.getType().equals(Property.class.getName())) {
			box.getChildren().add(makeComboBox(data.getValue()));
		} else if (data.getType().equals(KeyCode.class.getName())) {
			box.getChildren().add(makeKeySelectorButton());
		} else {
			box.getChildren().add(makeTextField(data.getValue()));
		}
		setGraphic(box);
	}

	private Button makeKeySelectorButton() {
		Button button = new Button();
		button.setText(myResources.getString("select"));
		if (!(getItem().getValue().equals(""))) {
			button.setText(getItem().getValue());
		}
		button.setOnAction(e -> {
			KeyCode key = KeyCode.getKeyCode(getItem().getValue());
			try {
				System.out.println(actors[0] + " " + key);
				controller.getKeyLibrary().returnKey(actors[0], key);
			} catch (IllegalArgumentException err) {
				System.out.println("whoops");
			}
			key = controller.getKeyLibrary().checkoutKey(actors[0]);
			if (key == null) {
				key = KeyCode.DEAD_GRAVE;
			}
			button.setText(key.getName());
			getItem().setValue(key.getName());
		});
		return button;
	}

}
