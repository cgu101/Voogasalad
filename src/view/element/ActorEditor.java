package view.element;

import java.io.File;
import java.util.List;

import authoring.controller.AuthoringController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import view.actor.ActorCell;
import view.actor.PropertyCell;
import view.screen.AbstractScreenInterface;

public class ActorEditor extends AbstractDockElement {

	private ActorBrowser browser;
	private AuthoringController controller;

	public ActorEditor(GridPane pane, GridPane home, String title, AbstractScreenInterface screen, ActorBrowser browser,
			AuthoringController controller) {
		super(pane, home, title, screen);
		findResources();
		this.controller = controller;
		this.browser = browser;
		for (ListView<String> list : browser.getLists()) {
			list.getSelectionModel().selectedItemProperty().addListener(e -> load());
		}
		makePane();
	}

	@Override
	protected void makePane() {
		addLabelPane();
		pane.prefWidthProperty().bind(browser.getPane().widthProperty());
		pane.setMaxHeight(Double.parseDouble(myResources.getString("height")));
		load();
		showing.setValue(false);
		pane.setAlignment(Pos.CENTER);
	}

	private void addLabelPane() {
		GridPane labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		GridPane.setColumnSpan(labelPane, 2);
	}

	private void showSelector(String item) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload Image:");
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		if (selectedFile != null) {
			String file = selectedFile.toURI().toString();
			String imageName = file.substring(file.lastIndexOf('/'));
			// add copy method
			refresh();
		}

	}

	public void refresh() {
		if (browser.getLists() != null) {
			for (ListView<String> list : browser.getLists()) {
				list.refresh();
			}
		}
		load();
	}

	private void load() {
		pane.getChildren().clear();
		addLabelPane();
		if (!showing.getValue()) {
			showing.setValue(true);
		}
		String leftItem = browser.getLists().get(0).getSelectionModel().getSelectedItem();
		String rightItem = browser.getLists().get(1).getSelectionModel().getSelectedItem();
		if (leftItem == null && rightItem == null) {
			Text none = new Text(myResources.getString("none"));
			none.setFont(textFont);
			pane.add(none, 0, 1);
		} else if (leftItem != null && rightItem != null) {
			// external trigger

		} else if (leftItem != null) {
			// left list selected
			editActor(leftItem);
		} else {
			// right list selected
		}
	}

	private void editActor(String item) {
		pane.add(makeImage(item), 0, 1);
		pane.add(makeName(item), 1, 1);
		pane.add(makePropertyEditor(item), 1, 2);
	}

	private ListView<String> makePropertyEditor(String item) {
		ListView<String> list = new ListView<String>();
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new PropertyCell(controller);
			}
		});
		return list;
	}

	private ImageView makeImage(String item) {
		ImageView output = new ImageView(new Image(getClass().getClassLoader()
				.getResourceAsStream(controller.getAuthoringConfigManager().getDefaultPropertyValue(item, "image"))));
		output.setFitHeight(100);
		output.setPreserveRatio(true);
		output.setSmooth(true);
		output.setCache(true);
		output.setOnMouseClicked(e -> showSelector(item));
		GridPane.setRowSpan(output, 2);
		return output;
	}

	private TextField makeName(String item) {
		TextField name = new TextField(item);
		// name.setOnAction(e -> {
		// item.getName().setValue(name.getText());
		// refresh();
		// });
		return name;
	}
}
