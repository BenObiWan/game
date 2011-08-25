package game.common;

import java.io.Serializable;

/**
 * Interface describing a player. Used to send the list of participant of a game
 * from the server to the client.
 * 
 * @author benobiwan
 * 
 */
public interface IPlayerDescription extends Serializable,
		Comparable<IPlayerDescription>
{
	/**
	 * Check whether this player is ready or not.
	 * 
	 * @return true if this player is ready.
	 */
	boolean isReady();

	/**
	 * Change the ready status of this player.
	 * 
	 * @param bIsReady
	 *            the new ready status of this player.
	 */
	void setReady(boolean bIsReady);

	/**
	 * Get the name of this player.
	 * 
	 * @return the name of this player.
	 */
	String getPlayerName();

	/**
	 * Check whether this player is an AI or not.
	 * 
	 * @return true if this player is an AI.
	 */
	boolean isAI();
}
