import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;

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
		//Test
		/* Create a test game */
		Game testGame = new Game();
		Property p = new Property("name", "Awesome Game");
		Property p2 = new Property(PropertyKeyResource.getKey(PropertyKey.GAME_LEVEL_COUNT_KEY), "2");
		testGame.addProperty(p);
		testGame.addProperty(p2);
		Level testLevel = new Level("0");
		TreeConstructor tc = new TreeConstructor();  //Changed constructor from none to public
		MapConstructor m = new MapConstructor();
		ActorGroupsConstructor ac = new ActorGroupsConstructor();	// Changed constructor from nothing to public
		ac.getActorGroups().addGroup("bullet");		

		//MegaMan Actor
		ActorPropertyMap apmMegaMan = new ActorPropertyMap();
		apmMegaMan.addProperty("groupID", "player");
		apmMegaMan.addProperty("image", "megaman.png");
		apmMegaMan.addProperty("xLocation", "0");
		apmMegaMan.addProperty("yLocation", "0");
		apmMegaMan.addProperty("angle", "45");
		apmMegaMan.addProperty("speed", "0");
		apmMegaMan.addProperty("size", "50");
		apmMegaMan.addProperty("range", "40");
		apmMegaMan.addProperty("health", "20");
		ac.updateActor("testActor", apmMegaMan);
		
		//Asteroid Actors
		Random generator = new Random();
		ActorPropertyMap apmAsteroid = new ActorPropertyMap();
		apmAsteroid.addProperty("groupID", "asteroid");
		apmAsteroid.addProperty("image", "asteroids.png");
		apmAsteroid.addProperty("size", "128");
		apmAsteroid.addProperty("health", "20");
		apmAsteroid.addProperty("xLocation", Integer.toString(generator.nextInt(701)));
		apmAsteroid.addProperty("yLocation", Integer.toString(generator.nextInt(601)));
		apmAsteroid.addProperty("angle", Integer.toString(generator.nextInt(360)));
		apmAsteroid.addProperty("speed", Integer.toString(10 + generator.nextInt(11)));
		ac.updateActor("testActor2", apmAsteroid);
		apmAsteroid.addProperty("xLocation", Integer.toString(generator.nextInt(701)));
		apmAsteroid.addProperty("yLocation", Integer.toString(generator.nextInt(601)));
		apmAsteroid.addProperty("angle", Integer.toString(generator.nextInt(360)));
		apmAsteroid.addProperty("speed", Integer.toString(10 + generator.nextInt(11)));
		ac.updateActor("testActor3", apmAsteroid);
		apmAsteroid.addProperty("xLocation", Integer.toString(generator.nextInt(701)));
		apmAsteroid.addProperty("yLocation", Integer.toString(generator.nextInt(601)));
		apmAsteroid.addProperty("angle", Integer.toString(generator.nextInt(360)));
		apmAsteroid.addProperty("speed", Integer.toString(10 + generator.nextInt(11)));
		ac.updateActor("testActor4", apmAsteroid);
		apmAsteroid.addProperty("xLocation", Integer.toString(generator.nextInt(701)));
		apmAsteroid.addProperty("yLocation", Integer.toString(generator.nextInt(601)));
		apmAsteroid.addProperty("angle", Integer.toString(generator.nextInt(360)));
		apmAsteroid.addProperty("speed", Integer.toString(10 + generator.nextInt(11)));
		ac.updateActor("testActor5", apmAsteroid);
		
		//Global Level Transition Actor
		ActorPropertyMap apmGlobal = new ActorPropertyMap();
		apmGlobal.addProperty("groupID", "LevelTransition");
		apmGlobal.addProperty("image", "rcd.jpg");
		apmGlobal.addProperty("xLocation", "300");
		apmGlobal.addProperty("yLocation", "300");
		apmGlobal.addProperty("size", "300");
		ac.updateActor("GlobalActor", apmGlobal);
		
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
		
		
		// LevelTransition Self Triggers
		actorList.clear();
		actorList.add("LevelTransition");
		triggerList.clear();
		triggerList.add("DownArrowKey");
		actionList.clear();
		actionList.add("NextLevel");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		
		// Asteroid Self Triggers
		actorList.clear();
		actorList.add("asteroid");
//		triggerList.clear();
//		triggerList.add("authoring.model.triggers.selfconditions.DownArrowKey");
//		actionList.clear();
//		actionList.add("authoring.model.actions.oneActorActions.Move");
//		tc.addTreeNode(actorList, triggerList, actionList);
		
		triggerList.clear();
		triggerList.add("TrueSelfTrigger");
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
		
		triggerList.clear();
		triggerList.add("DistanceTraveledCheck");
		actionList.clear();
		actionList.add("RemoveActor");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		// External Triggers
		//Remove asteroid when asteroid and bullet collide
		actorList.clear();
		actorList.add("asteroid");
		actorList.add("bullet");
		triggerList.clear();
		triggerList.add("CircleCollision");
		actionList.clear();
		actionList.add("RemoveActor");
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
		
		
		
		
		
		Level testLevel1 = new Level("1");
		TreeConstructor tc1 = new TreeConstructor();  //Changed constructor from none to public
		MapConstructor m1 = new MapConstructor();
		ActorGroupsConstructor ac1 = new ActorGroupsConstructor();	// Changed constructor from nothing to public

		//MegaMan Actor
		ActorPropertyMap apmMegaMan1 = new ActorPropertyMap();
		apmMegaMan1.addProperty("groupID", "player");
		apmMegaMan1.addProperty("image", "rcd.jpg");
		apmMegaMan1.addProperty("xLocation", "0");
		apmMegaMan1.addProperty("yLocation", "0");
		apmMegaMan1.addProperty("angle", "45");
		apmMegaMan1.addProperty("speed", "0");
		apmMegaMan1.addProperty("size", "50");
		apmMegaMan1.addProperty("range", "40");
		apmMegaMan1.addProperty("health", "20");
		ac1.updateActor("testActor7", apmMegaMan1);
		
//		//Asteroid Actors
//		ActorPropertyMap apmAsteroid1 = new ActorPropertyMap();
//		apmAsteroid1.addProperty("groupID", "asteroid");
//		apmAsteroid1.addProperty("image", "asteroids.png");
//		apmAsteroid1.addProperty("size", "128");
//		apmAsteroid1.addProperty("health", "20");
//		apmAsteroid1.addProperty("xLocation", Integer.toString(generator.nextInt(701)));
//		apmAsteroid1.addProperty("yLocation", Integer.toString(generator.nextInt(601)));
//		apmAsteroid1.addProperty("angle", Integer.toString(generator.nextInt(360)));
//		apmAsteroid1.addProperty("speed", Integer.toString(10 + generator.nextInt(11)));
//		ac1.updateActor("testActor8", apmAsteroid1);
		
		
		List<String> actorList1 = new ArrayList<String>();
		actorList1.add("player");
		List<String> triggerList1 = new ArrayList<String>();
		triggerList1.add("UpArrowKey");
		List<String> actionList1 = new ArrayList<String>();
		actionList1.add("IncreaseSpeed");
		tc.addTreeNode(actorList, triggerList, actionList);
		
		testLevel1.setTreeConstructorValues(tc1);
		testLevel1.setActorGroupsValues(ac1);
		testLevel1.setMapConstructorValues(m1);
		
		testGame.addLevel(testLevel1);
		
		XMLManager out = new XMLManager();
		try {
			out.saveGame(testGame, "testgame2.game");
		} catch (GameFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
