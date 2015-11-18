package view.element;

import java.io.File;
import java.util.ArrayList;
import authoring.controller.AuthoringController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import view.actor.PropertyCell;
import view.actor.TriggerCell;
import view.screen.AbstractScreenInterface;

public class ActorEditor extends AbstractDockElement {

	private ActorBrowser browser;
	private AuthoringController controller;
	private Workspace workspace;
	private ImageView image;
	private GridPane contentPane;

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
		pane.setMaxHeight(Double.parseDouble(myResources.getString("height")));
		pane.prefWidthProperty().bind(browser.getPane().widthProperty());
		pane.maxWidthProperty().bind(browser.getPane().widthProperty());
		pane.setAlignment(Pos.CENTER);
		GridPane labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		contentPane = new GridPane();
		pane.add(contentPane, 0, 1);
		load(null);
		showing.setValue(false);
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
		contentPane.getChildren().clear();
		if (!showing.getValue()) {
			showing.setValue(true);
		}
		String leftItem = browser.getLists().get(0).getSelectionModel().getSelectedItem();
		String rightItem = browser.getLists().get(1).getSelectionModel().getSelectedItem();
		if (controller == null || (leftItem == null && rightItem == null)) {
			Text none = new Text(myResources.getString("none"));
			none.setFont(textFont);
			contentPane.add(none, 0, 1);
		} else if (leftItem != null && rightItem != null) {
			// external trigger
			editInteraction(leftItem, rightItem);
		} else if (leftItem != null) {
			// left list selected
			editActor(leftItem);
		} else {
			// right list selected
			editActor(rightItem);
		}
	}

	private void editInteraction(String leftItem, String rightItem) {
		contentPane.add(makeImage(leftItem), 0, 1);
		contentPane.add(makeInteractionText(leftItem, rightItem), 1, 1);
		contentPane.add(makeImage(rightItem), 2, 1);
		contentPane.add(makeExternalTriggerEditor(leftItem, rightItem), 0, 2);
	}

	private ListView<String> makeExternalTriggerEditor(String leftItem, String rightItem) {
		ObservableList<String> triggers = FXCollections.observableArrayList(new ArrayList<String>());
		triggers.addAll(controller.getAuthoringActorConstructor().getEventTriggerList(leftItem, rightItem));
		ListView<String> list = new ListView<String>(triggers);
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new TriggerCell(controller, leftItem, list);
			}
		});
		GridPane.setColumnSpan(list, 3);
		list.setFocusTraversable(false);
		return list;
	}

	private VBox makeInteractionText(String left, String right) {
		VBox box = new VBox();
		Text t1 = new Text(left);
		Text t3 = new Text(right);
		t1.setFont(textFont);
		t3.setFont(textFont);
		ImageView t2 = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("bolt.png")));
		t2.setPreserveRatio(true);
		t2.setSmooth(true);
		t2.setCache(true);
		t2.setFitHeight(Double.parseDouble(myResources.getString("boltheight")));
		box.getChildren().addAll(t1, t2, t3);
		box.setAlignment(Pos.CENTER);
		return box;
	}

	private void editActor(String item) {
		contentPane.add(makeImage(item), 0, 1);
		contentPane.add(makeName(item), 1, 1);
		contentPane.add(makePropertyEditor(item), 1, 2);
		contentPane.add(makeSelfTriggerEditor(item), 0, 3);
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
				return new TriggerCell(controller, item, list);
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
		name.prefWidthProperty().bind(pane.widthProperty());
		name.setFont(textFont);
		return name;
	}
}
