package view.element;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import authoring.controller.AuthoringController;
import authoring.controller.constructor.configreader.AuthoringConfigManager;
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

	public ActorBrowser(GridPane home, String title, AbstractScreenInterface screen, Workspace workspace) {
		super(home, title, screen);
		findResources();
		doubleLists = new SimpleBooleanProperty(true);
		doubleLists.addListener(e -> toggleDoubleLists(doubleLists.getValue()));
		this.controller = null;
		workspace.addListener((ov, oldTab, newTab) -> {
			if (workspace.getCurrentLevel() != null) {
				load(workspace.getCurrentLevel().getController());
			} else {
				load(null);
			}
		});
		makePane();
	}

	@Override
	protected void makePane() {
		pane.add(titlePane, 0, 0);
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
			System.out.println(actors.toString());
		}
		// load new groups into ActorGroups
		actors.forEach(a -> {
			controller.getLevelConstructor().getActorGroupsConstructor().getActorGroups().addGroup(a);
		});
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

	public void addNewActor(String actorName, Map<String, String> properties) {
		System.out.println("add actor");
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("src/authoring/files/actors/" + actorName + ".properties");
			String props = "";
			for (Map.Entry<String, String> property : properties.entrySet()) {
				props += property.getKey() + ",";
				prop.setProperty(property.getKey(), property.getValue());
			}
			prop.setProperty("properties", props);
			prop.store(output, "New User Defined Actor Added");
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileInputStream in;
		Properties props = new Properties();
		try {
			in = new FileInputStream("src/authoring/files/configuration.properties");
			props.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileOutputStream out = new FileOutputStream("src/authoring/files/configuration.properties");
			String actors = props.getProperty("actors") + "," + actorName;
			props.setProperty("actors", actors);
			props.store(out, null);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AuthoringConfigManager.getInstance().refresh();
		load(controller);

	}

	// private void externalModify() {
	// IProject project = root.getProject(currentProjectName);
	// project.refreshLocal(IResource.DEPTH_INFINITE, null);
	// }

	public BooleanProperty getDoubleListsProperty() {
		return doubleLists;
	}

	private void toggleDoubleLists(Boolean value) {
		if (value) {
			listPane.add(rightlist, 1, 1);
			setWidth(leftlist, Double.parseDouble(myResources.getString("width")));
			GridPane.setColumnSpan(titlePane, 2);
			listPane.setAlignment(Pos.TOP_CENTER);
		} else {
			rightlist.getSelectionModel().clearSelection();
			listPane.getChildren().remove(rightlist);
			setWidth(leftlist, 2 * Double.parseDouble(myResources.getString("width")));
			GridPane.setColumnSpan(titlePane, 1);
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
