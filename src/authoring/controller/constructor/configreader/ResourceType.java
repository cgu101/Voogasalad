package authoring.controller.constructor.configreader;

public enum ResourceType {
	
	// TODO: Change to resource file
	ACTORS("actors"),
	TRIGGERS("triggers"),
	PROPERTIES("properties"),
	PARAMETERS("parameters"),
	ACTIONS("actions"),	
	NUM_PARAMS("numParams"),
	NUM_ACTORS("numActors"),
	TYPE("type"),	
	CLASS_NAME("className");
	
	private final String resource;
	
	ResourceType (String resource) {
		this.resource = resource;
	}
	
	@Override
	public String toString () {
		return resource;
	}
}
