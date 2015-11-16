package view.element;

import javafx.scene.layout.GridPane;
import view.visual.AbstractVisual;

public abstract class AbstractElement extends AbstractVisual {
	public GridPane pane;

	public AbstractElement(GridPane pane) {
		this.pane = pane;
	}

	protected abstract void makePane();

	public GridPane getPane() {
		return pane;
	}

}
