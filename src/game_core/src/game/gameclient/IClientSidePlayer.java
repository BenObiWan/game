package game.gameclient;

import game.common.IGameServer;
import game.common.IPlayer;
import game.communication.event.IGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.communication.event.game.IGameEventHandler;
import game.communication.event.gamecreation.IGameCreationEventHandler;
import game.communication.event.gamectrl.IGameCtrlEventHandler;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

/**
 * An interface describing a Player on the client side.
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
 *            the type of player configuration.
 * @param <CLIENT_CHANGE_LISTENER>
 *            the type of {@link IClientSidePlayerChangeListener} for this
 *            player.
 */
public interface IClientSidePlayer<CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, EVENT_TYPE extends IGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, PLAYER_CONF, CONF_TYPE>, PLAYER_CONF extends IPlayerConfiguration, CLIENT_CHANGE_LISTENER extends IClientSidePlayerChangeListener>
		extends IPlayer<PLAYER_CONF>, IGameCreationEventHandler,
		IGameEventHandler, IGameCtrlEventHandler
{
	/**
	 * Get the {@link IClientSideGame} where this {@link IClientSidePlayer} is
	 * playing.
	 * 
	 * @return the {@link IClientSideGame} where this {@link IClientSidePlayer}
	 *         is playing.
	 */
	IClientSideGame<?, ?, ?> getGame();

	/**
	 * Get the {@link IClientGameCreator} which this {@link IClientSidePlayer}
	 * is joining.
	 * 
	 * @return the {@link IClientGameCreator} which this
	 *         {@link IClientSidePlayer} is joining.
	 */
	IClientGameCreator<?, ?, ?, ?, ?, ?> getGameCreator();

	/**
	 * Handle an {@link IGameEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             the type field of the {@link IGameEvent} and it's class are
	 *             inconsistent.
	 */
	void handleGameEvent(final IGameEvent evt)
			throws InconsistentEventTypeException;

	/**
	 * Check whether the game is in creation.
	 * 
	 * @return true if the game is in creation.
	 */
	boolean isGameInCreation();

	/**
	 * Get the id of the game.
	 * 
	 * @return the id of the game.
	 */
	int getGameId();

	/**
	 * Get the {@link IGameServer} where this client is playing.
	 * 
	 * @return the {@link IGameServer} where this client is playing.
	 */
	IGameServer getServer();

	/**
	 * register a new {@link IClientSidePlayerChangeListener}.
	 * 
	 * @param o
	 *            the new new {@link IClientSidePlayerChangeListener}.
	 */
	void registerClientChangeListener(CLIENT_CHANGE_LISTENER o);

	/**
	 * Get the configuration of this game.
	 * 
	 * TODO see if this is useful.
	 * 
	 * @return the configuration of this game.
	 */
	CONF_TYPE getGameConfiguration();
}
