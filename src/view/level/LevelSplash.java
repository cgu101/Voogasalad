package view.level;

import java.util.Deque;

import authoring.controller.AuthoringController;
import authoring.model.Anscestral;
import authoring.model.level.Level;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import network.framework.format.Mail;
import view.element.AbstractElement;
import view.screen.AbstractScreen;

public class LevelSplash extends AbstractElement implements LevelInterface {
	private Tab myTab;
	private String myString;
	private AbstractScreen screen;

	public LevelSplash(GridPane pane, int i, AbstractScreen screen) {
		super(pane);
		findResources();
		myString = myResources.getString("tabName");
		this.screen = screen;
		makePane();
		myTab = new Tab("" + i);
		myTab.setContent(pane);
		myTab.setId(Integer.toString(i));
	}

	@Override
	public Tab getTab() {
		return myTab;
	}

	@Override
	public AuthoringController getController() {
		return null;
	}

	@Override
	protected void makePane() {
		Rectangle start = new Rectangle();
		start.setFill(Color.valueOf(myResources.getString("background")));
		start.widthProperty().bind(screen.getScene().widthProperty());
		start.heightProperty().bind(screen.getScene().heightProperty());
		pane.add(start, 0, 0);
	}

	@Override
	public void initializeBackground(Image i) {
		pane.getChildren().clear();
		ImageView image = new ImageView(i);
		image.fitWidthProperty().bind(screen.getScene().widthProperty());
		image.fitHeightProperty().bind(screen.getScene().widthProperty());
		image.setPreserveRatio(true);
		pane.add(image, 0, 0);
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public void redraw(Level modelLevel) {
		// TODO Auto-generated method stub

	}

	@Override
	public Level buildLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<String> getAnscestralPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(Mail mail) {
		// TODO Auto-generated method stub

	}

	@Override
	public Anscestral getChild(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
