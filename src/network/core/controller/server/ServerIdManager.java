// This entire file is part of my masterpiece.
// Christopher Streiffer
package network.core.controller.server;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Manager to generate client IDs (unique)
 */

public class ServerIdManager {

	private static Set<String> uniqueIds;

	/**
	 * Generates a unique client ID
	 * @return unique client ID
	 */
	static String getNewClientId() {
		uniqueIds = new HashSet<String>();
		String newId = SessionIdentifierGenerator.nextSessionId();
		if(!uniqueIds.contains(newId)) {
			uniqueIds.add(newId);
			return newId;
		} else {
			return getNewClientId();
		}
	}

	static void getStoredGame(String gameId) {
		// TODO load game stored on s3

	}

	/**
	 * Generates random session identifier
	 * 
	 * @author Chris 
	 */
	static class SessionIdentifierGenerator {
		private static SecureRandom random = new SecureRandom();

		public static String nextSessionId() {
			return new BigInteger(130, random).toString(32);
		}
	}
}
