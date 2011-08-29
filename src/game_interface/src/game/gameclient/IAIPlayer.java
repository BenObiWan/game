package game.gameclient;

import game.communication.event.IGameEvent;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

/**
 * An interface describing an AI, opposed to an interactive player.
 * 
 * @author benobiwan
 * 
 * @param <CLIENT_GAME_TYPE>
 *            the type of game to create.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure the game.
 * @param <EVENT_TYPE>
 *            the type of {@link IGameEvent} handled by the game.
 * @param <PLAYER_CONF>
 *            the type of {@link IPlayerConfiguration}.
 */
public interface IAIPlayer<CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, EVENT_TYPE extends IGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, PLAYER_CONF, CONF_TYPE>, PLAYER_CONF extends IPlayerConfiguration>
		extends
		IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF>
{
	// TODO AIPlayer
}
