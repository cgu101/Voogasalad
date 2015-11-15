package view.actor;

import javafx.scene.image.Image;

public class ActorFactory {
	private Image image;
	private String name;

	public ActorView getActor() {
		return new ActorView(image);
	}

}
