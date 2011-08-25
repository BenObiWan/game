package game.common;

import java.io.Serializable;

/**
 * Interface for the description of a game. Used to be sent to the client and to
 * display on the interface.
 * 
 * @author benobiwan
 * 
 */
public interface IGameDescription extends Comparable<IGameDescription>,
		Serializable
{
	/**
	 * Get the game id.
	 * 
	 * @return the game id.
	 */
	int getGameId();

	/**
	 * Get the name of the game creator.
	 * 
	 * @return the name of the game creator.
	 */
	String getGameCreatorName();

	/**
	 * Get the type of game.
	 * 
	 * @return the type of game.
	 */
	String getGameType();

	/**
	 * Get the maximum number of player which can join this game.
	 * 
	 * @return the maximum number of player which can join this game.
	 */
	int getMaximumNumberOfPlayer();

	/**
	 * Get the current number of player in this game.
	 * 
	 * @return the current number of player in this game.
	 */
	int getCurrentNumberOfPlayer();

	/**
	 * Change the number of player of this game.
	 * 
	 * @param iNumberOfPlayer
	 *            the new number of player.
	 */
	void setNumberOfPlayer(final int iNumberOfPlayer);
}
