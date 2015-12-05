import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import authoring.controller.constructor.levelwriter.ActorGroupsConstructor;
import authoring.controller.constructor.levelwriter.MapConstructor;
import authoring.controller.constructor.levelwriter.TreeConstructor;
import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import authoring.model.game.Game;
import authoring.model.level.Level;
import authoring.model.properties.Property;
import authoring.model.triggers.ITriggerEvent;
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
		launch(args);
	}
	
	/**
	 * Up Arrow: Move Mega Man forward
	 * Left Arrow: Turn Mega Man left
	 * Right Arrow: Turn Mega Man right
	 * Space Bar: Shoot bullet (bullets are currently using rcd.jpg)
	 */
	private static void createTestGame2() {
		/* Create a test game */
		Game testGame = new Game();
		Level testLevel = new Level("0");
		TreeConstructor tc = new TreeConstructor();  //Changed constructor from none to public
		
		Property p = new Property("name", "Awesome Game");
		testGame.addProperty(p);
		
		ActorGroupsConstructor ac = new ActorGroupsConstructor();	// Changed constructor from nothing to public
		ActorPropertyMap apm = new ActorPropertyMap();
		apm.addProperty("xLocation", "0");
		apm.addProperty("yLocation", "0");
		apm.addProperty("angle", "45");
		apm.addProperty("speed", "0");
		apm.addProperty("image", "megaman.png");
		apm.addProperty("groupID", "player");
		apm.addProperty("size", "12");
		apm.addProperty("range", "40");
		apm.addProperty("health", "20");
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
		
		ac.getActorGroups().addGroup("bullet");
		
//		ActorPropertyMap apm3 = new ActorPropertyMap();
//		apm.addProperty("s", "0");
//		apm.addProperty("yLocation", "0");
//		apm.addProperty("angle", "45");
//		apm.addProperty("speed", "0");
//		apm.addProperty("image", "megaman.png");
//		apm.addProperty("groupID", "bullet");
//		apm.addProperty("size", "12");
//		apm.addProperty("range", "40");
//		apm.addProperty("health", "20");
//		ac.updateActor("testActor3", apm3);

		
		MapConstructor m = new MapConstructor();
//		m.addTriggerToMap("authoring.model.triggers.selfconditions.UpArrowKey");
//		m.addTriggerToMap("authoring.model.triggers.selfconditions.LeftArrowKey");
//		m.addTriggerToMap("authoring.model.triggers.selfconditions.RightArrowKey");
//		m.addTriggerToMap("authoring.model.triggers.selfconditions.DownArrowKey");
//		m.addTriggerToMap("authoring.model.triggers.selfconditions.SpaceBarKey");
//		m.addTriggerToMap("authoring.model.triggers.selfconditions.TrueSelfTrigger");
//		m.addTriggerToMap("authoring.model.triggers.externalconditions.CircleCollision");
//		m.addActionsToMap("authoring.model.actions.actorActions.oneActorActions.Move");
//		m.addActionsToMap("authoring.model.actions.actorActions.oneActorActions.RotateCounterclockwise");
//		m.addActionsToMap("authoring.model.actions.actorActions.oneActorActions.RotateClockwise");
//		m.addActionsToMap("authoring.model.actions.actorActions.oneActorActions.IncreaseSpeed");
//		m.addActionsToMap("authoring.model.actions.actorActions.oneActorActions.DecreaseSpeed");
//		m.addActionsToMap("authoring.model.actions.actorActions.oneActorActions.ShootBullet");
//		m.addActionsToMap("authoring.model.actions.actorActions.oneActorActions.SplitAndReduceSize");
//		m.addActionsToMap("authoring.model.actions.actorActions.oneActorActions.RemoveActor");
		
		// Mega Man Self Triggers
		List<String> actorList = new ArrayList<String>();
		actorList.add("player");
		List<String> triggerList = new ArrayList<String>();
		triggerList.add("UpArrowKey");
		List<String> actionList = new ArrayList<String>();
		actionList.add("IncreaseSpeed");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		triggerList.clear();
		triggerList.add("RightArrowKey");
		actionList.clear();
		actionList.add("RotateCounterclockwise");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		triggerList.clear();
		triggerList.add("LeftArrowKey");
		actionList.clear();
		actionList.add("RotateClockwise");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		triggerList.clear();
		triggerList.add("SpaceBarKey");
		actionList.clear();
		actionList.add("ShootBullet");
		tc.addTreeNode(actorList, triggerList, actionList);

		triggerList.clear();
		triggerList.add("TrueSelfTrigger");
		actionList.clear();
		actionList.add("DecreaseSpeed");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		triggerList.clear();
		triggerList.add("TrueSelfTrigger");
		actionList.clear();
		actionList.add("Move");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		
		// Asteroid Self Triggers
		actorList.clear();
		actorList.add("asteroid");
		triggerList.clear();
		triggerList.add("DownArrowKey");
		actionList.clear();
		actionList.add("Move");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		// Bullet Self Triggers
		actorList.clear();
		actorList.add("bullet");
		triggerList.clear();
		triggerList.add("TrueSelfTrigger");
		actionList.clear();
		actionList.add("Move");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		// External Triggers
		//Remove asteroid when asteroid and bullet collide
		actorList.clear();
		actorList.add("asteroid");
		actorList.add("bullet");
		triggerList.clear();
		triggerList.add("authoring.model.triggers.externalconditions.CircleCollision");
		actionList.clear();
		actionList.add("authoring.model.actions.actorActions.oneActorActions.RemoveActor");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		//Remove bullet when asteroid and bullet collide
		actorList.clear();
		actorList.add("bullet");
		actorList.add("asteroid");
		triggerList.clear();
		triggerList.add("CircleCollision");
		actionList.clear();
		actionList.add("RemoveActor");
		tc.addTreeNode(actorList, triggerList, actionList);

			
		
		
		testLevel.setTreeConstructorValues(tc);
		testLevel.setActorGroupsValues(ac);
		testLevel.setMapConstructorValues(m);
		
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
