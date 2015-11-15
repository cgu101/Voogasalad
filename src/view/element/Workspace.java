package view.element;

import java.util.ArrayList;

import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import view.screen.AbstractScreen;

public class Workspace extends AbstractElement {
	private TabPane manager;
	private ArrayList<LevelMap> levels;
	private LevelMap currentLevel;
	private AbstractScreen screen;

	public Workspace(GridPane pane, AbstractScreen screen) {
		super(pane);
		this.screen = screen;
		makePane();
	}

	@Override
	protected void makePane() {
		manager = new TabPane();
		levels = new ArrayList<LevelMap>();
		manager.setSide(Side.TOP);
		pane.add(manager, 0, 0);
		manager.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			manager.maxWidthProperty().unbind();
			try {
				currentLevel = levels.get(Integer.parseInt(newTab.getId()));
				manager.maxWidthProperty().bind(currentLevel.getMap().widthProperty());
			} catch (NullPointerException e) {
				currentLevel = null;
			}
		});
	}

	public Tab addLevel() {
		if (levels.size() == 0) {
			for (AbstractDockElement c : screen.getComponents()) {
				c.isShowing().setValue(true);
			}
		}
		LevelMap newLevel = new LevelMap(new GridPane(), levels.size(), screen);
		levels.add(newLevel);
		Tab newLevelTab = newLevel.getTab();
		int newID = Integer.parseInt(newLevelTab.getId());
		newLevelTab.setOnClosed(e -> removeLevel(newLevelTab));
		manager.getTabs().add(levels.size() - 1, newLevelTab);
		manager.getSelectionModel().select(newID);
		return newLevelTab;
	}

	private void removeLevel(Tab tab) {
		int Id = Integer.parseInt(tab.getId());
		levels.remove(Id);
		for (int i = Id; i < levels.size(); i++) {
			levels.get(i).getTab().setId(Integer.toString(i));
			levels.get(i).getTab().setText("Level " + (i + 1));
		}
	}
}
