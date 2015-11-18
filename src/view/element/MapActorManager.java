package view.element;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MapActorManager {
	private StackPane mapLayout;
	
	public MapActorManager(StackPane layout) {
		mapLayout = layout;
	}
	
	public void addActor(Node actor, double x, double y) {
		StackPane.setAlignment(actor, Pos.TOP_LEFT);
		actor.setTranslateX(x);
		actor.setTranslateY(y);
		mapLayout.getChildren().add(actor);
	}
}
