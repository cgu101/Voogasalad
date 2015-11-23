package KeyLibrary;

import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class KeyLibrary {
	private Stage stage;
	private GridPane mainPane;
	private GridPane infoPane;
	private HashMap<KeyCode, ImageView> keys;
	private HashMap<KeyCode, ObservableList<String>> library;
	private ListView<String> list;
	private Text currentKey;
	private ResourceBundle myResources = ResourceBundle.getBundle("KeyLibrary/KeyLibrary");
	private boolean multi;

	public KeyLibrary(boolean multi) {
		this.multi = multi;
		load();
		makePane();
	}

	public KeyCode checkout(String s, KeyCode selection) {
		if (!multi && !library.get(selection).isEmpty()) {
			return null;
		}
		if (!mainPane.getChildren().contains(keys.get(selection))) {
			mainPane.add(keys.get(selection), 0, 0);
		}
		library.get(selection).add(s);
		return selection;
	}

	public void show() {
		if (stage == null) {
			stage = new Stage();
			Scene s = new Scene(mainPane);
			s.setOnKeyPressed(e -> select(e));
			stage.setScene(s);
			stage.setTitle(myResources.getString("title"));
			stage.show();
			stage.setResizable(false);
			stage.setAlwaysOnTop(true);
			stage.setOnCloseRequest(e -> {
				currentKey.setText(myResources.getString("default"));
			});
		}
	}

	private void load() {
		keys = new HashMap<KeyCode, ImageView>();
		library = new HashMap<KeyCode, ObservableList<String>>();
		Scanner s;
		try {
			s = new Scanner(new File("src/KeyLibrary/keys.txt"));
			while (s.hasNext()) {
				String next = s.nextLine();
				try {
					keys.put(KeyCode.getKeyCode(next), makeImage("KeyLibrary/" + next + ".png"));
					library.put(KeyCode.getKeyCode(next), FXCollections
							.observableArrayList(FXCollections.observableArrayList(new ArrayList<String>())));
				} catch (java.lang.NullPointerException e) {
					e.printStackTrace();
					System.err.println(myResources.getString("imgerror"));
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(myResources.getString("fileerror"));
		}
	}

	private void makePane() {
		mainPane = new GridPane();
		ImageView keyboard = makeImage("KeyLibrary/keyboard.png");
		mainPane.add(keyboard, 0, 0);
		infoPane = new GridPane();
		currentKey = new Text(myResources.getString("default"));
		currentKey
				.setFont(new Font(Font.getDefault().getName(), Double.parseDouble(myResources.getString("fontsize"))));
		list = new ListView<String>();
		list.setMaxHeight(keyboard.getBoundsInLocal().getHeight() - currentKey.getBoundsInLocal().getHeight());
		list.setFocusTraversable(false);
		infoPane.add(currentKey, 0, 0);
		infoPane.add(list, 0, 1);
		mainPane.add(infoPane, 1, 0);
		mainPane.setHgap(Double.parseDouble(myResources.getString("hgap")));
	}

	private void select(KeyEvent e) {
		KeyCode code = e.getCode();
		list.setItems(library.get(code));
		currentKey.setText(code.getName());
	}

	private ImageView makeImage(String s) {
		ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(s)));
		image.setFitWidth(Double.parseDouble(myResources.getString("width")));
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);
		return image;
	}
}
