package network.core.controller;

public class IdManager {
	
	private static Integer clientId = 0;
	
	public static Integer getNewClientId() {
		return clientId++;
	}
	
	public static void getStoredGame(String gameId) {
		// TODO load game stored on s3
		
	}

}
