package view.map;

import javafx.scene.layout.GridPane;
import view.element.AbstractDockElement;
import view.level.LevelMap;
import view.level.Workspace;
import view.screen.AbstractScreenInterface;

public abstract class MapSliders extends AbstractDockElement {

	protected view.map.Map map;
	
	public MapSliders(GridPane pane, GridPane home, String title, AbstractScreenInterface screen) {
		super(pane, home, title, screen);
	}

	@Override
	protected void makePane() {
		GridPane labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		pane.add(map.getZoomSlider(), 1, 0);
		pane.add(map.getResizerSlider(), 2, 0);
		pane.add(map.getOpacitySlider(), 3, 0);
	}

}
