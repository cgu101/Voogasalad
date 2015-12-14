package view.element;

import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import player.controller.PlayerController;
import player.controller.PlayerStateUtility;
import view.screen.AbstractScreenInterface;

public class GameInfoMonitor extends AbstractDockElement {

	private PlayerStateUtility playerStateUtility;

	public GameInfoMonitor(GridPane home, String title, AbstractScreenInterface screen,  PlayerStateUtility playerStateUtility) {
		super(home, title, screen);
		findResources();
		this.playerStateUtility = playerStateUtility;
	}

	@Override
	protected void makePane() {
		pane.getChildren().clear();
		pane.setMaxHeight(Double.parseDouble(myResources.getString("height")));
		//pane.prefHeightProperty().bind(screen.getScene().heightProperty());
		pane.setFocusTraversable(false);
		pane.setAlignment(Pos.TOP_CENTER);
		//pane.setMaxHeight(screen.getScene().getHeight() * 1/3);
		pane.setPrefWidth(Double.parseDouble(myResources.getString("width")));
		pane.setMaxWidth(Double.parseDouble(myResources.getString("width")));
		configureGameInfo();
	}

	private void configureGameInfo() {
		pane.getChildren().clear();
		addLabelPane();
		HBox hBox = new HBox(8);
		Map<String, String> propertyStringMap = playerStateUtility.getGameProperties();
		hBox.getChildren().add(makeImage(myResources.getString("gameimage"), propertyStringMap.get("name"), propertyStringMap.get("level")));
		pane.add(hBox, 0, 1);
	}

	private void addLabelPane() {
		pane.add(titlePane, 0, 0);
	}
	
	private Label makeImage(String pic, String name, String level) {
//		HBox h = new HBox();
//		ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(pic)));
//		image.setFitHeight(Double.parseDouble(myResources.getString("imagesize")));
//		image.setPreserveRatio(true);
//		image.setSmooth(true);
//		image.setCache(true);
//		GridPane.setRowSpan(image, 1);
//		Label gameName = new Label(name);
//		gameName.setFont(value);
//		
		Label label = new Label(name + "\n Level: "+ level);
		ImageView image =  new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(pic)));
		Double gameInfoSize = Double.parseDouble(myResources.getString("gameinfosize"));
		image.setFitHeight(gameInfoSize);
		image.setPreserveRatio(true);
		image.setSmooth(true);
		label.setGraphic(image);
		label.setTextFill(Color.web("#0076a3"));
		
		return label;
	}
	
	public void initializePane(){
		makePane();
		showing.setValue(true);
	}
}
