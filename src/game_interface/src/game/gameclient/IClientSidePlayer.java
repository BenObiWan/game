package game.gameclient;

import game.common.IPlayer;
import game.communication.IGameServer;
import game.communication.event.IGameCreationEvent;
import game.communication.event.IGameCtrlEvent;
import game.communication.event.IGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.config.IGameConfiguration;

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
 */
public interface IClientSidePlayer<CONF_TYPE extends IGameConfiguration, EVENT_TYPE extends IGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, CONF_TYPE>>
		extends IPlayer
{
	/**
	 * Get the {@link IClientSideGame} where this {@link IClientSidePlayer} is
	 * playing.
	 * 
	 * @return the {@link IClientSideGame} where this {@link IClientSidePlayer}
	 *         is playing.
	 */
	IClientSideGame<?, ?> getGame();

	/**
	 * Get the {@link IClientGameCreator} which this {@link IClientSidePlayer}
	 * is joining.
	 * 
	 * @return the {@link IClientGameCreator} which this
	 *         {@link IClientSidePlayer} is joining.
	 */
	IClientGameCreator<?, ?, ?, ?> getGameCreator();

	/**
	 * Handle an {@link IGameCreationEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             the type field of the {@link IGameCreationEvent} and it's
	 *             class are inconsistent.
	 */
	void handleGameCreationEvent(final IGameCreationEvent evt)
			throws InconsistentEventTypeException;

	/**
	 * Handle an {@link IGameCtrlEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             the type field of the {@link IGameCtrlEvent} and it's class
	 *             are inconsistent.
	 */
	void handleGameCtrlEvent(final IGameCtrlEvent evt)
			throws InconsistentEventTypeException;

	/**
	 * Handle an {@link IGameEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	void handleGameEvent(final IGameEvent evt);

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
}
