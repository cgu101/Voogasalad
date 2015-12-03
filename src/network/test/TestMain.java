package network.test;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestMain extends Application {


	@Override
	public void start(Stage primaryStage) {
		String host = JOptionPane.showInputDialog(
				"Enter the host name of the \n computer conneting to Vogasalad");
		if (host == null || host.trim().length() == 0)
			return;
		
		GameWindow window = new GameWindow(host);
		
		primaryStage.setScene(window.getScreen().getScene());
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
