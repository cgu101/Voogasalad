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
		createTestGame2();
		
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
	
	private static void createTestGame2() {
		/* Create a test game */
		Game testGame = new Game();
		Level testLevel = new Level("0");
		TreeConstructor tc = new TreeConstructor();
		
//		tc.addSelfTriggerActions("player", "authoring.model.triggers.selfconditions.TrueSelfTrigger", 
//				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.Move"}));
		
//		tc.addSelfTriggerActions("player", "authoring.model.triggers.selfconditions.TrueSelfTrigger", 
//				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.DecreaseSpeed"}));
		
		tc.addSelfTriggerActions("player", "authoring.model.triggers.selfconditions.UpArrowKey", 
				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.Move"}));
		
		tc.addSelfTriggerActions("player", "authoring.model.triggers.selfconditions.DownArrowKey", 
				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.MoveBackwards"}));
		
//		tc.addSelfTriggerActions("player", "authoring.model.triggers.selfconditions.LeftArrowKey", 
//				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.RotateClockwise"}));
//		
//		tc.addSelfTriggerActions("player", "authoring.model.triggers.selfconditions.RightArrowKey", 
//				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.RotateCounterclockwise"}));
		

		tc.addSelfTriggerActions("asteroid", "authoring.model.triggers.selfconditions.LeftArrowKey",
				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.Move"}));
		
		tc.addSelfTriggerActions("asteroid", "authoring.model.triggers.selfconditions.RightArrowKey",
				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.MoveBackwards"}));
		
		tc.addSelfTriggerActions("asteroid", "authoring.model.triggers.selfconditions.SpaceBarKey",
				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.SplitAndReduceSize"}));

		
		tc.addEventTriggerActions("player", "asteroid", "authoring.model.triggers.externalconditions.CircleCollision", 
				Arrays.asList(new String[]{"authoring.model.actions.twoActorActions.SwapDirections"}));

//		tc.addEventTriggerActions("player", "asteroid", "authoring.model.triggers.externalconditions.InRange", 
//				Arrays.asList(new String[]{"authoring.model.actions.oneActorActions.ReduceSize"}));

		
		testLevel.setTreeConstructorValues(tc);
		ActorGroupsConstructor ac = new ActorGroupsConstructor();
		ActorPropertyMap apm = new ActorPropertyMap();
		apm.addProperty("xLocation", "0");
		apm.addProperty("yLocation", "0");
		apm.addProperty("angle", "45");
		apm.addProperty("speed", "15");
		apm.addProperty("image", "megaman.png");
		apm.addProperty("groupID", "player");
		apm.addProperty("size", "12");
		apm.addProperty("range", "40");
//		apm.addProperty("health", "20");
		ac.updateActor("testActor", apm);
		
		ActorPropertyMap apm2 = new ActorPropertyMap();
		apm2.addProperty("xLocation", "330");
		apm2.addProperty("yLocation", "0");
		apm2.addProperty("angle", "135");
		apm2.addProperty("speed", "15");
		apm2.addProperty("image", "asteroids.png");
		apm2.addProperty("groupID", "asteroid");
		apm2.addProperty("size", "30");
		apm2.addProperty("health", "20");
		ac.updateActor("testActor2", apm2);
		
		testLevel.setActorGroupsValues(ac);
		
		testLevel.setActorGroupsValues(ac);
		testGame.addLevel(testLevel);
		XMLManager out = new XMLManager();
		try {
			out.saveGame(testGame, "testgame2.game");
		} catch (GameFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
