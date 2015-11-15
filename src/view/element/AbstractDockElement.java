package view.element;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import view.screen.CreatorScreen;

public abstract class AbstractDockElement extends AbstractElement {

	protected Stage stage;
	protected Label title;
	protected CreatorScreen screen;
	protected GridPane home;
	protected BooleanProperty isShown;
	protected CheckMenuItem viewMenu;

	public AbstractDockElement(GridPane pane, GridPane home, String title, CreatorScreen screen) {
		super(pane);
		this.screen = screen;
		this.home = home;
		this.title = new Label(title);
		this.title.setFont(headerFont);
		this.title.setOnMouseDragged(me -> {
			screen.getScene().setCursor(Cursor.CLOSED_HAND);
		});
		this.viewMenu = new CheckMenuItem(this.getClass().getName());
		viewMenu.setSelected(true);
		viewMenu.selectedProperty().addListener(e -> isSelected(viewMenu.isSelected()));
		dock();
	}

	// TODO: display/don't display based on whether isSelected is true or false
	private void isSelected(Boolean isSelected) {
		if (isSelected) {
			System.out.println("Is Selected");
		} else {
			System.out.println("Isn't Selected");
		}
	}

	private void handleDrag(MouseEvent me, boolean docked) {
		screen.getScene().setCursor(Cursor.DEFAULT);
		Point2D mouseLoc = new Point2D(me.getScreenX(), me.getScreenY());
		Window window = screen.getScene().getWindow();
		Rectangle2D windowBounds = new Rectangle2D(window.getX(), window.getY(), window.getWidth(), window.getHeight());
		if (docked && !windowBounds.contains(mouseLoc)) {
			launch(me.getScreenX() - pane.widthProperty().doubleValue() / 2,
					me.getScreenY() - title.heightProperty().doubleValue());
		}
		if (!docked) {
			if (windowBounds.contains(mouseLoc)) {
				dock();
			} else {
				stage.setX(me.getScreenX() - pane.widthProperty().doubleValue() / 2);
				stage.setY(me.getScreenY() - title.heightProperty().doubleValue());
			}
		}
	}

	public void launch(double x, double y) {
		home.getChildren().clear();
		stage = new Stage();
		stage.setScene(new Scene(pane));
		stage.setTitle(title.getText());
		stage.setX(x);
		stage.setY(y);
		stage.show();
		stage.setResizable(false);
		stage.setOnCloseRequest(e -> dock());
		stage.setAlwaysOnTop(true);
		this.title.setOnMouseReleased(me -> handleDrag(me, false));
	}

	public void dock() {
		if (stage != null) {
			stage.close();
		}
		home.add(pane, 0, 0);
		this.title.setOnMouseReleased(me -> handleDrag(me, true));
	}

	public GridPane makeLabelPane() {
		GridPane labelPane = new GridPane();
		labelPane.add(title, 0, 0);
		labelPane.setAlignment(Pos.CENTER);
		return labelPane;
	}
	
	public CheckMenuItem getMenuItem() {
		return viewMenu;
	}	
}
