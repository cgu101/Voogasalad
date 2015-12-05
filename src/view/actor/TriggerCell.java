package view.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.controlsfx.control.CheckComboBox;

import authoring.controller.AuthoringController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
/**
 * @author David
 * 
 * This class is designed to be used with the ActorEditor
 * It allows for modification of Triggers
 * 
 */
public class TriggerCell extends AbstractListCell<String> {
	private AuthoringController controller;
	private String actor;
	private String other;

	public TriggerCell(AuthoringController controller, String actor) {
		findResources();
		this.controller = controller;
		this.actor = actor;
		this.other = null;
	}

	public TriggerCell(AuthoringController controller, String actor, String other) {
		this(controller, actor);
		this.other = other;
	}

	private Text makeNameField(String item) {
		String triggerName = item.substring(item.lastIndexOf('.') + 1);
		Text text = new Text(triggerName + " -> ");
		text.setFont(textFont);
		return text;
	}

	@Override
	protected void makeCell(String item) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().add(makeNameField(item));
		box.getChildren().add(makeSelector(item));
		setGraphic(box);
	}

	protected CheckComboBox<String> makeSelector(String item) {
		final ObservableList<String> actions = FXCollections.observableArrayList();
		CheckComboBox<String> selector = new CheckComboBox<String>(actions);
		if (other == null) {
			actions.addAll(controller.getAuthoringActorConstructor().getActionList(actor));
			selector.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
				public void onChanged(ListChangeListener.Change<? extends String> c) {
					List<String> actions = selector.getCheckModel().getCheckedItems();
					
					controller.getLevelConstructor().getTreeConstructor().deleteTreeNode(
							new ArrayList<String>(Arrays.asList(new String[] {actor})),
							new ArrayList<String>(Arrays.asList(new String[] {item})),
									null);
					
					controller.getLevelConstructor().getTreeConstructor().addTreeNode(
							new ArrayList<String>(Arrays.asList(new String[] {actor})),
							new ArrayList<String>(Arrays.asList(new String[] {item})),
									actions);
					
//					controller.getLevelConstructor().getTreeConstructor().removeSelfTrigger(actor, item);
//					controller.getLevelConstructor().getTreeConstructor().addSelfTriggerActions(actor, item, actions);
				}
			});
		} else {
			actions.addAll(controller.getAuthoringActorConstructor().getTwoActorActionList(actor, other));
			selector.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
				public void onChanged(ListChangeListener.Change<? extends String> c) {
					List<String> actions = selector.getCheckModel().getCheckedItems();
					
					controller.getLevelConstructor().getTreeConstructor().deleteTreeNode(
							new ArrayList<String>(Arrays.asList(new String[] {actor, other})),
							new ArrayList<String>(Arrays.asList(new String[] {item})),
									null);
					
					controller.getLevelConstructor().getTreeConstructor().addTreeNode(
							new ArrayList<String>(Arrays.asList(new String[] {actor, other})),
							new ArrayList<String>(Arrays.asList(new String[] {item})),
									actions);
					
//					controller.getLevelConstructor().getTreeConstructor().removeEventTrigger(actor, other, item);
//					controller.getLevelConstructor().getTreeConstructor().addEventTriggerActions(actor, other, item,
//							actions);
				}
			});
		}
		selector.setMaxWidth(Double.parseDouble(myResources.getString("checkwidth")));
		return selector;
	}

}
