package view.actor;

import authoring.controller.AuthoringController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

public class ActorCell extends AbstractListCell {

	private AuthoringController controller;
	private boolean deselect;

	public ActorCell(AuthoringController controller) {
		this.controller = controller;
	}

	private ImageView makeImage(String item) {
		ImageView output = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(
				controller.getAuthoringActorConstructor().getDefaultPropertyValue(item, "image"))));
		output.setFitHeight(25);
		output.setPreserveRatio(true);
		output.setSmooth(true);
		output.setCache(true);
		return output;
	}

	public boolean deselect() {
		boolean output = deselect;
		deselect = false;
		return output;
	}

	public void markForDeselection() {
		this.deselect = true;
	}

	public void drag(MouseEvent me) {
		this.deselect = false;
		Dragboard db = this.startDragAndDrop(TransferMode.COPY);
	}

	@Override
	protected void makeCell(String item) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(makeImage(item));
		box.getChildren().add(new Label(item));
		setGraphic(box);
	}
}
