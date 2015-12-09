package view.element;

import javafx.scene.layout.GridPane;
import view.visual.AbstractVisual;
/**
 * @author David
 * 
 * A wrapper class for a GridPane that allows for modular construction of Screens.
 * 
 */
public abstract class AbstractElement extends AbstractVisual {
	protected GridPane pane;

	public AbstractElement(GridPane pane) {
		this.pane = pane;
	}

	protected abstract void makePane();

	public GridPane getPane() {
		return pane;
	}

}
