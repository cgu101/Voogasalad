package view.actor;

import java.util.ResourceBundle;

import authoring.controller.AuthoringController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import util.Sprite;

/**
 * @author David
 * 
 *         This class is designed to be used with the ActorBrowser
 * 
 */
public class ActorCell extends AbstractListCell {

	private AuthoringController controller;
	private boolean deselect;
	private String actor;
	private Sprite image;

	public ActorCell(AuthoringController controller) {
		this.controller = controller;
	}

	private Sprite makeImage(String item) {
		//image = new Image(getClass().getClassLoader()
		//		.getResourceAsStream(controller.getAuthoringActorConstructor().getDefaultPropertyValue(item, "image")));
		String imageString = controller.getAuthoringActorConstructor().getDefaultPropertyValue(item, "image");
		String[] dimensions = ResourceBundle.getBundle("resources/SpriteManager").getString(imageString).split(",");
		Sprite output = new Sprite(imageString, Integer.parseInt(dimensions[0]), 
									Integer.parseInt(dimensions[1]));
		image = output;
		//image.play(); //debugging
		this.actor = item;
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

	public void drag(MouseEvent e) {
		this.deselect = false;
		Dragboard db = this.startDragAndDrop(TransferMode.ANY);
		ClipboardContent content = new ClipboardContent();
		content.putString(this.actor);
		db.setContent(content);
		// is there any way to use an ImageView rather than an image?
		db.setDragView(image.getImage(), image.getImage().getWidth() / 2, image.getImage().getHeight() / 2);
	}

	@Override
	protected void makeCell(String item) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(makeImage(item));
		Label label = new Label(item);
		label.setFont(textFont);
		box.getChildren().add(label);
		setGraphic(box);
	}

	public void dragDone(DragEvent e) {
		e.consume();
	}
}
