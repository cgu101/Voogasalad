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
	
	public void deleteActor() {
		//Unimplemented - Need a method to remove actors from the map
	}
}
