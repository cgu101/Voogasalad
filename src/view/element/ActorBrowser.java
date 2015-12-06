package view.element;

import java.util.ArrayList;

import authoring.controller.AuthoringController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import view.actor.ActorCell;
import view.level.Workspace;
import view.screen.AbstractScreenInterface;

/**
 * @author David
 * 
 *         A double list view element class that allows the user to see the
 *         level's current actor types. Also allows for drag and drop placement
 *         of new actor instances
 * 
 */
public class ActorBrowser extends AbstractDockElement {

	private ListView<String> rightlist;
	private ListView<String> leftlist;
	private ArrayList<ListView<String>> lists;
	private ObservableList<String> actors;
	private BooleanProperty doubleLists;
	private AuthoringController controller;
	private GridPane listPane;
	private GridPane labelPane;

	public ActorBrowser(GridPane pane, GridPane home, String title, AbstractScreenInterface screen,
			Workspace workspace) {
		super(pane, home, title, screen);
		findResources();
		doubleLists = new SimpleBooleanProperty(true);
		doubleLists.addListener(e -> toggleDoubleLists(doubleLists.getValue()));
		this.controller = null;
		workspace.addListener((ov, oldTab, newTab) -> {
			if (workspace.getCurrentLevelInterface() != null) {
				load(workspace.getCurrentLevelInterface().getController());
			} else {
				load(null);
			}
		});
		makePane();
	}

	@Override
	protected void makePane() {
		labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		listPane = new GridPane();
		pane.add(listPane, 0, 1);
		actors = FXCollections.observableArrayList(new ArrayList<String>());
		rightlist = new ListView<String>(actors);
		leftlist = new ListView<String>(actors);
		listPane.add(leftlist, 0, 1);
		listPane.add(rightlist, 1, 1);
		configure(leftlist);
		configure(rightlist);
		lists = new ArrayList<ListView<String>>();
		lists.add(leftlist);
		lists.add(rightlist);
		load(controller);
	}

	public void load(AuthoringController controller) {
		this.controller = controller;
		actors = FXCollections.observableArrayList(new ArrayList<String>());
		leftlist.setItems(actors);
		rightlist.setItems(actors);
		if (controller != null) {
			actors.addAll(controller.getAuthoringActorConstructor().getActorList());
		}
	}

	private void configure(ListView<String> list) {
		list.prefHeightProperty().bind(screen.getScene().heightProperty());
		list.setMaxWidth(Double.parseDouble(myResources.getString("width")));
		list.setFocusTraversable(false);
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				ActorCell cell = new ActorCell(controller);
				cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> handlePress(cell, list, event));
				cell.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> handleRelease(cell, list, event));
				cell.setOnDragDetected(e -> cell.drag(e));
				cell.setOnDragDone(e -> cell.dragDone(e));
				return cell;
			}
		});
	}

	private void handlePress(ActorCell cell, ListView<String> list, MouseEvent event) {
		if (!cell.isEmpty()) {
			int index = cell.getIndex();
			if (list.getSelectionModel().getSelectedIndices().contains(index)) {
				cell.markForDeselection();
			} else {
				list.getSelectionModel().select(index);
			}
			event.consume();
		}
	}

	private void handleRelease(ActorCell cell, ListView<String> list, MouseEvent event) {
		if (cell.deselect()) {
			int index = cell.getIndex();
			list.getSelectionModel().clearSelection(index);
		}
		event.consume();
	}

	public void addNewActor() {
		// actors.add("Actor " + actors.size());
		// TODO: add backend implementation
		System.out.println("add actor");
	}

	public BooleanProperty getDoubleListsProperty() {
		return doubleLists;
	}

	private void toggleDoubleLists(Boolean value) {
		if (value) {
			listPane.add(rightlist, 1, 1);
			setWidth(leftlist, Double.parseDouble(myResources.getString("width")));
			GridPane.setColumnSpan(labelPane, 2);
			listPane.setAlignment(Pos.TOP_CENTER);
		} else {
			rightlist.getSelectionModel().clearSelection();
			listPane.getChildren().remove(rightlist);
			setWidth(leftlist, 2 * Double.parseDouble(myResources.getString("width")));
			GridPane.setColumnSpan(labelPane, 1);
		}
	}

	public void setWidth(ListView list, double width) {
		list.setMinWidth(width);
		list.setMaxWidth(width);
	}

	public ArrayList<ListView<String>> getLists() {
		return lists;
	}

}
