package view.element;

import java.io.File;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import view.actor.ActorFactory;
import view.screen.AbstractScreenInterface;

public class ActorEditor extends AbstractDockElement {

	private ActorBrowser browser;

	public ActorEditor(GridPane pane, GridPane home, String title, AbstractScreenInterface screen,
			ActorBrowser browser) {
		super(pane, home, title, screen);
		findResources();
		this.browser = browser;
		for (ListView<ActorFactory> list : browser.getLists()) {
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

	private void showSelector(ActorFactory item) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload Image:");
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		if (selectedFile != null) {
			item.setImage(new Image(selectedFile.toURI().toString()));
			refresh();
		}
		System.out.println(item.getName().getValue());
	}

	public void refresh() {
		if (browser.getLists() != null) {
			for (ListView<ActorFactory> list : browser.getLists()) {
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
		ActorFactory leftItem = browser.getLists().get(0).getSelectionModel().getSelectedItem();
		ActorFactory rightItem = browser.getLists().get(1).getSelectionModel().getSelectedItem();
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

	private void editActor(ActorFactory item) {
		pane.add(makeImage(item), 0, 1);
		pane.add(makeName(item), 1, 1);
	}

	private ImageView makeImage(ActorFactory item) {
		ImageView output = new ImageView(item.getImage());
		output.setFitHeight(100);
		output.setPreserveRatio(true);
		output.setSmooth(true);
		output.setCache(true);
		output.setOnMouseClicked(e -> showSelector(item));
		return output;
	}

	private TextField makeName(ActorFactory item) {
		TextField name = new TextField(item.getName().getValue());
		item.getName().addListener(e -> {
			name.setText(item.getName().getValue());
		});
		name.setOnAction(e -> {
			item.getName().setValue(name.getText());
			refresh();
		});
		return name;
	}
}
