package authoring.controller.constructor.other;

import authoring.controller.constructor.configreader.AuthoringActorConstructor;
import authoring.controller.constructor.levelwriter.LevelConstructor;

public class ConstructorFactory {
	
	public static LevelConstructor getLevelConstructor() {
		return new LevelConstructor();
	}
	
	public static AuthoringActorConstructor getAuthoringActorConstructor() {
		return new AuthoringActorConstructor();
	}

}
