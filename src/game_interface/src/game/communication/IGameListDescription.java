package game.communication;

import game.gameserver.IServerGameCreator;

/**
 * Description of a game in the list of game available in the application.
 * 
 * @author benobiwan
 * 
 */
public interface IGameListDescription
{
	/**
	 * Get the name of the game.
	 * 
	 * @return the name of the game.
	 */
	String getName();

	/**
	 * Get the version of the game.
	 * 
	 * @return the version of the game.
	 */
	String getVersion();

	/**
	 * Create an {@link IServerGameCreator} for this game.
	 * 
	 * @return the newly created {@link IServerGameCreator}.
	 */
	IServerGameCreator<?, ?, ?, ?, ?> createServerGameCreator();
}
