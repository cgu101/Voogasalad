package network.test;

import javafx.application.Application;
import javafx.stage.Stage;
import view.controlbar.ControlBarCreator;
import view.screen.CreatorScreen;


/**
 * @author Austin Liu (abl17) and Chris Streiffer (cds33)
 *
 */

public class TestMain extends Application {


	@Override
	public void start(Stage primaryStage) {
		ControlBarCreator myCreatorController = new ControlBarCreator(new CreatorScreen());
		
		primaryStage.setScene(myCreatorController.getVisual());
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
