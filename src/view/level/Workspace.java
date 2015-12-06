package view.level;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import authoring.model.Anscestral;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.level.Level;
import authoring.model.properties.Property;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import network.framework.format.Mail;
import network.framework.format.Request;
import network.instances.DataDecorator;
import view.element.AbstractDockElement;
import view.element.AbstractElement;
import view.screen.AbstractScreen;

public class Workspace extends AbstractElement implements Anscestral {

	private static final int DEFAULT_POSITION = 0;

	private TabPane tabManager;
	private AbstractScreen screen;
	private Map<String, LevelMap> levels;
	private LevelMap currentLevel;

	public Game game;
	public Bundle<Level> levelInfo;
	private Bundle<Property<?>> propertyInfo;

	private Deque<String> anscestors;

	public Workspace(GridPane pane, AbstractScreen screen, Game game) {
		super(pane);

		this.tabManager = new TabPane();
		this.screen = screen;
		this.levels = new HashMap<>();
		this.currentLevel = null; // TODO Make current level connect to
									// the server's current level (or
									// default to 1... Essentially load
									// game!)

		this.game = game;
		levelInfo = game.getBundleLevels();
		propertyInfo = game.getProperties();

		this.anscestors = new ArrayDeque<String>();

		makePane();
	}

	public Workspace(GridPane pane, AbstractScreen screen) {
		this(pane, screen, new Game());
	}

	private void deleteVisual(Level l) {
		tabManager.getTabs().remove(levels.get(l.getUniqueID()).getTab());
		removeLevel(l);
	}

	private void addVisual(Level l) {
		addScreenElement(l);
	}

	private void updateVisual(Level l) {
		LevelMap levelToBeModified = levels.get(l.getUniqueID());
		levelToBeModified.redraw(l);
		displayInfo(propertyInfo);
	}

	private void displayInfo(Bundle<Property<?>> p) {
		System.out.println(p.getSize());
	}

	/**
	 * Initialize tabManager
	 */
	@Override
	protected void makePane() {
		tabManager.setSide(Side.TOP);
		pane.add(tabManager, DEFAULT_POSITION, DEFAULT_POSITION);
		addListener((ov, oldTab, newTab) -> {
			tabManager.maxWidthProperty().unbind();
			currentLevel = levels.get(newTab.getId());
		});
	}

	public void addListener(ChangeListener<? super Tab> listener) {
		SingleSelectionModel<Tab> tabManagerModel = tabManager.getSelectionModel();
		tabManagerModel.selectedItemProperty().addListener(listener);
	}

	public void addLevel(Level level) {
		if (levels.size() == 0) {
			initializeVisualLevelComponents();
		}

		levels.put(level.getUniqueID(), new LevelMap(new GridPane(), level, screen));
		levelInfo.add(level);

		configureTab(level);
	}

	private void initializeVisualLevelComponents() {
		for (AbstractDockElement dockElement : screen.getComponents()) {
			dockElement.getShowingProperty().setValue(true);
		}
	}

	public void addSplashScreen(Level level) {
		addScreenElement(level);

		// LevelMap levelMap = (LevelMap) levels.get(level.getUniqueID());
	}

	private void addScreenElement(Level level) {
		LevelMap newLevel = new LevelMap(new GridPane(), level, screen);

		levels.put(level.getUniqueID(), newLevel);
		levelInfo.add(level);

		addLevel(level);
	}

	public void configureTab(Level level) {
		LevelMap levelmap = levels.get(level.getUniqueID());
		Tab newTab = levelmap.getTab();
		newTab.setOnClosed(e -> {
			removeLevel(level);
			DataDecorator d = new DataDecorator(Request.DELETE, level, this.anscestors);
			updateObservers(d);
		});
		tabManager.getTabs().add(levels.size() - 1, newTab);
		tabManager.getSelectionModel().select(newTab);
	}

	private void removeLevel(Level level) {
		levels.remove(level.getUniqueID());
		levelInfo.remove(level.getUniqueID());
	}

	public void moveLevel(Boolean left) {
		int currentTabIndex = tabManager.getSelectionModel().getSelectedIndex();

		if (left && currentTabIndex <= 0 || !left && currentTabIndex > tabManager.getTabs().size()) {
			return;
		}

		if (left) {
			tabManager.getSelectionModel().select(currentTabIndex - 1);
		} else {
			tabManager.getSelectionModel().select(currentTabIndex + 1);
		}
	}

	public void updateObservers(Object o) {
		setChanged();
		notifyObservers(o);
	}

	@Override
	public Deque<String> getAnscestralPath() {
		return this.anscestors;
	}

	@Override
	public void process(Mail mail) {
		Level data = (Level) mail.getData();
		Request request = mail.getRequest();

		switch (request) {
		case ADD: {
			addVisual(data);
			break;
		}
		case DELETE: {
			deleteVisual(data);
			break;
		}
		case MODIFY: {
			updateVisual(data);
			break;
		}
		case TRANSITION: {
			addSplashScreen(data);
			break;
		}
		default: {
			break;
		}
		}
	}

	public Game getGame() {
		return game;
	}

	public LevelMap getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public Anscestral getChild(String id) {
		Anscestral a = this.levels.get(id);
		return a;
	}
}
