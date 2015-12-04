package view.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import player.controller.PlayerController;
import view.actor.ActorCell;
import view.actor.ActorMonitorCell;
import view.actor.PropertyCell;
import view.element.AbstractDockElement;
import view.screen.AbstractScreenInterface;

public class ActorMonitor extends AbstractDockElement implements Observer{

	private ObservableList<String> individualActorList;
	private PlayerController controller; 
	private ListView<String> observableIndividualActorList;
	private boolean canUpdate;
	
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
		canUpdate = true;
	}

	@Override
	protected void makePane() {
		addLabelPane();
		individualActorList = FXCollections.observableArrayList(new ArrayList<String>());
		
		//Actor's keyset = List<String> 
		for(Actor a : controller.getIndividualActorsList()){
			individualActorList.add(a.getUniqueID());
		}
		
		observableIndividualActorList = new ListView<String>(individualActorList);
		pane.add(observableIndividualActorList, 0, 1);
		pane.prefHeightProperty().bind(screen.getScene().heightProperty());
		pane.setFocusTraversable(false);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setMaxWidth(Double.parseDouble(myResources.getString("width")));
		configure(observableIndividualActorList);
		load();
	}
		
	public void load() {
		individualActorList = FXCollections.observableArrayList(new ArrayList<String>());
		observableIndividualActorList.setItems(individualActorList);
		if (controller != null) {
			for(Actor a : controller.getIndividualActorsList()){
				individualActorList.add(a.getUniqueID());
			}
			configure(observableIndividualActorList);
		}
	}
	
	//Creates the Hbox of Properties for a single actor
	private void configure(ListView<String> list) {
		observableIndividualActorList.setFocusTraversable(false);
		observableIndividualActorList.setMaxWidth(Double.parseDouble(myResources.getString("width")));
		observableIndividualActorList.setPrefWidth(Double.parseDouble(myResources.getString("width")));
		observableIndividualActorList.setFocusTraversable(false);
		observableIndividualActorList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new ActorMonitorCell(controller);
			}
		});
		observableIndividualActorList.setOnMouseEntered(new EventHandler<MouseEvent>() {
		      public void handle(MouseEvent me) {
		    	  canUpdate = false;
		        }
		      });
		observableIndividualActorList.setOnMouseExited(new EventHandler<MouseEvent>() {
		      public void handle(MouseEvent me) {
		    	  canUpdate = true;
		        }
		      });
	}

	private void addLabelPane() {
		GridPane labelPane = makeLabelPane();
		labelPane.setAlignment(Pos.CENTER);
		pane.add(labelPane, 0, 0);
		//GridPane.setColumnSpan(labelPane, 2);
	}
	
	//Call to reload the properties for every actor
	private void update() {
		pane.getChildren().remove(observableIndividualActorList);
		addLabelPane();
		//if (!showing.getValue()) {
	//		showing.setValue(true);
		//}
		
		individualActorList = FXCollections.observableArrayList(new ArrayList<String>());
		
		for(Actor a : controller.getIndividualActorsList()){
			individualActorList.add(a.getUniqueID());
		}
		
		observableIndividualActorList = new ListView<String>(individualActorList);
		configure(observableIndividualActorList);
		pane.add(observableIndividualActorList, 0, 1);

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
		observableIndividualActorList.refresh();
	}

	@Override
	public void update(Observable o, Object arg) {
		update();
	}
		

}
