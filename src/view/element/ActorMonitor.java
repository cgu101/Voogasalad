package view.element;

import java.util.ArrayList;
import java.util.HashMap;
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

	private ObservableList<String> actorsList;
	private PlayerController controller; 
	private GridPane listPane;
	private ListView<String> actorList;
	private int i = 1;
	
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
		actorsList = FXCollections.observableArrayList(new ArrayList<String>());
		
		//Actor's keyset = List<String> 
		for(Actor a : controller.getIndividualActorsList()){
			actorsList.add(a.getUniqueID());
		}
		
		actorList = new ListView<String>(actorsList);
		pane.add(actorList, 0, 1);
		pane.prefHeightProperty().bind(screen.getScene().heightProperty());
		pane.setFocusTraversable(false);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setMaxWidth(Double.parseDouble(myResources.getString("width")));
	}
		
	//Creates the Hbox of Properties for a single actor
	private void makeProperties(Actor a) {
		actorList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new ActorMonitorCell(controller, a);
			}
		});
		actorList.setFocusTraversable(false);
		pane.add(actorList, i, 0);
		i++;
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
		
		for(Actor a : controller.getIndividualActorsList()){
			makeProperties(a);
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
