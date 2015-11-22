package authoring.controller.constructor;

public class ConstructorFactory {
	
	public static LevelConstructor getLevelConstructor() {
		return new LevelConstructor();
	}
	
	public static AuthoringActorConstructor getAuthoringActorConstructor() {
		return new AuthoringActorConstructor();
	}

}
