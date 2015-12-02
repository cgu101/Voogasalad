package view.element;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MiniMap {
	private StackPane theMiniMap;
	private ImageView theBackground;
	private ImageView miniMapImageView;
	private ScrollPane theMapArea;
	private double miniMapWidth;
	private double miniMapHeight;
	
	public MiniMap(ImageView background, ScrollPane mapArea) {
		theMiniMap = new StackPane();
		theBackground = background;
		theMapArea = mapArea;
		createMiniMap();
	}
	
	public void updateMiniMapRectangle(double scale) {
		double rectangleScale = 1 / scale;
		Rectangle newRect = new Rectangle((miniMapWidth * rectangleScale), (miniMapHeight * rectangleScale));
		newRect.setFill(Color.TRANSPARENT);
		newRect.setStroke(Color.RED);
		newRect.setStrokeWidth(5);
		StackPane.setAlignment(newRect, Pos.TOP_LEFT);
		
		theMiniMap.getChildren().remove(1);
		theMiniMap.getChildren().add(newRect);
	}
	
	private void createMiniMap() {
		miniMapImageView = new ImageView(theBackground.getImage());
		miniMapImageView.setFitWidth(200);
		miniMapImageView.setPreserveRatio(true);
		StackPane.setAlignment(miniMapImageView, Pos.TOP_LEFT);
		theMiniMap.getChildren().add(miniMapImageView);
		miniMapWidth = (double) miniMapImageView.getBoundsInParent().getWidth();
		miniMapHeight = (double) miniMapImageView.getBoundsInParent().getHeight();
		
		Rectangle rect = new Rectangle(miniMapWidth, miniMapHeight);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.RED);
		rect.setStrokeWidth(5);
		StackPane.setAlignment(rect, Pos.TOP_LEFT);
		theMiniMap.getChildren().add(rect);
		createPanListeners();
	}
	
	private void createPanListeners() {
		theMapArea.vvalueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				System.out.println("Vvalue is: " + new_val.doubleValue());
			}
			
		});
		
		theMapArea.hvalueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				System.out.println("Hvalue is: " + new_val.doubleValue());
			}
			
		});
	}
	
	public StackPane getMiniMap() {
		return theMiniMap;
	}
}
