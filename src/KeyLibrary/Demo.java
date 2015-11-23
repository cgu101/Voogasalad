package KeyLibrary;

import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Demo extends Application {

	private static final int FRAMES_PER_SECOND = 60;
	private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private KeyLibrary kl;

	@Override
	public void start(Stage primaryStage) {
		kl = new KeyLibrary(true);
		kl.show();
		primaryStage.setTitle("KeyLibrary Demo");
		Group root = new Group();
		Scene scene = new Scene(root, 800, 400);
		scene.setOnKeyPressed(e -> checkout(e));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void checkout(KeyEvent e) {
		Scanner s = new Scanner(System.in);
		kl.checkout(s.nextLine(), e.getCode());
		s.close();
	}

	private void showError(String title, String message) {
		Alert uhoh = new Alert(AlertType.ERROR);
		uhoh.setTitle(title);
		uhoh.setContentText(message);
		uhoh.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
