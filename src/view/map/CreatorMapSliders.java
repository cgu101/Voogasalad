package view.map;

import javafx.scene.layout.GridPane;
import view.element.AbstractDockElement;
import view.level.LevelInterface;
import view.level.LevelMap;
import view.level.Workspace;
import view.screen.AbstractScreenInterface;

public class CreatorMapSliders extends MapSliders {

	public CreatorMapSliders(GridPane pane, GridPane home, String title, AbstractScreenInterface screen, Workspace workspace) {
		super(pane, home, title, screen);
		workspace.addListener((ov, oldTab, newTab) -> {
			load(workspace.getCurrentLevel());
		});
	}

	private void checkMap() {
		if (map == null) {
			showing.setValue(false);
		}
	}

	private void load(LevelInterface currentLevel) {
		pane.getChildren().clear();
		if (currentLevel instanceof LevelMap) {
			this.map = (LevelMap) currentLevel;
			makePane();
			showing.setValue(true);
		} else {
			map = null;
			showing.setValue(false);
		}
	}

}
