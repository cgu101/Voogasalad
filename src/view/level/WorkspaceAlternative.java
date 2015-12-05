package view.level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.controller.constructor.levelwriter.LevelConstructor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.level.Level;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import network.test.GameWindow;
import view.element.AbstractDockElement;
import view.element.AbstractElement;
import view.screen.AbstractScreen;

public class WorkspaceAlternative extends AbstractElement {
	private TabPane manager;
	private ArrayList<LevelInterface> levels;
	private LevelInterface currentLevel;
	private AbstractScreen screen;
	
	private Game myGame;
	private GameWindow myNetworkGame;
	
	@Deprecated
	public WorkspaceAlternative(GridPane pane, AbstractScreen screen) {
		super(pane);
		this.screen = screen;
		makePane();
		
		myGame = null;
		myNetworkGame = null;
	}
	
	@Deprecated
	public void updateVisual (GameWindow w, Game g) {
		myNetworkGame = w;
		myGame = g;
		
		System.out.println("A new level is registered");
		
		Collection<Level> myLevels = g.getLevels();
		Map<String, LevelInterface> myLevelMap = new HashMap<>();
		
		for (LevelInterface levelInterface : levels) {
			myLevelMap.put(levelInterface.getTitle(), levelInterface);
		}
		
		for (Level modelLevel : myLevels) {
			if (myLevelMap.get(modelLevel.getUniqueID()) == null) {
				LevelInterface newLevelInterface = new LevelMap(new GridPane(), modelLevel, screen);
				
				myLevelMap.put(modelLevel.getUniqueID(), newLevelInterface);
				addLevel(modelLevel);

				myGame = buildGame();
				
				myNetworkGame.send(myGame);
			} else {
				LevelInterface levelToBeModified = myLevelMap.get(modelLevel.getUniqueID());
				levelToBeModified.redraw(modelLevel);
			}
		}
	}

	@Override
	@Deprecated
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

	@Deprecated
	public void addListener(ChangeListener<? super Tab> e) {
		manager.getSelectionModel().selectedItemProperty().addListener(e);
	}

	@Deprecated
	public Tab addLevel() {
		if (levels.size() == 0) {
			for (AbstractDockElement c : screen.getComponents()) {
				c.getShowingProperty().setValue(true);
			}
		}
		LevelMap newLevel = new LevelMap(new GridPane(), levels.size(), screen);
		return configureTab(newLevel);
	}
	
	@Deprecated
	public Tab addLevel (Level l) {
		if (levels.size() == 0) {
			for (AbstractDockElement c : screen.getComponents()) {
				c.getShowingProperty().setValue(true);
			}
		}
		LevelMap newLevel = new LevelMap(new GridPane(), l, screen);
		return configureTab(newLevel);
	}

	@Deprecated
	public Tab addSplash() {
		LevelSplash newLevel = new LevelSplash(new GridPane(), levels.size(), screen);
		return configureTab(newLevel);
	}

	@Deprecated
	public Tab configureTab(LevelInterface newLevel) {
		levels.add(newLevel);
		Tab newLevelTab = newLevel.getTab();
		int newID = Integer.parseInt(newLevelTab.getId());
		newLevelTab.setOnClosed(e -> removeLevel(newLevelTab));
		manager.getTabs().add(levels.size() - 1, newLevelTab);
		manager.getSelectionModel().select(newID);
		return newLevelTab;
	}

	@Deprecated
	public void moveLevel (Boolean left) {
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

	@Deprecated
	public List<LevelConstructor> getLevels() {
		List<LevelConstructor> levelConstructorList = new ArrayList<LevelConstructor>();
		for (LevelInterface levelMap : levels) {
			levelConstructorList.add(levelMap.getController().getLevelConstructor());
		}
		return levelConstructorList;
	}
	
	@Deprecated
	private void removeLevel(Tab tab) {
		int Id = Integer.parseInt(tab.getId());
		levels.remove(Id);
		for (int i = Id; i < levels.size(); i++) {
			levels.get(i).getTab().setId(Integer.toString(i));
			levels.get(i).getTab().setText("Level " + (i + 1));
		}
	}

	@Deprecated
	public LevelInterface getCurrentLevel() {
		return currentLevel;
	}
	
	@Deprecated
	private Game buildGame () {
		Game changeGame = new Game();
		changeGame.addAllProperties(myGame.getProperties());
		
		Bundle<Level> changedLevelBundle = new Bundle<Level>();
		
		for (LevelInterface l : levels) {
			Level newLevel = l.buildLevel();
			changedLevelBundle.add(newLevel);
		}
		
		changeGame.addAllLevels(changedLevelBundle);
		
		return changeGame;
	}
	
	@Deprecated
	public void addNetwork (GameWindow gw) {
		this.myNetworkGame = gw;
	}
}
