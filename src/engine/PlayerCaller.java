package engine;

import player.IPlayer;

@FunctionalInterface
public interface PlayerCaller {
	
	public void call(IPlayer p);

}
