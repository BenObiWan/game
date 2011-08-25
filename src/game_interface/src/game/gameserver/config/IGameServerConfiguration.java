package game.gameserver.config;

import common.config.IConfigurationBranch;

/**
 * Configuration for the local game server.
 * 
 * @author benobiwan
 * 
 */
public interface IGameServerConfiguration extends IConfigurationBranch
{
	/**
	 * Get the maximum number of game that can be launched on this game server.
	 * 
	 * @return the maximum number of game that can be launched on this game
	 *         server.
	 */
	int getMaxNumberOfGame();
}
