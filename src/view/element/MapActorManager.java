package view.element;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MapActorManager {
	private Group mapLayout;
	
	public MapActorManager(Group layout) {
		mapLayout = layout;
	}
	
	public void addActor(Node actor, double x, double y) {
		actor.setTranslateX(x);
		actor.setTranslateY(y);
		mapLayout.getChildren().add(actor);
	}
	
	public void removeActor(Node actor) {
		mapLayout.getChildren().remove(actor);
	}
	
	public void updateBackground(Node background) {
		mapLayout.getChildren().remove(background);
		mapLayout.getChildren().add(0, background);
	}
}
