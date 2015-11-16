package view.element;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import view.actor.ActorCell;
import view.actor.ActorFactory;
import view.screen.AbstractScreenInterface;

public class ActorBrowser extends AbstractDockElement {

	private ListView<ActorFactory> rightlist;
	private ListView<ActorFactory> leftlist;
	private ArrayList<ListView<ActorFactory>> lists;
	private ObservableList<ActorFactory> actors;
	private BooleanProperty doubleLists;

	public ActorBrowser(GridPane pane, GridPane home, String title, AbstractScreenInterface screen) {
		super(pane, home, title, screen);
		doubleLists = new SimpleBooleanProperty(true);
		doubleLists.addListener(e -> toggleDoubleLists(doubleLists.getValue()));
		makePane();
	}

	@Override
	protected void makePane() {
		GridPane labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		GridPane.setColumnSpan(labelPane, 2);
		actors = FXCollections.observableArrayList(new ArrayList<ActorFactory>());
		rightlist = new ListView<ActorFactory>(actors);
		leftlist = new ListView<ActorFactory>(actors);
		pane.add(leftlist, 0, 1);
		pane.add(rightlist, 1, 1);
		pane.setAlignment(Pos.TOP_CENTER);
		leftlist.prefHeightProperty().bind(screen.getScene().heightProperty());
		rightlist.prefHeightProperty().bind(screen.getScene().heightProperty());
		leftlist.setFocusTraversable(false);
		rightlist.setFocusTraversable(false);
		configure(leftlist);
		configure(rightlist);
		lists = new ArrayList<ListView<ActorFactory>>();
		lists.add(leftlist);
		lists.add(rightlist);
	}

	private void configure(ListView<ActorFactory> list) {
		list.setCellFactory(new Callback<ListView<ActorFactory>, ListCell<ActorFactory>>() {
			@Override
			public ListCell<ActorFactory> call(ListView<ActorFactory> list) {
				return new ActorCell();
			}
		});
	}

	public void addNewActor() {
		actors.add(new ActorFactory(actors.size()));
	}

	public BooleanProperty getDoubleListsProperty() {
		return doubleLists;
	}

	private void toggleDoubleLists(Boolean value) {
		if (value) {
			pane.add(rightlist, 1, 1);
		} else {
			rightlist.getSelectionModel().clearSelection();
			pane.getChildren().remove(rightlist);
		}
	}

	public ArrayList<ListView<ActorFactory>> getLists() {
		return lists;
	}

}
