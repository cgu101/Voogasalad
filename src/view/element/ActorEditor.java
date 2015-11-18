package view.element;

import java.io.File;
import java.util.ArrayList;
import authoring.controller.AuthoringController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
import view.actor.PropertyCell;
import view.actor.SelfTriggerCell;
import view.screen.AbstractScreenInterface;

public class ActorEditor extends AbstractDockElement {

	private ActorBrowser browser;
	private AuthoringController controller;
	private Workspace workspace;
	private ImageView image;

	public ActorEditor(GridPane pane, GridPane home, String title, AbstractScreenInterface screen, ActorBrowser browser,
			Workspace workspace) {
		super(pane, home, title, screen);
		findResources();
		this.controller = null;
		this.browser = browser;
		this.workspace = workspace;
		for (ListView<String> list : browser.getLists()) {
			list.getSelectionModel().selectedItemProperty()
					.addListener(e -> load(workspace.getCurrentLevel().getController()));
		}
		makePane();
	}

	@Override
	protected void makePane() {
		addLabelPane();
		pane.prefWidthProperty().bind(browser.getPane().widthProperty());
		pane.setMaxHeight(Double.parseDouble(myResources.getString("height")));
		load(null);
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
		load(workspace.getCurrentLevel().getController());
	}

	private void load(AuthoringController controller) {
		this.controller = controller;
		pane.getChildren().clear();
		addLabelPane();
		if (!showing.getValue()) {
			showing.setValue(true);
		}
		String leftItem = browser.getLists().get(0).getSelectionModel().getSelectedItem();
		String rightItem = browser.getLists().get(1).getSelectionModel().getSelectedItem();
		if (controller == null || (leftItem == null && rightItem == null)) {
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
			editActor(rightItem);
		}
	}

	private void editActor(String item) {
		pane.add(makeImage(item), 0, 1);
		pane.add(makeName(item), 1, 1);
		pane.add(makePropertyEditor(item), 1, 2);
		pane.add(makeSelfTriggerEditor(item), 0, 3);
	}

	private ListView<String> makePropertyEditor(String item) {
		ObservableList<String> properties = FXCollections.observableArrayList(new ArrayList<String>());
		properties.addAll(controller.getAuthoringActorConstructor().getPropertyList(item));
		ListView<String> list = new ListView<String>(properties);
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new PropertyCell(controller, item, list);
			}
		});
		list.setFocusTraversable(false);
		return list;
	}

	private ListView<String> makeSelfTriggerEditor(String item) {
		ObservableList<String> triggers = FXCollections.observableArrayList(new ArrayList<String>());
		triggers.addAll(controller.getAuthoringActorConstructor().getSelfTriggerList(item));
		ListView<String> list = new ListView<String>(triggers);
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new SelfTriggerCell(controller, item, list);
			}
		});
		GridPane.setColumnSpan(list, 2);
		list.setFocusTraversable(false);
		return list;
	}

	private ImageView makeImage(String item) {
		image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(
				controller.getAuthoringActorConstructor().getDefaultPropertyValue(item, "image"))));
		image.setFitHeight(Double.parseDouble(myResources.getString("imagesize")));
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);
		image.setOnMouseClicked(e -> showSelector(item));
		GridPane.setRowSpan(image, 2);
		return image;
	}

	private TextField makeName(String item) {
		TextField name = new TextField(item);
		// name.setOnAction(e -> {
		// item.getName().setValue(name.getText());
		// refresh();
		// });
		name.prefWidthProperty().bind(pane.widthProperty());
		name.setFont(textFont);
		return name;
	}
}
