package engine;

import player.IPlayer;

public class EngineHeartbeat {
	private InteractionExecutor myGameState;
	private PlayerCaller myPlayerCaller;
	
	public EngineHeartbeat(InteractionExecutor ref, PlayerCaller p){
		myGameState = ref;
		myPlayerCaller = p;
	}
	
	public void call(IPlayer p){
		myPlayerCaller.call(p);
	}
}
