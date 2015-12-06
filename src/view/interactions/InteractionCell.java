package view.interactions;

import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import authoring.controller.AuthoringController;
import authoring.model.tree.ActionTreeNode;
import authoring.model.tree.ParameterTreeNode;
import authoring.model.tree.TriggerTreeNode;
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

public class InteractionCell extends TreeCell<ParameterTreeNode>{

	private GridPane pane;
	private AuthoringController controller;
	private String[] actors;
	private ResourceBundle resources = ResourceBundle.getBundle("resources/InteractionCell");
	private static final String PROPERTY_RESOURCES_PATH = "authoring/files/";
	private static final String ACTIONS = "actions/";
	private static final String TRIGGERS = "triggers/";

	public InteractionCell (GridPane pane, AuthoringController controller, String... actors) {
		this.pane = pane;
		this.controller = controller;
		this.actors = actors;

	}
	@SuppressWarnings("unchecked")
	@Override
	public void updateItem(ParameterTreeNode item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(getItem() == null || getTreeItem().getParent() == null ? "" : getItem().getIdentifier().split("TreeNode")[0]);
			HBox box = new HBox(2);
			ImageView icon;
			try {
				String iconName = resources.getString(getItem().getIdentifier() + "Icon");
				icon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(iconName)));
			} catch (Exception e) {
				icon = new ImageView();
			}
			icon.setPreserveRatio(true);
			icon.setFitHeight(Double.parseDouble(resources.getString("IconHeight")));
			box.getChildren().add(icon);
			box.getChildren().add(new Text(getItem().getValue()));
			setGraphic(box);
			ContextMenu menu = new ContextMenu();
			if (getTreeItem().getParent()!= null){
				if (getItem() != null && getItem().getIdentifier().equals(TriggerTreeNode.class.getSimpleName())) {
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
				tooltext = ((Set<Entry<String,Object>>)item.getParameters().getParameterAndValues()).stream()
					.map(e -> {return e.getKey() + ": " + (String) e.getValue();})
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
			TriggerTreeNode triggerNode = new TriggerTreeNode("Trigger");
			TriggerParametersView view = new TriggerParametersView(triggerNode, pane, controller);
			TreeItem<ParameterTreeNode> newTrigger = 
					new TreeItem<ParameterTreeNode>(triggerNode);
			newTrigger.setExpanded(true);
			getItem().addChild(triggerNode);
			getTreeItem().getChildren().add(newTrigger);

		});
		return triggerItem;
	}
	private MenuItem makeAddActionItem () {
		MenuItem actionItem = new MenuItem("Add Action");
		actionItem.setOnAction(e -> {
			ActionTreeNode actionNode = new ActionTreeNode("Action");
			TreeItem<ParameterTreeNode> newAction = 
					new TreeItem<ParameterTreeNode>(actionNode);
			getItem().addChild(actionNode);
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
			TreeItem<ParameterTreeNode> treeItem = getTreeItem();
			treeItem.getParent().getValue().remove(getItem());
			treeItem.getParent().getChildren().remove(treeItem);
		});
		return deleteItem;
	}
	public String[] getActors() {
		return actors;
	}
}


