package view.actor;

import javafx.scene.control.ListCell;

public abstract class AbstractListCell extends ListCell<String> {
	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else if (item != null) {
			makeCell(item);
		}
	}

	protected abstract void makeCell(String item);
}
