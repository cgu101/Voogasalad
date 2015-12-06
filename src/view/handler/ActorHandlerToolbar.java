package view.handler;

import javafx.scene.layout.GridPane;
import view.element.AbstractDockElement;
import view.level.LevelInterface;
import view.level.LevelMap;
import view.level.Workspace;
import view.map.Map;
import view.screen.AbstractScreenInterface;

public class ActorHandlerToolbar extends AbstractDockElement {
	private Map map;

	public ActorHandlerToolbar(GridPane pane, GridPane home, String title, AbstractScreenInterface screen,
			Workspace w) {
		super(pane, home, title, screen);
		w.addListener((ov, oldTab, newTab) -> {
			load(w.getCurrentLevelInterface());
		});
	}

	@Override
	protected void makePane() {
		GridPane labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		pane.add(map.getToolbar(), 1, 0);
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
