package game.communication.event.control;

import game.common.IGameServer;
import game.communication.event.IControlEvent;
import game.communication.event.InconsistentEventTypeException;

/**
 * Handler of {@link IControlEvent}.
 * 
 * @author benobiwan
 * 
 */
public interface IControlEventHandler
{
	/**
	 * Method handling all the control event.
	 * 
	 * @param server
	 *            the game server from which the control event is coming.
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             the type field of the {@link IControlEvent} and it's class
	 *             are inconsistent.
	 */
	void handleControlEvent(final IGameServer server, final IControlEvent evt)
			throws InconsistentEventTypeException;

	/**
	 * Handle a {@link GameJoinedCtrlEvent}.
	 * 
	 * @param server
	 *            the game server from which the control event is coming.
	 * @param evt
	 *            the event to handle.
	 */
	void handleControlEvent(final IGameServer server,
			final GameJoinedCtrlEvent evt);

	/**
	 * Handle a {@link GameCreationStartedCtrlEvent}.
	 * 
	 * @param server
	 *            the game server from which the control event is coming.
	 * @param evt
	 *            the event to handle.
	 */
	void handleControlEvent(final IGameServer server,
			final GameCreationStartedCtrlEvent evt);

	/**
	 * Handle a {@link ServerStateCtrlEvent}.
	 * 
	 * @param server
	 *            the game server from which the control event is coming.
	 * @param evt
	 *            the event to handle.
	 */
	void handleControlEvent(final IGameServer server,
			final ServerStateCtrlEvent evt);
}
