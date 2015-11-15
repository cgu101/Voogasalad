package view.element;

import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.screen.AbstractScreen;

public class LevelMap extends AbstractElement {
	private Tab myTab;
	private Rectangle map;
	private AbstractScreen screen;

	public LevelMap(GridPane pane, int i, AbstractScreen screen) {
		super(pane);
		findResources();
		myTab = new Tab("Level " + (i + 1));
		myTab.setContent(pane);
		myTab.setId(Integer.toString(i));
		this.screen = screen;
		makePane();
	}

	public Tab getTab() {
		return myTab;
	}

	@Override
	protected void makePane() {
		map = new Rectangle();
		map.setFill(Color.valueOf(myResources.getString("background")));
		map.widthProperty().bind(screen.getScene().widthProperty());
		map.heightProperty().bind(screen.getScene().heightProperty());
		pane.add(map, 0, 0);
	}

	public Rectangle getMap() {
		return map;
	}

}
