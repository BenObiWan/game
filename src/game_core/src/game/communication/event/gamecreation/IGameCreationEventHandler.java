package game.communication.event.gamecreation;

import game.communication.event.IGameCreationEvent;
import game.communication.event.InconsistentEventTypeException;

/**
 * Handler of {@link IGameCreationEvent}.
 * 
 * @author benobiwan
 * 
 */
public interface IGameCreationEventHandler
{
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
	 * Handle a {@link ConfigurationUpdateCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	void handleConfigurationUpdateCrEvent(final ConfigurationUpdateCrEvent evt);

	/**
	 * Handle a {@link GameCreatedCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	void handleGameCreatedCrEvent(final GameCreatedCrEvent evt);
}
