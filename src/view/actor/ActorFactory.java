package view.actor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class ActorFactory {
	private Image image;
	private StringProperty name;

	public ActorFactory(int i) {
		this.image = new Image(getClass().getClassLoader().getResourceAsStream("rcd.jpg"));
		this.name = new SimpleStringProperty();
		this.name.setValue("Actor " + i);
	}

	public ActorView getActor() {
		return new ActorView(image);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public StringProperty getName() {
		return name;
	}

}
