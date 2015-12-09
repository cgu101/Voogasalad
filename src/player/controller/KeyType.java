package player.controller;

public enum KeyType {
	Type1("Type 1"),
	Type2("Type 2");
	
	private final String typeName;
	
	KeyType(String s) {
		typeName = s;
	}
	
	public String toString () {
		return typeName;
	}
}
