package view.element;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public abstract class ControlBar extends AbstractElement {

	public ControlBar(GridPane pane) {
		super(pane);
		findResources();
	}

	@Override
	protected abstract void makePane();

	protected Button makeButton(String s, EventHandler<ActionEvent> handler) {
		Button button = new Button();
		Image img = new Image(getClass().getClassLoader().getResourceAsStream(myResources.getString(s)));
		ImageView image = new ImageView(img);
		image.setFitHeight(Double.parseDouble(myResources.getString("height")));
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);
		button.setGraphic(image);
		Tooltip tooltip = new Tooltip();
		tooltip.setText(myResources.getString(s + ".message"));
		button.setTooltip(tooltip);
		button.setFocusTraversable(false);
		button.setOnAction(handler);
		return button;
	}

	protected MenuItem makeMenuItem(String s, EventHandler<ActionEvent> handler) {
		MenuItem m = new MenuItem(s);
		m.setOnAction(handler);
		return m;
	}

	protected Menu addToMenu(Menu menu, MenuItem... ms) {
		for (MenuItem m : ms) {
			menu.getItems().add(m);
		}
		return menu;
	}

	protected void makeMenuBar(MenuBar init, Menu... ms) {
		for (Menu m : ms) {
			init.getMenus().add(m);
		}
	}
}
