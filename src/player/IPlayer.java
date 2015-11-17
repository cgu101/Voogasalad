package player;

import exceptions.data.GameFileException;

public interface IPlayer {

	// Resumes gameplay
	public void resume() throws GameFileException;
	
	// Pauses gameplay
	public void pause() throws GameFileException;
	
}
