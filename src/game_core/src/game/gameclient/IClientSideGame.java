package game.gameclient;

import game.common.IGameServer;
import game.communication.event.ICommonGameEvent;
import game.communication.event.IUniCastGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

/**
 * An interface representing the game on the client side.
 * 
 * @author benobiwan
 * 
 * @param <EVENT_TYPE>
 *            the type of {@link IUniCastGameEvent} handled by this game.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure this
 *            game.
 * @param <PLAYER_CONF>
 *            the type of {@link IPlayerConfiguration}.
 */
public interface IClientSideGame<EVENT_TYPE extends IUniCastGameEvent, PLAYER_CONF extends IPlayerConfiguration, CONF_TYPE extends IGameConfiguration<PLAYER_CONF>>
		extends Comparable<IClientSideGame<?, ?, ?>>
{
	/**
	 * Get the id of this game.
	 * 
	 * @return the id of this game.
	 */
	int getGameId();

	/**
	 * Handle an {@link IUniCastGameEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             if the event type field and the class of the event object are
	 *             inconsistent.
	 */
	void handleEvent(final IUniCastGameEvent evt)
			throws InconsistentEventTypeException;

	/**
	 * Handle a {@link ICommonGameEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             * the type field of the {@link ICommonGameEvent} and it's
	 *             class are inconsistent.
	 */
	void handleEvent(final ICommonGameEvent evt)
			throws InconsistentEventTypeException;

	/**
	 * Get the game server hosting this game.
	 * 
	 * @return the game server hosting this game.
	 */
	IGameServer getGameServer();

	/**
	 * Get the configuration of this game.
	 * 
	 * @return the configuration of this game.
	 */
	CONF_TYPE getGameConfiguration();
}
