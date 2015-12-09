package view.controlbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.layout.GridPane;
import view.element.AbstractDockElement;
import view.element.AbstractElement;
import view.screen.AbstractScreen;

/**
 * @author David
 * @author Bridget
 * 
 *         This class is used to make control bars at the top of Screens
 * 
 */
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

	protected MenuItem makeMenuItem(String s, EventHandler<ActionEvent> handler, KeyCode key, Modifier mod) {
		MenuItem menu = makeMenuItem(s, handler);
		if (mod == null) {
			menu.setAccelerator(new KeyCodeCombination(key));
		} else {
			menu.setAccelerator(new KeyCodeCombination(key, mod));
		}
		return menu;
	}
	
	protected CheckMenuItem makeCheckMenuItem(String s, KeyCode key, Modifier mod){
		CheckMenuItem menu = new CheckMenuItem(s);
		if(mod == null){
			menu.setAccelerator(new KeyCodeCombination(key));
		} else {
			menu.setAccelerator(new KeyCodeCombination(key, mod));
		}
		return menu;
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

	protected void makeComponentCheckMenus(Menu window, AbstractScreen screen) {
		for (AbstractDockElement c : screen.getComponents()) {
			CheckMenuItem item = new CheckMenuItem(myResources.getString(c.getClass().getSimpleName()));
			item.selectedProperty().bindBidirectional(c.getShowingProperty());
			addToMenu(window, item);
		}
		MenuItem show = makeMenuItem(myResources.getString("show"), e -> toggleComponents(true, screen));
		MenuItem hide = makeMenuItem(myResources.getString("hide"), e -> toggleComponents(false, screen));
		addToMenu(window, show);
		addToMenu(window, hide);
	}

	protected void toggleComponents(boolean showing, AbstractScreen screen) {
		for (AbstractDockElement c : screen.getComponents()) {
			
			if (c.getShowingProperty().getValue() != showing) {
				c.getShowingProperty().setValue(showing);
			}
		}
	}
}
