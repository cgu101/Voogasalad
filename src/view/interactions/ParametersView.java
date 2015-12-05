package view.interactions;

import java.util.ResourceBundle;

import authoring.controller.AuthoringController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class ParametersView {
	protected String title;
	private Stage stage;
	private Scene scene;
	protected AuthoringController controller;
	private BorderPane mainPane;
	private ListView<ParameterCell> paramList;
	private Button finishButton;
	protected InteractionEditor cell;
	private ResourceBundle myResources = ResourceBundle.getBundle("resources/ParametersView");

	public ParametersView (InteractionEditor cell, AuthoringController controller) {
		this.cell = cell;
		this.controller = controller;
		makePane();
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		scene = new Scene(mainPane);
		finishButton = new Button(myResources.getString("finish"));
		finishButton.setOnAction(e -> assign());
		finishButton.setFocusTraversable(false);
		showStage();
		stage.setTitle(myResources.getString("title") + title);
		stage.showAndWait();
	}
	
	private void assign () {
		
	}

	private void showStage() {
		if (stage != null) {
			stage.close();
		}
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.setOnCloseRequest(e -> {
			e.consume();
		});
	}
	private void makePane() {
		// TODO Auto-generated method stub
		mainPane = new BorderPane();
		mainPane.setTop(makeComboBox());
		paramList = makeParamList();
		mainPane.setCenter(paramList);
	}
	protected abstract ListView<ParameterCell> makeParamList();
	protected abstract ComboBox<String> makeComboBox();
}