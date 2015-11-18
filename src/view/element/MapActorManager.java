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
		//StackPanes have (0, 0) set at the center, need to realign nodes
		StackPane.setAlignment(actor, Pos.TOP_LEFT);
		
		actor.setTranslateX(x);
		actor.setTranslateY(y);
		mapLayout.getChildren().add(actor);
	}
	
	public void deleteActor() {
		//Unimplemented - Need a method to remove actors from the map
	}
}
