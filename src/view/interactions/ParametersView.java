package view.interactions;

import java.util.ResourceBundle;

import authoring.controller.AuthoringController;
import authoring.controller.parameters.ParameterData;
import authoring.model.tree.InteractionTreeNode;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class ParametersView {
	private static final String ZERO = "0";
	
	protected String title;
	private Stage stage;
	private Scene scene;
	protected AuthoringController controller;
	protected GridPane pane;
	private BorderPane mainPane;
	private ListView<ParameterData> paramListView;
	protected ObservableList<ParameterData> paramList;
	private Button finishButton;
	protected InteractionTreeNode cell;
	private ResourceBundle myResources = ResourceBundle.getBundle("resources/ParametersView");

	public ParametersView (InteractionTreeNode cell, GridPane pane, AuthoringController controller) {
		this.cell = cell;
		this.pane = pane;
		this.controller = controller;
		makePane();
	}
	protected void init() {
		// TODO Auto-generated method stub
		scene = new Scene(mainPane);
		finishButton = new Button(myResources.getString("finish"));
		finishButton.setOnAction(e -> assign());
		finishButton.setFocusTraversable(false);
		mainPane.setBottom(finishButton);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.setOnCloseRequest(e -> {
			e.consume();
		});
		stage.initOwner(pane.getScene().getWindow());
		stage.setTitle(myResources.getString("title") + title);
		stage.showAndWait();
	}
	
	private void assign () {
		paramList.forEach(e -> {
			if (e.getType().equals(Double.class.getName())) {
				try {
					Double.parseDouble(e.getValue());
				} catch (Exception ex) {
					e.setValue(ZERO);
				}
			}
		});
		stage.close();
	}

	private void makePane() {
		// TODO Auto-generated method stub
		mainPane = new BorderPane();
		mainPane.setTop(makeComboBox());
		paramListView = makeParamList();
		mainPane.setCenter(paramListView);
	}
	protected abstract ListView<ParameterData> makeParamList();
	protected abstract ComboBox<String> makeComboBox();
}