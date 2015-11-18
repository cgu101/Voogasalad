package view.actor;

import authoring.model.actors.Actor;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import player.PlayerController;

public class ActorMonitorCell extends AbstractListCell {

	private PlayerController controller;
	
	public ActorMonitorCell(PlayerController controller, Actor actor) {
		this.controller = controller;
	}

	//TODO: Make a non-default image
	private ImageView makeImage() {
		ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("rcd.jpg")));
		image.setFitHeight(Double.parseDouble(myResources.getString("imagesize")));
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);
		GridPane.setRowSpan(image, 2);
		return image;
	}
	
	@Override
	protected void makeCell(String item) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(makeImage());
		//Add the actual properties
		setGraphic(box);
	//	"groupID"
	}

}
