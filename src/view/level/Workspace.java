package view.level;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.Anscestral;
import authoring.model.game.Game;
import authoring.model.level.Level;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import network.framework.Mail;
import network.framework.Request;
import network.test.GameWindow;
import view.element.AbstractDockElement;
import view.element.AbstractElement;
import view.screen.AbstractScreen;

public class Workspace extends AbstractElement implements Anscestral {

	private static final int DEFAULT_POSITION = 0;

	private TabPane tabManager;
	private AbstractScreen screen;
	private Map<String, LevelInterface> levelInterfaceMap;
	private LevelInterface currentLevelInterface;

	private Game game;
	private GameWindow network;
	
	private List<String> anscestors;

	public Workspace (GridPane pane, AbstractScreen screen) {
		super(pane);

		this.tabManager = new TabPane();
		this.screen = screen;
		this.levelInterfaceMap = new HashMap<>();
		this.currentLevelInterface = null; // TODO Make current level connect to the server's current level (or default to 1... Essentially load game!)

		this.game = new Game();
		this.network = null; // Default to no network.

		makePane();
	}

	public void updateVisual (GameWindow w, Game g) {
		network = w;
		game = g;

		Collection<Level> myLevels = g.getLevels();

		for (Level modelLevel : myLevels) {
			if (levelInterfaceMap.get(modelLevel.getUniqueID()) == null) {
				LevelInterface newLevelInterface = new LevelMap(new GridPane(), modelLevel, screen);

				levelInterfaceMap.put(modelLevel.getUniqueID(), newLevelInterface);
				addLevel(modelLevel);

				game = buildGame();

				network.send(game);
			} else {
				LevelInterface levelToBeModified = levelInterfaceMap.get(modelLevel.getUniqueID());
				levelToBeModified.redraw(modelLevel);
			}
		}

	}

	/**
	 * Initialize tabManager
	 */
	@Override
	protected void makePane () {
		tabManager.setSide(Side.TOP);
		pane.add(tabManager, DEFAULT_POSITION, DEFAULT_POSITION);
		addListener((ov, oldTab, newTab) -> {
			tabManager.maxWidthProperty().unbind();
			currentLevelInterface = levelInterfaceMap.get(newTab.getId());
		});
	}

	public void addListener (ChangeListener<? super Tab> listener) {
		SingleSelectionModel<Tab> tabManagerModel = tabManager.getSelectionModel();
		tabManagerModel.selectedItemProperty().addListener(listener);
	}

	public void addLevel (Level level) {
		if (levelInterfaceMap.size() == 0) {
			initializeVisualLevelComponents();
		}

		LevelInterface newLevelInterface = new LevelMap(new GridPane(), level, screen);
		levelInterfaceMap.put(newLevelInterface.getTitle(), newLevelInterface);
		configureTab(newLevelInterface);
	}

	private void initializeVisualLevelComponents () {
		for (AbstractDockElement dockElement : screen.getComponents()) {
			dockElement.getShowingProperty().setValue(true);
		}
	}

	public void addSplashScreen () {
		//TODO
	}

	public void configureTab (LevelInterface levelInterface) {
		Tab newTab = levelInterface.getTab();
		newTab.setOnClosed(e -> removeLevel(levelInterface));
		tabManager.getTabs().add(levelInterfaceMap.size() - 1, newTab);
		tabManager.getSelectionModel().select(newTab);
	}

	private void removeLevel (LevelInterface levelInterface) {
		levelInterfaceMap.remove(levelInterface.getTitle());
	}

	public LevelInterface getCurrentLevel () {
		return currentLevelInterface;
	}

	public void moveLevel (Boolean left) {
		int currentTabIndex = tabManager.getSelectionModel().getSelectedIndex();

		if (left && currentTabIndex <= 0 ||
				!left && currentTabIndex > tabManager.getTabs().size()) {
			return;
		}

		if (left) {
			tabManager.getSelectionModel().select(currentTabIndex - 1);
		} else {
			tabManager.getSelectionModel().select(currentTabIndex + 1);
		}
	}

	private Game buildGame () {
		Game changedGame = new Game();

		changedGame.addAllProperties(game.getProperties());

		for (LevelInterface levelInterface : levelInterfaceMap.values()) {
			Level newLevel = levelInterface.buildLevel();
			changedGame.addLevel(newLevel);
		}

		return changedGame;
	}
	
	public void addNetwork (GameWindow gw) {
		this.network = gw;
	}

	@Override
	public Deque<String> getAnscestralPath() {
		// TODO
		return null;
	}

	@Override
	public void process(Mail mail) {
		// TODO
	}
}
