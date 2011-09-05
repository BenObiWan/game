package game.common;

import game.communication.IGameClient;
import game.config.IPlayerConfiguration;

/**
 * An interface describing a Player.
 * 
 * @author benobiwan
 * 
 * @param <PLAYER_CONF>
 *            the type of player configuration.
 */
public interface IPlayer<PLAYER_CONF extends IPlayerConfiguration>
{
	/**
	 * Get the player name.
	 * 
	 * @return the player name.
	 */
	String getName();

	/**
	 * Get the id of this player on the hosting {@link IGameClient}. The id is
	 * used to distinguish between Players on the same client.
	 * 
	 * @return the id of this player on the hosting {@link IGameClient}.
	 */
	int getId();

	/**
	 * Get the configuration of this player.
	 * 
	 * @return the configuration of this player.
	 */
	PLAYER_CONF getPlayerConfiguration();
}
