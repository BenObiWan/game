package game.gameserver;

import game.common.IPlayer;
import game.common.IPlayerDescription;
import game.communication.IGameClient;
import game.config.IPlayerConfiguration;

/**
 * An interface describing a Player on the server side.
 * 
 * @author benobiwan
 * @param <PLAYER_CONF>
 *            the type of player configuration.
 */
public interface IServerSidePlayer<PLAYER_CONF extends IPlayerConfiguration>
		extends IPlayer<PLAYER_CONF>,
		Comparable<IServerSidePlayer<PLAYER_CONF>>
{
	/**
	 * Get the {@link IGameClient} hosting this player.
	 * 
	 * @return {@link IGameClient} hosting this player.
	 */
	IGameClient getClient();

	/**
	 * Get the {@link IServerSideGame} where this {@link IServerSidePlayer} is
	 * playing.
	 * 
	 * @return the {@link IServerSideGame} where this {@link IServerSidePlayer}
	 *         is playing.
	 */
	IServerSideGame<?, ?, ?> getGame();

	/**
	 * Get the {@link IServerGameCreator} which this {@link IServerSidePlayer}
	 * is joining.
	 * 
	 * @return the {@link IServerGameCreator} which this
	 *         {@link IServerSidePlayer} is joining.
	 */
	IServerGameCreator<?, ?, ?, ?, ?> getGameCreator();

	/**
	 * Get whether this player is an AI.
	 * 
	 * @return true if this player is an AI.
	 */
	boolean isAI();

	/**
	 * Get the status of the player.
	 * 
	 * @return true if the player is ready to play.
	 */
	boolean isReady();

	/**
	 * Update the status of the player, ready to play or not.
	 * 
	 * @param bReady
	 *            the new status of the player.
	 */
	void setReady(boolean bReady);

	/**
	 * Get the description of the player.
	 * 
	 * @return the description of the player.
	 */
	IPlayerDescription getDescription();

	// TOD add default play from the player
}
