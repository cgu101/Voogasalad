package view.map;

import javafx.scene.layout.GridPane;
import view.element.AbstractDockElement;
import view.screen.AbstractScreenInterface;

public class PlayerMapSliders extends MapSliders {

	
	public PlayerMapSliders(GridPane pane, GridPane home, String title, AbstractScreenInterface screen) {
		super(pane, home, title, screen);
	}

	public void initializeMap(view.map.Map map){
		this.map = map;
		makePane();
	}

}
