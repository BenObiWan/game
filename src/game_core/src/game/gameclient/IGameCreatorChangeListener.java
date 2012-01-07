package game.gameclient;

import game.common.IPlayerDescription;
import game.config.IGameConfiguration;

import java.util.Set;

import com.google.common.eventbus.Subscribe;

/**
 * Change listener for {@link IClientGameCreator}.
 * 
 * @author benobiwan
 * 
 */
public interface IGameCreatorChangeListener
{
	/**
	 * Set the game configuration.
	 * 
	 * @param gameConfiguration
	 *            the new {@link IGameConfiguration}.
	 */
	@Subscribe
	void setConfiguration(final IGameConfiguration<?> gameConfiguration);

	/**
	 * Set the player list.
	 * 
	 * @param playerList
	 *            the new player list.
	 */
	@Subscribe
	void setClientSidePlayerList(final Set<IPlayerDescription> playerList);
}
