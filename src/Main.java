import java.util.Arrays;

import authoring.controller.constructor.ActorGroupsConstructor;
import authoring.controller.constructor.TreeConstructor;
import authoring.model.actors.ActorPropertyMap;
import authoring.model.game.Game;
import authoring.model.level.Level;
import controller.RootManager;
import data.XMLManager;
import exceptions.data.GameFileException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	private static final int FRAMES_PER_SECOND = 60;
	private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private RootManager manager;

	@Override
	public void start(Stage primaryStage) throws Exception {
		manager = new RootManager(primaryStage);
		primaryStage.setTitle("VoogaSalad");

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> manager.run());

		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public static void main(String[] args) {
		/* Create a test game */
		/*Game testGame = new Game();
		Level testLevel = new Level("0");
		TreeConstructor tc = new TreeConstructor();
		
		tc.addSelfTriggerActions("onlyone", "authoring.model.triggers.selfconditions.TrueSelfTrigger", 
				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.Move"}));
		
		testLevel.setTreeConstructorValues(tc);
		ActorGroupsConstructor ac = new ActorGroupsConstructor();
		ActorPropertyMap apm = new ActorPropertyMap();
		apm.addProperty("xLocation", "150");
		apm.addProperty("yLocation", "150");
		apm.addProperty("angle", "5");
		apm.addProperty("speed", "5");
		apm.addProperty("image", "megaman.png");
		apm.addProperty("groupID", "onlyone");
		ac.updateActor("testActor", apm);
		testLevel.setActorGroupsValues(ac);
		testGame.addLevel(testLevel);
		XMLManager out = new XMLManager();
		try {
			out.saveGame(testGame, "testgame.game");
		} catch (GameFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		launch(args);
	}
}
