package view.actor;

import java.io.File;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
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

	public ActorCell() {
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
			box.getChildren().add(new Label(item.getName().getValue()));
			setGraphic(box);
		}
	}

	private ImageView makeImage(ActorFactory item) {
		ImageView output = new ImageView(item.getImage());
		output.setFitHeight(25);
		output.setPreserveRatio(true);
		output.setSmooth(true);
		output.setCache(true);
		return output;
	}
}
