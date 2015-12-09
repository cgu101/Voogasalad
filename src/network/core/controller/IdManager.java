package network.core.controller;

public class IdManager {
	
	private static Integer clientId = 0;
	
	public static String getNewClientId() {
		String ret = clientId.toString();
		clientId++;
		return ret;
	}
	
	public static void getStoredGame(String gameId) {
		// TODO load game stored on s3
		
	}

}
