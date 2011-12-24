package game.communication.event.gamectrl;

import game.communication.event.IGameCtrlEvent;
import game.communication.event.InconsistentEventTypeException;

/**
 * Handler of {@link IGameCtrlEvent}.
 * 
 * @author benobiwan
 * 
 */
public interface IGameCtrlEventHandler
{
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
	 * Handle a {@link KickedFromGameCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	void handleKickedFromGameCrEvent(final KickedFromGameCrEvent evt);

	/**
	 * Handle a {@link GameLeftCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	void handleGameLeftCrEvent(final GameLeftCrEvent evt);

	/**
	 * Handle a {@link GameDestroyedCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	void handleGameDestroyedCrEvent(final GameDestroyedCrEvent evt);

	/**
	 * Handle a {@link GameFullCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	void handleGameFullCrEvent(final GameFullCrEvent evt);

	/**
	 * Handle a {@link PlayerListUpdateCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	void handlePlayerListUpdateCrEvent(final PlayerListUpdateCrEvent evt);
}
