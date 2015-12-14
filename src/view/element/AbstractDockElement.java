package view.element;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import view.screen.AbstractScreenInterface;

/**
 * @author David
 * 
 *         This class allows for flexible elements that can be docked in a
 *         Screen, launched in a separate window, and closed/hidden.
 * 
 */
public abstract class AbstractDockElement extends AbstractElement {

	private Stage stage;
	private Label title;
	protected AbstractScreenInterface screen;
	protected GridPane home;
	protected Pane titlePane;
	protected BooleanProperty showing;
	private BooleanProperty docked;

	/**
	 * 
	 * @param home
	 *            the Pane that the element will be contained within when it is
	 *            in the docked state.
	 * @param title
	 *            the name of the element; this will be displayed when the
	 *            element is opened in a new window.
	 * @param screen
	 *            the home screen that this element is bound to and can dock in.
	 */

	public AbstractDockElement(GridPane home, String title, AbstractScreenInterface screen) {
		super(new GridPane());
		this.screen = screen;
		this.home = home;
		this.title = new Label(title);
		this.title.setFont(headerFont);
		this.title.setOnMouseReleased(me -> reposition(me));
		configureCursors();
		showing = new SimpleBooleanProperty(false);
		docked = new SimpleBooleanProperty(false);
		showing.addListener(e -> toggleShowing(showing.getValue()));
		GridPane tp = new GridPane();
		tp.add(this.title, 0, 0);
		tp.setAlignment(Pos.CENTER);
		titlePane = tp;
	}

	private void configureCursors() {
		this.title.setOnMouseEntered(me -> {
			screen.getScene().setCursor(Cursor.OPEN_HAND);
		});
		this.title.setOnMousePressed(me -> {
			screen.getScene().setCursor(Cursor.CLOSED_HAND);
		});
		this.title.setOnMouseDragged(me -> {
			screen.getScene().setCursor(Cursor.CLOSED_HAND);
		});
		this.title.setOnMouseExited(me -> {
			screen.getScene().setCursor(Cursor.DEFAULT);
		});
	}

	private void toggleShowing(boolean input) {
		if (input) {
			dock();
		} else {
			hide();
		}
	}

	private void reposition(MouseEvent me) {
		screen.getScene().setCursor(Cursor.DEFAULT);
		Point2D mouseLoc = new Point2D(me.getScreenX(), me.getScreenY());
		Window window = screen.getScene().getWindow();
		Rectangle2D windowBounds = new Rectangle2D(window.getX(), window.getY(), window.getWidth(), window.getHeight());
		if (docked.getValue() && !screen.getFullscreenProperty().getValue() && !windowBounds.contains(mouseLoc)) {
			launch(me.getScreenX() - pane.widthProperty().doubleValue() / 2,
					me.getScreenY() - title.heightProperty().doubleValue());
		} else if (!docked.getValue()) {
			if (windowBounds.contains(mouseLoc)) {
				dock();
			} else {
				stage.setX(me.getScreenX() - pane.widthProperty().doubleValue() / 2);
				stage.setY(me.getScreenY() - title.heightProperty().doubleValue());
			}
		}
	}

	private void launch(double x, double y) {
		home.getChildren().clear();
		stage = new Stage();
		stage.setScene(new Scene(pane));
		stage.setTitle(title.getText());
		stage.setX(x);
		stage.setY(y);
		stage.show();
		stage.setResizable(false);
		stage.setOnCloseRequest(e -> showing.setValue(false));
		stage.setAlwaysOnTop(true);
		docked.setValue(false);
	}

	private void dock() {
		if (stage != null) {
			stage.close();
		}
		home.add(pane, 0, 0);
		docked.setValue(true);
	}

	private void hide() {
		if (stage != null) {
			stage.close();
		}
		home.getChildren().clear();
		docked.setValue(false);
	}

	/**
	 * This property dictates the element's current state. When the value of
	 * this property is set to true, the element will dock itself. When the
	 * value is set to false, the element will be hidden.
	 * 
	 * @return the showing property
	 */

	public BooleanProperty getShowingProperty() {
		return showing;
	}

	/**
	 * This property keeps track of whether the element is docked or not (the
	 * showing property is also true when the element is open in a separate
	 * window). It is read only so that observers can be set but the value
	 * cannot change.
	 * 
	 * @return the docked property
	 */

	public ReadOnlyBooleanProperty getDockedProperty() {
		return docked;
	}
}
