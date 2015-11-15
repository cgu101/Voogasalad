package view.actor;

import java.io.File;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ActorCell extends ListCell<ActorFactory> {

	private List<ListView<ActorFactory>> lists;

	public ActorCell(List<ListView<ActorFactory>> lists) {
		this.lists = lists;
	}

	@Override
	public void updateItem(ActorFactory item, boolean empty) {
		super.updateItem(item, empty);
		HBox box = new HBox(5);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else if (item != null) {
			box.setAlignment(Pos.CENTER_LEFT);
			box.getChildren().add(makeImage(item));
			box.getChildren().add(makeName(item));
			setGraphic(box);
		}
	}

	private TextField makeName(ActorFactory item) {
		TextField name = new TextField(item.getName().getValue());
		item.getName().addListener(e -> {
			name.setText(item.getName().getValue());
		});
		name.setOnAction(e -> {
			item.getName().setValue(name.getText());
		});
		return name;
	}

	private ImageView makeImage(ActorFactory item) {
		ImageView output = new ImageView(item.getImage());
		output.setFitHeight(25);
		output.setPreserveRatio(true);
		output.setSmooth(true);
		output.setCache(true);
		output.setOnMouseClicked(e -> showSelector(item));
		return output;
	}

	private void showSelector(ActorFactory item) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload Image:");
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		if (selectedFile != null) {
			item.setImage(new Image(selectedFile.toURI().toString()));
			if (lists != null) {
				for (ListView<ActorFactory> list : lists) {
					list.refresh();
				}
			}
		}
		System.out.println(item.getName().getValue());
	}
}
