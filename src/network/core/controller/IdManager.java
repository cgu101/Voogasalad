package network.core.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Set;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class IdManager {

	private static Set<String> uniqueIds;

	public static String getNewClientId() {
		String newId = SessionIdentifierGenerator.nextSessionId();
		if(!uniqueIds.contains(newId)) {
			uniqueIds.add(newId);
			return newId;
		} else {
			return getNewClientId();
		}
	}

	public static void getStoredGame(String gameId) {
		// TODO load game stored on s3

	}

	public static class SessionIdentifierGenerator {
		private static SecureRandom random = new SecureRandom();

		public static String nextSessionId() {
			return new BigInteger(130, random).toString(32);
		}
	}
}
