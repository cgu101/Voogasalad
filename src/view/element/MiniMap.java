package view.element;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MiniMap {
	private StackPane theMiniMap;
	private ImageView theBackground;
	private ImageView miniMapImageView;
	private ScrollPane theMapArea;
	
	public MiniMap(ImageView background, ScrollPane mapArea) {
		theMiniMap = new StackPane();
		theBackground = background;
		theMapArea = mapArea;
		createMiniMap();
	}
	
	public void updateMiniMap(double scale) {
		
	}
	
	private void createMiniMap() {
		miniMapImageView = new ImageView(theBackground.getImage());
		miniMapImageView.setFitWidth(200);
		miniMapImageView.setPreserveRatio(true);
		StackPane.setAlignment(miniMapImageView, Pos.BOTTOM_RIGHT);
		theMiniMap.getChildren().add(miniMapImageView);
	}
	
	public StackPane getMiniMap() {
		return theMiniMap;
	}
}
