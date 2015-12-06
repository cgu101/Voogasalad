package view.map;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.visual.AbstractVisual;

public class MiniMap extends AbstractVisual {
	private StackPane theMiniMap;
	private ImageView theBackground;
	private ImageView miniMapImageView;

	private double miniMapWidth;
	private double miniMapHeight;

	private double theMapWidth;
	private double theMapHeight;

	private double currentScale;
	private double currentOpacity;
	private Rectangle currentRectangle;
	private double currentRectangleXPos;
	private double currentRectangleYPos;

	public MiniMap(ImageView background, ScrollPane mapArea, double mapWidth, double mapHeight) {
		findResources();
		theMiniMap = new StackPane();
		theBackground = background;
		currentScale = Double.valueOf(myResources.getString("startingscale"));
		currentOpacity = Double.valueOf(myResources.getString("startingopacity"));
		currentRectangleXPos = Double.valueOf(myResources.getString("startingrectx"));
		currentRectangleYPos = Double.valueOf(myResources.getString("startingrecty"));

		theMapWidth = mapWidth;
		theMapHeight = mapHeight;
		createMiniMap();
	}

	public void updateMiniMapRectangleOnZoom(double scale) {
		double rectangleScale = 1 / scale;
		currentScale = rectangleScale;
		createMiniMapRectangle(miniMapWidth, miniMapHeight, currentScale, currentOpacity);
		addMiniMapRectangle(currentRectangleXPos, currentRectangleYPos);
	}

	public void updateMiniMapOpacity(double opacity) {
		currentOpacity = opacity;
		miniMapImageView.setOpacity(opacity);
		currentRectangle.setStroke(Color.rgb(Integer.valueOf(myResources.getString("rectrvalue")),
				Integer.valueOf(myResources.getString("rectgvalue")),
				Integer.valueOf(myResources.getString("rectbvalue")), opacity));
	}

	public void updateMiniMapBackground(ImageView background) {
		// Not sure if this will properly update it
		miniMapImageView = background;
	}

	private void createMiniMap() {
		createMiniMapImageView(Double.valueOf(myResources.getString("minimapwidth")));
		addMiniMapImageView();
		createMiniMapRectangle(miniMapWidth, miniMapHeight, currentScale, currentOpacity);
		addMiniMapRectangle(currentRectangleXPos, currentRectangleYPos);

		setUpDragFilters();
	}

	public void updateMiniMapSize(double width) {
		miniMapImageView.setFitWidth(width);
		miniMapImageView.setPreserveRatio(true);

		createMiniMapRectangle(miniMapWidth, miniMapHeight, currentScale, currentOpacity);
		addMiniMapRectangle(currentRectangleXPos, currentRectangleYPos);

		miniMapWidth = (double) miniMapImageView.getBoundsInParent().getWidth();
		miniMapHeight = (double) miniMapImageView.getBoundsInParent().getHeight();
	}

	private void createMiniMapImageView(double width) {
		miniMapImageView = new ImageView(theBackground.getImage());
		miniMapImageView.setOpacity(currentOpacity);
		miniMapImageView.setFitWidth(width);
		miniMapImageView.setPreserveRatio(true);
	}

	private void addMiniMapImageView() {
		theMiniMap.getChildren().add(miniMapImageView);
		miniMapWidth = (double) miniMapImageView.getBoundsInParent().getWidth();
		miniMapHeight = (double) miniMapImageView.getBoundsInParent().getHeight();
	}

	private void createMiniMapRectangle(double width, double height, double scale, double opacity) {
		Rectangle rect = new Rectangle(width * scale, height * scale);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.rgb(Integer.valueOf(myResources.getString("rectrvalue")),
				Integer.valueOf(myResources.getString("rectgvalue")),
				Integer.valueOf(myResources.getString("rectbvalue")), opacity));
		rect.setStrokeWidth(Double.valueOf(myResources.getString("rectedgewidth")));
		StackPane.setAlignment(rect, Pos.TOP_LEFT);
		currentRectangle = rect;
	}

	private void addMiniMapRectangle(double xPos, double yPos) {
		if (theMiniMap.getChildren().size() > 1) {
			theMiniMap.getChildren().remove(1);
		}
		currentRectangle.setTranslateX(xPos);
		currentRectangle.setTranslateY(yPos);
		theMiniMap.getChildren().add(currentRectangle);
	}

	private void setUpDragFilters() {
		final DragContext dragContext = new DragContext();

		theMiniMap.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				dragContext.mouseAnchorX = mouseEvent.getSceneX();
				dragContext.mouseAnchorY = mouseEvent.getSceneY();
				dragContext.initialTranslateX = ((StackPane) mouseEvent.getSource()).getTranslateX();
				dragContext.initialTranslateY = ((StackPane) mouseEvent.getSource()).getTranslateY();
			}
		});

		theMiniMap.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				double offsetX = mouseEvent.getSceneX() - dragContext.mouseAnchorX;
				double offsetY = mouseEvent.getSceneY() - dragContext.mouseAnchorY;
				double newX = dragContext.initialTranslateX + offsetX;
				double newY = dragContext.initialTranslateY + offsetY;
				if (!isXOver(newX)) {
					theMiniMap.setTranslateX(newX);
				}
				if (!isYOver(newY)) {
					theMiniMap.setTranslateY(newY);
				}
			}
		});

	}

	private boolean isXOver(double newX) {
		return newX < 0 || newX > theMapWidth - miniMapWidth;
	}

	private boolean isYOver(double newY) {
		return newY < 0 || newY > theMapHeight - miniMapHeight;
	}

	public void updateMiniMapRectangleOnHorizontalPan(double new_value) {
		double maxHorizontalMovement = miniMapWidth * (1 - currentScale);
		double newRectangleXPos = new_value * maxHorizontalMovement;
		currentRectangleXPos = newRectangleXPos;
		addMiniMapRectangle(currentRectangleXPos, currentRectangleYPos);
	}

	public void updateMiniMapRectangleOnVerticalPan(double new_value) {
		double maxVerticalMovement = miniMapHeight * (1 - currentScale);
		double newRectangleYPos = new_value * maxVerticalMovement;
		currentRectangleYPos = newRectangleYPos;
		addMiniMapRectangle(currentRectangleXPos, currentRectangleYPos);
	}

	public StackPane getMiniMap() {
		return theMiniMap;
	}

	private static final class DragContext {
		public double mouseAnchorX;
		public double mouseAnchorY;
		public double initialTranslateX;
		public double initialTranslateY;
	}
	
	public double getRectangleX() {
		return currentRectangleXPos;
	}
	
	public double getRectangleY() {
		return currentRectangleYPos;
	}
}
