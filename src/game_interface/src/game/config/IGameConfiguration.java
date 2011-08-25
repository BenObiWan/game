package game.config;

import java.io.Serializable;

import common.config.IConfigurationNode;

/**
 * Base configuration for all the games.
 * 
 * @author benobiwan
 * @param <PLAYER_CONF>
 *            the type of player configuration for this game.
 * 
 */
public interface IGameConfiguration<PLAYER_CONF extends IPlayerConfiguration>
		extends Serializable, IConfigurationNode
{
	/**
	 * Maximum number of players in this game.
	 * 
	 * @return the maximum number of player in this game.
	 */
	int getMaxNumberOfPlayers();

	/**
	 * Minimum number of players in this game.
	 * 
	 * @return the minimum number of player in this game.
	 */
	int getMinNumberOfPlayers();
}
