package view.level;

import authoring.controller.AuthoringController;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
		myTab = new Tab(makeTitle(i));
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
	public String makeTitle(int i) {
		return myString + (i + 1);
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
}
