package view.level;

import java.util.ArrayList;
import java.util.List;

import authoring.controller.constructor.levelwriter.LevelConstructor;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import network.test.GameWindow;
import view.element.AbstractDockElement;
import view.element.AbstractElement;
import view.screen.AbstractScreen;

public class Workspace extends AbstractElement {
	private TabPane manager;
	private ArrayList<LevelInterface> levels;
	private LevelInterface currentLevel;
	private AbstractScreen screen;
	private GameWindow gameWindow;

	public Workspace(GridPane pane, AbstractScreen screen) {
		super(pane);
		this.screen = screen;
		makePane();
	}
	
//	public Workspace (SGameState gs) {
//		buildWorkspace(gs);
//	}
	
//	public SGameState buildGameState(Workspace w);

	@Override
	protected void makePane() {
		manager = new TabPane();
		levels = new ArrayList<LevelInterface>();
		manager.setSide(Side.TOP);
		pane.add(manager, 0, 0);
		addListener((ov, oldTab, newTab) -> {
			manager.maxWidthProperty().unbind();
			try {
				currentLevel = levels.get(Integer.parseInt(newTab.getId()));
			} catch (NullPointerException e) {
				currentLevel = null;
			}
		});
	}

	public void addListener(ChangeListener<? super Tab> e) {
		manager.getSelectionModel().selectedItemProperty().addListener(e);
	}

	public Tab addLevel() {
		if (levels.size() == 0) {
			for (AbstractDockElement c : screen.getComponents()) {
				c.getShowingProperty().setValue(true);
			}
		}
		LevelMap newLevel = new LevelMap(new GridPane(), levels.size(), screen);
		newLevel.setGameWindow(gameWindow);
		return configureTab(newLevel);
	}

	public Tab addSplash() {
		LevelSplash newLevel = new LevelSplash(new GridPane(), levels.size(), screen);
		return configureTab(newLevel);
	}

	public Tab configureTab(LevelInterface newLevel) {
		levels.add(newLevel);
		Tab newLevelTab = newLevel.getTab();
		int newID = Integer.parseInt(newLevelTab.getId());
		newLevelTab.setOnClosed(e -> removeLevel(newLevelTab));
		manager.getTabs().add(levels.size() - 1, newLevelTab);
		manager.getSelectionModel().select(newID);
		return newLevelTab;
	}

	public void moveLevelLeft(Boolean left) {
		if (levels.size() == 0)
			return;
		int currID = Integer.parseInt(currentLevel.getTab().getId());

		int switchID;
		if (left) {
			switchID = currID - 1;
		} else {
			switchID = currID + 1;
		}

		if (switchID >= levels.size() || switchID < 0)
			return;

		LevelInterface switchLevel = levels.get(switchID);

		currentLevel.getTab().setId(Integer.toString(switchID));
		currentLevel.getTab().setText(currentLevel.makeTitle(switchID));
		switchLevel.getTab().setId(Integer.toString(currID));
		switchLevel.getTab().setText(switchLevel.makeTitle(currID));

		manager.getTabs().remove(switchLevel.getTab());
		manager.getTabs().add(currID, switchLevel.getTab());

		levels.remove(switchLevel);
		levels.add(currID, switchLevel);

		manager.getSelectionModel().select(switchID);
	}

	public List<LevelConstructor> getLevels() {
		List<LevelConstructor> levelConstructorList = new ArrayList<LevelConstructor>();
		for (LevelInterface levelMap : levels) {
			levelConstructorList.add(levelMap.getController().getLevelConstructor());
		}
		return levelConstructorList;
	}

	private void removeLevel(Tab tab) {
		int Id = Integer.parseInt(tab.getId());
		levels.remove(Id);
		for (int i = Id; i < levels.size(); i++) {
			levels.get(i).getTab().setId(Integer.toString(i));
			levels.get(i).getTab().setText("Level " + (i + 1));
		}
	}

	public LevelInterface getCurrentLevel() {
		return currentLevel;
	}

	public void setGameWindow(GameWindow g) {
		this.gameWindow = g;
	}

}
