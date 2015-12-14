package authoring.model.actors;

/**
 * @author Austin
 */
public enum ActorType {
	GLOBAL("Global"),
	LOCAL("Local");
	
	private final String typeName;
	
	ActorType (String s) {
		this.typeName = s;
	}
	
	public String toString () {
		return typeName;
	}
}
