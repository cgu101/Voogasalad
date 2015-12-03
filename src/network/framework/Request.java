package network.framework;

public enum Request {
	NEW_GAME("New Game"), 
	LOAD_GAME("Load Game");
	
	private final String name;
	
	Request (String name) {
		this.name = name;
	}
	
	@Override
	public String toString () {
		return name;
	}
}
