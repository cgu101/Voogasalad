package view.interactions;

import authoring.controller.AuthoringController;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;

public class InteractionCell extends TreeCell<InteractionData>{
	
	private ContextMenu menu;
	private GridPane pane;
	private AuthoringController controller;

	public InteractionCell (GridPane pane, AuthoringController controller) {
		this.pane = pane;
		this.controller = controller;
		menu = new ContextMenu();
		if (getItem() != null && getItem().getType().equals("Trigger")) {
			MenuItem triggerItem = new MenuItem("Add Trigger");
			menu.getItems().add(triggerItem);
			triggerItem.setOnAction(e -> {
				TriggerParametersView view = new TriggerParametersView(getItem(), pane, controller);
				TreeItem<InteractionData> newTrigger = 
						new TreeItem<InteractionData>(new InteractionData(null, "new",null, null));
				
				getTreeItem().getChildren().add(newTrigger);
				
			});
			MenuItem actionItem = new MenuItem("Add Action");
			menu.getItems().add(actionItem);
			triggerItem.setOnAction(e -> {
				TreeItem<InteractionData> newAction = 
						new TreeItem<InteractionData>();
				getTreeItem().getChildren().add(newAction);
			});
		}
		MenuItem editItem = new MenuItem("Edit");
		menu.getItems().add(editItem);
		editItem.setOnAction(e -> {
			TriggerParametersView view = new TriggerParametersView(getItem(), pane, controller);
		});
		MenuItem deleteItem = new MenuItem("Delete");
		menu.getItems().add(deleteItem);
		deleteItem.setOnAction(e -> {
			TreeItem<InteractionData> item = getTreeItem();
			item.getParent().getChildren().remove(item);
		});
		
	}
	@Override
	public void updateItem(InteractionData item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			setText(getItem() == null ? "" : getItem().getID());
            setGraphic(getTreeItem().getGraphic());
			if (getTreeItem().getParent()!= null){
				setContextMenu(menu);
			} else {
				ContextMenu rootMenu = new ContextMenu();
				MenuItem triggerItem = new MenuItem("Add Trigger");
				rootMenu.getItems().add(triggerItem);
				triggerItem.setOnAction(e -> {
					TriggerParametersView view = new TriggerParametersView(getItem(), pane, controller);
					TreeItem<InteractionData> newTrigger = 
							new TreeItem<InteractionData>(new InteractionData("Trigger", "new",null,null));
					
					getTreeItem().getChildren().add(newTrigger);
					
				});
				setContextMenu(rootMenu);
			}
		}
	}
}


