package view.element;

import javafx.scene.layout.GridPane;
import view.level.LevelInterface;
import view.level.LevelMap;
import view.level.Workspace;
import view.screen.AbstractScreenInterface;

public class Slider extends AbstractDockElement {

	private LevelMap map;

	public Slider(GridPane pane, GridPane home, String title, AbstractScreenInterface screen, Workspace workspace) {
		super(pane, home, title, screen);
		showing.addListener(e -> checkMap());
		workspace.addListener((ov, oldTab, newTab) -> {
			if (workspace.getCurrentLevel() != null) {
				load(workspace.getCurrentLevel());
			} else {
				load(null);
			}
		});
	}

	private void checkMap() {
		if (map == null) {
			showing.setValue(false);
		}
	}

	private void load(LevelInterface currentLevel) {
		if (currentLevel instanceof LevelMap) {
			this.map = (LevelMap) currentLevel;
			makePane();
		} else {
			pane.getChildren().clear();
			map = null;
			pane.getChildren().clear();
			showing.setValue(false);
		}
	}

	@Override
	protected void makePane() {
		GridPane labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		pane.add(map.getSlider(), 1, 0);
	}

}
