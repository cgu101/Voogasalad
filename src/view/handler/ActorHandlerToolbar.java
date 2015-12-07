package view.handler;

import javafx.scene.layout.GridPane;
import view.element.AbstractDockElement;
import view.level.LevelMap;
import view.level.LevelType;
import view.level.Workspace;
import view.map.Map;
import view.screen.AbstractScreenInterface;

public class ActorHandlerToolbar extends AbstractDockElement {
	private Map map;

	public ActorHandlerToolbar(GridPane home, String title, AbstractScreenInterface screen, Workspace w) {
		super(home, title, screen);
		w.addListener((ov, oldTab, newTab) -> {
			load(w.getCurrentLevel());
		});
	}

	@Override
	protected void makePane() {
		pane.add(titlePane, 0, 0);
		pane.add(map.getToolbar(), 1, 0);
	}

	private void load(LevelMap currentLevel) {
		pane.getChildren().clear();
		if (currentLevel.getType() == LevelType.LEVEL) {
			this.map = (LevelMap) currentLevel;
			makePane();
			showing.setValue(true);
		} else {
			map = null;
			showing.setValue(false);
		}
	}

}
