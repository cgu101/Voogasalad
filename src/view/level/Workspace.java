package view.level;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
import network.deprecated.RequestType;
import network.framework.GameWindow;
import network.framework.format.Mail;
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
									// default to 0.. Essentially load
									// game!)

		this.game = game;
		levelInfo = game.getBundleLevels();
		propertyInfo = game.getProperties();

		this.anscestors = new ArrayDeque<String>();
		this.anscestors.add(game.getUniqueID());
		
		makePane();
		
		for (Level level:levelInfo) {
//			System.out.println(level);
			addVisual(level);
		}
		
		initializeTimer();
	}

	public Workspace(GridPane pane, AbstractScreen screen) {
		this(pane, screen, new Game());
	}

	private void deleteVisual(Level l) {
		tabManager.getTabs().remove(levels.get(l.getUniqueID()).getTab());
		removeLevel(l);
	}

	public void addVisual(Level l) {
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
			try {
				currentLevel = levels.get(newTab.getId());
			} catch (NullPointerException e) {
				currentLevel = null;
			}
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
		LevelMap toAdd = new LevelMap(new GridPane(), level, screen);
		toAdd.setDeque(anscestors);
		
		levels.put(level.getUniqueID(), toAdd);
		levelInfo.add(level);

		configureTab(level);
	}

	private void initializeVisualLevelComponents() {
		for (AbstractDockElement dockElement : screen.getComponents()) {
			dockElement.getShowingProperty().setValue(true);
		}
	}

	public void refresh() {
		// deals with resizing problems
		addLevel(new Level(""));
		deleteVisual(levelInfo.get(""));
	}

	public void addSplashScreen(Level level) {
		addScreenElement(level);

		// LevelMap levelMap = (LevelMap) levels.get(level.getUniqueID());
	}

	private void addScreenElement(Level level) {
		LevelMap newLevel = new LevelMap(new GridPane(), level, screen);
		newLevel.setDeque(anscestors);
		
		levels.put(level.getUniqueID(), newLevel);
		levelInfo.add(level);

		addLevel(level);
	}

	public void configureTab(Level level) {
		LevelMap levelmap = levels.get(level.getUniqueID());
		Tab newTab = levelmap.getTab();
		newTab.setOnClosed(e -> {
			removeLevel(level);
			DataDecorator d = new DataDecorator(RequestType.DELETE, level, this.anscestors);
			GameWindow.getInstance().send(d);
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
		RequestType request = mail.getRequest();

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
		case LOAD: {
			loadVisual(data);
			break;
		}
		default: {
			break;
		}
		}
	}

	private void loadVisual(Level data) {
		// TODO Auto-generated method stub
		addVisual(data);
		LevelMap levelmap = levels.get(data.getUniqueID());
		levelmap.buildLevel();
		
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
	
	private void initializeTimer() {
//		Timer timer = new Timer();
//		timer.scheduleAtFixedRate(new TimerTask() {
//
//		    @Override
//		    public void run() {
//				DataDecorator dataMail = new DataDecorator(RequestType.GAME, game, null);
//				GameWindow.getInstance().send(dataMail);
//		    }
//
//		}, 0, 5000);
	}
	
}
