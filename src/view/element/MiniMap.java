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
	private double currentScale;
	private double currentOpacity;
	private Rectangle currentRectangle;
	
	public MiniMap(ImageView background, ScrollPane mapArea) {
		theMiniMap = new StackPane();
		theBackground = background;
		theMapArea = mapArea;
		currentScale = 1.0;
		currentOpacity = 0.5;
		createMiniMap();
	}
	
	public void updateMiniMapRectangle(double scale) {
		double rectangleScale = 1 / scale;
		currentScale = rectangleScale;
		Rectangle newRect = new Rectangle((miniMapWidth * rectangleScale), (miniMapHeight * rectangleScale));
		newRect.setFill(Color.TRANSPARENT);
		newRect.setStroke(Color.rgb(255, 0, 0, 0.5));
		newRect.setStrokeWidth(5);
		StackPane.setAlignment(newRect, Pos.TOP_LEFT);
		currentRectangle = newRect;
		
		theMiniMap.getChildren().remove(1);
		theMiniMap.getChildren().add(newRect);
	}
	
	public void updateMiniMapOpacity(double opacity) {
		miniMapImageView.setOpacity(opacity);
		currentRectangle.setStroke(Color.rgb(255, 0, 0, opacity));;
	}
	
	private void createMiniMap() {
		miniMapImageView = new ImageView(theBackground.getImage());
		miniMapImageView.setOpacity(0.5);
		miniMapImageView.setFitWidth(200);
		miniMapImageView.setPreserveRatio(true);
		StackPane.setAlignment(miniMapImageView, Pos.BOTTOM_RIGHT);
		theMiniMap.getChildren().add(miniMapImageView);
		miniMapWidth = (double) miniMapImageView.getBoundsInParent().getWidth();
		miniMapHeight = (double) miniMapImageView.getBoundsInParent().getHeight();
		
		Rectangle rect = new Rectangle(miniMapWidth, miniMapHeight);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.rgb(255, 0, 0, 0.5));
		rect.setStrokeWidth(5);
		StackPane.setAlignment(rect, Pos.TOP_LEFT);
		currentRectangle = rect;
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
