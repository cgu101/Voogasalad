package KeyLibrary;

import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.image.Image;

public class KeyLibrary {
	private Stage stage;
	private StackPane pane;
	private HashMap<KeyCode, Image> keys;

	public KeyLibrary() {
		load();
	}

	public KeyCode checkout() {
		launch();
	}

	private void launch() {
		stage = new Stage();
		stage.setScene(new Scene(pane));
		stage.setTitle("Key Checkout");
		stage.show();
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
	}

	private void load() {
		keys = new HashMap<KeyCode, Image>();
		Scanner s;
		try {
			s = new Scanner(new File("src/KeyLibrary/keys.txt"));
			while (s.hasNext()) {
				String next = s.nextLine();
				try {
					keys.put(KeyCode.getKeyCode(next),
							new Image(getClass().getClassLoader().getResourceAsStream("KeyLibrary/" + next + ".png")));
				} catch (java.lang.NullPointerException e) {
					e.printStackTrace();
					System.err.println("images not found");
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("keys.txt not found");
		}

	}
}
