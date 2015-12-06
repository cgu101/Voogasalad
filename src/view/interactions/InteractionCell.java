package view.interactions;

import java.util.ResourceBundle;
import java.util.stream.Collectors;

import authoring.controller.AuthoringController;
import authoring.model.tree.InteractionTreeNode;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class InteractionCell extends TreeCell<InteractionTreeNode>{

	private GridPane pane;
	private AuthoringController controller;
	private ResourceBundle resources = ResourceBundle.getBundle("resources/InteractionCell");

	public InteractionCell (GridPane pane, AuthoringController controller) {
		this.pane = pane;
		this.controller = controller;

	}
	@Override
	public void updateItem(InteractionTreeNode item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(getItem() == null ? "" : getItem().getID());
			HBox box = new HBox(2);
			ImageView icon;
			try {
				String iconName = resources.getString(getItem().getType() + "Icon");
				icon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(iconName)));
			} catch (Exception e) {
				icon = new ImageView();
			}
			icon.setPreserveRatio(true);
			icon.setFitHeight(Double.parseDouble(resources.getString("IconHeight")));
			box.getChildren().add(icon);
			box.getChildren().add(new Text(getItem().getID()));
			setGraphic(box);
			ContextMenu menu = new ContextMenu();
			if (getTreeItem().getParent()!= null){
				if (getItem() != null && getItem().getType().equals("Trigger")) {
					menu.getItems().add(makeAddTriggerItem());
					menu.getItems().add(makeAddActionItem());
				}
				menu.getItems().add(makeEditItem());
				menu.getItems().add(makeDeleteItem());
			} else {
				menu.getItems().add(makeAddTriggerItem());
			}
			setContextMenu(menu);
			String tooltext;
			try {
				tooltext = item.getValues().stream()
						.map(e -> {return e.getText() + ": " + e.getValue();})
						.collect(Collectors.joining("\n"));
			} catch (NullPointerException n) {
				tooltext = "";
			}
			setTooltip(new Tooltip(tooltext));
		}
	}
	private MenuItem makeAddTriggerItem () {
		MenuItem triggerItem = new MenuItem("Add Trigger");
		triggerItem.setOnAction(e -> {
			TriggerParametersView view = new TriggerParametersView(getItem(), pane, controller);
			TreeItem<InteractionTreeNode> newTrigger = 
					new TreeItem<InteractionTreeNode>(new InteractionTreeNode("Trigger", "notlikethis",null, null));
			newTrigger.setExpanded(true);
			getTreeItem().getChildren().add(newTrigger);

		});
		return triggerItem;
	}
	private MenuItem makeAddActionItem () {
		MenuItem actionItem = new MenuItem("Add Action");
		actionItem.setOnAction(e -> {
			TreeItem<InteractionTreeNode> newAction = 
					new TreeItem<InteractionTreeNode>(new InteractionTreeNode("Action", "a",null,null));
			getTreeItem().getChildren().add(newAction);
		});
		return actionItem;
	}
	private MenuItem makeEditItem () {
		MenuItem editItem = new MenuItem("Edit");
		editItem.setOnAction(e -> {
			TriggerParametersView view = new TriggerParametersView(getItem(), pane, controller);
		});
		return editItem;
	}
	private MenuItem makeDeleteItem () {
		MenuItem deleteItem = new MenuItem("Delete");
		deleteItem.setOnAction(e -> {
			TreeItem<InteractionTreeNode> treeItem = getTreeItem();
			treeItem.getParent().getChildren().remove(treeItem);
		});
		return deleteItem;
	}
}


