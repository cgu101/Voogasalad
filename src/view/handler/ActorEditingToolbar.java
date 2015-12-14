// This entire file is part of my masterpiece.
// BRIDGET DOU

package view.handler;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import view.visual.AbstractVisual;

/**
 * This class is responsible for filling and clearing the toolbar when you enter and exit editing mode, respectively.
 * It does not touch any other classes; all buttons and handlers are passed to it.
 * 
 * @author Bridget
 *
 */
public class ActorEditingToolbar extends AbstractVisual {
	private ToolBar myToolbar;
	private Label defaultLabel;
	private boolean rectangleOn;

	public ActorEditingToolbar(ToolBar tb, double mapWidth) {
		findResources();
		myToolbar = tb;
		myToolbar.setPrefWidth(mapWidth);
		defaultLabel = makeLabel(myResources.getString("defaultPrompt"));
		restoreToolbar();
		rectangleOn = false;
	}
	
	protected void makeToolbar(String instructions, List<String> buttonNames, List<EventHandler<ActionEvent>> handlers, int spacerIndex) {
		if (handlers.size() != buttonNames.size()) return;
		myToolbar.getItems().clear();
		for (int i = 0; i < buttonNames.size(); i++) {
			if (i == spacerIndex) addToToolbar(makeSpacer());
			Button b = makeButton(buttonNames.get(i), handlers.get(i));
			addToToolbar(b);
		}
	}
					
	private Label makeLabel(String desc) {
		Label l = new Label(desc);
		l.setFont(textFont);
		return l;
	}
	
	private Button makeButton(String title, EventHandler<ActionEvent> handler) {
		Button b = new Button(title);
		b.setOnAction(handler);
		b.setFont(textFont);
		return b;
	}
		
	private Pane makeSpacer() {
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		return spacer;
	}
	
	private void addToToolbar(Node... nodes) {
		for (Node n : nodes) {
			myToolbar.getItems().add(n);
		}
	}

	protected void restoreToolbar() {
		myToolbar.getItems().clear();
		myToolbar.getItems().add(defaultLabel);
	}
	
	protected void setEditing(boolean on) {
		rectangleOn = on;
	}
	
	protected boolean isEditing() {
		return rectangleOn;
	}
}
