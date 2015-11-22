package KeyLibrary;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Demo extends Application {

	private static final int FRAMES_PER_SECOND = 60;
	private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

	@Override
	public void start(Stage primaryStage) {
		KeyLibrary kl = new KeyLibrary();
		primaryStage.setTitle("KeyLibrary Demo");
		Group root = new Group();
		Scene scene = new Scene(root, 800, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void handleKey(KeyCode code) {
	}

	public static void main(String[] args) {
		launch(args);
	}
}
