package engine;

import player.IPlayer;

@FunctionalInterface
public interface PlayerCaller {
	
	/**
	 * Call function linked with EngineHeartbeat
	 * @param p
	 */
	public void call(IPlayer p);

}
