package view.element;

import java.util.ArrayList;
import java.util.Map.Entry;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import player.controller.PlayerController;
import view.actor.ActorCell;
import view.actor.ActorMonitorCell;
import view.actor.PropertyCell;
import view.element.AbstractDockElement;
import view.screen.AbstractScreenInterface;

public class ActorMonitor extends AbstractDockElement {

	private ObservableList<String> actors;
	private PlayerController controller; 
	
	/**
	 * Actor Monitor Constructor
	 * This method creates a new instance of an ActorMonitor
	 *
	 * @param  pane The GridPane on which the component will be applied
	 * @param  name The GridPane location on which the component will snapback to on the main Player
	 * @param  title The Title of this component
	 * @param  screen The Screen used to determine dimensions of the component
	 * @param  controller The PlayerController which allows the ActorMonitor to grab the actors' properties
	 */
	public ActorMonitor(GridPane pane, GridPane home, String title, AbstractScreenInterface screen, PlayerController controller) {
		super(pane, home, title, screen);
		findResources();
		this.controller = controller;
	}

	@Override
	protected void makePane() {
		addLabelPane();
		actors = FXCollections.observableArrayList(new ArrayList<String>());
		
		//Actor's keyset = List<String> 
		actors.addAll(controller.getActorMap().keySet());
		pane.prefHeightProperty().bind(screen.getScene().heightProperty());
		pane.setFocusTraversable(false);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setMaxWidth(Double.parseDouble(myResources.getString("width")));
	}
		
	//Creates the Hbox of Properties for a single actor
	private ListView<String> makeProperties(Actor a) {
		ObservableList<String> properties = FXCollections.observableArrayList(new ArrayList<String>());
		properties.addAll(controller.getActorMap().keySet());
		ListView<String> list = new ListView<String>(properties);
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new ActorMonitorCell(controller, a);
			}
		});
		list.setFocusTraversable(false);
		return list;
	}

	private void addLabelPane() {
		GridPane labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		GridPane.setColumnSpan(labelPane, 2);
	}
	
	//Call to reload the properties for every actor
	private void update() {
		pane.getChildren().clear();
		addLabelPane();
		if (!showing.getValue()) {
			showing.setValue(true);
		}
		
		int i = 0;
		for(Actor a : controller.getActorList()){
			pane.add(makeProperties(a), i, 1);
			i++;
		}

	}
	
	/**
	 * This method allows for the initialization of the Actor Monitor Pane 
	 * component once a Game has been loaded. 
	 */
	public void initializePane(){
		makePane();
	}
	
	/**
	 * This method allows properties of Actor's to be refreshed.
	 */
	public void refresh(){
		update();
	}
		

}
