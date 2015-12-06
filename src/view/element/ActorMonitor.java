package view.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.controlsfx.control.CheckComboBox;

import authoring.model.actors.Actor;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import player.controller.PlayerController;
import view.actor.ActorMonitorCell;
import view.element.AbstractDockElement;
import view.screen.AbstractScreenInterface;

public class ActorMonitor extends AbstractDockElement implements Observer{

	private ObservableList<String> individualActorList;
	private PlayerController controller; 
	private ListView<String> observableIndividualActorList;
	private CheckComboBox<String> checkComboBox;
	private ObservableList<String> showOnlyGroups;
	private ObservableList<String> currentlySelected;

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
		//pane.getChildren().clear();
		addLabelPane();
		individualActorList = FXCollections.observableArrayList(new ArrayList<String>());

		//Actor's keyset = List<String> 
		for(Actor a : controller.getIndividualActorsList()){
			individualActorList.add(a.getUniqueID());
		}

		observableIndividualActorList = new ListView<String>(individualActorList);
		pane.add(observableIndividualActorList, 0, 1);
		pane.setMaxHeight(Double.parseDouble(myResources.getString("height")));
		//pane.prefHeightProperty().bind(screen.getScene().heightProperty());
		//pane.setMaxHeight(screen.getScene().getHeight() * 2/3);
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
	}

	private void addLabelPane() {
		GridPane labelPane = makeLabelPane();
		labelPane.setAlignment(Pos.CENTER);
		pane.add(labelPane, 0, 0);
		labelPane.add(makeCheckBox(), 0, 1);
		GridPane.setColumnSpan(labelPane, 2);
	}

	private Node makeCheckBox(){
		showOnlyGroups = FXCollections.observableArrayList();
		currentlySelected = FXCollections.observableArrayList(new ArrayList<String>());
		showOnlyGroups.addAll(controller.getActorGroups());
		checkComboBox = new CheckComboBox<String>(showOnlyGroups);
		checkComboBox.setMaxWidth(Double.parseDouble(myResources.getString("width")));
		checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) {
				currentlySelected = checkComboBox.getCheckModel().getCheckedItems();
				resetData();
			}
		});

		return checkComboBox;
	}

	//Call to reload the properties for every actor
	public void resetData() {
	//	pane.getChildren().clear();
		pane.getChildren().remove(observableIndividualActorList);
		pane.getChildren().remove(checkComboBox);
		
		individualActorList = FXCollections.observableArrayList(new ArrayList<String>());
		for(Actor a : controller.getIndividualActorsList()){
			if(currentlySelected.contains(a.getGroupName()) || currentlySelected.isEmpty()){
				individualActorList.add(a.getUniqueID());
			}
		}

		observableIndividualActorList = new ListView<String>(individualActorList);
		configure(observableIndividualActorList);
		pane.add(observableIndividualActorList, 0, 2);

	}

	/**
	 * This method allows for the initialization of the Actor Monitor Pane 
	 * component once a Game has been loaded. 
	 */
	public void initializePane(){
		makePane();
		showing.setValue(true);
	}

	/**
	 * This method allows properties of Actor's to be refreshed without the whole pane resetting.
	 */
	public void refresh(){
		observableIndividualActorList.refresh();
	}

	@Override
	public void update(Observable o, Object arg) {
		resetData();
	}


}
