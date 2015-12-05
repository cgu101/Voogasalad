package engine;

import player.IPlayer;

public class EngineHeartbeat {
	private PlayerCaller myPlayerCaller;
	private State myGameState;
	
	public EngineHeartbeat (PlayerCaller p) {
		this(p, null);
	}
	public EngineHeartbeat (PlayerCaller p, State state) {
		this.myPlayerCaller = p;
		this.myGameState = state;
	}
	
	/**
	 * Calls the PlayerCaller class
	 * @param p
	 */
	public void call(IPlayer p){
		myPlayerCaller.call(p);
	}
	public State getState() {
		return myGameState;
	}
}
