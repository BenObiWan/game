package game.communication.event.game;

import game.communication.event.IGameEvent;

/**
 * Handler of {@link IGameEvent}.
 * 
 * @author benobiwan
 * 
 */
public interface IGameEventHandler
{
	/**
	 * Handle a {@link YourTurnCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link YourTurnCmnEvent} to handle.
	 */
	void handleYourTurnCmnEvent(final YourTurnCmnEvent evt);

	/**
	 * Handle a {@link TurnTimeoutCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link TurnTimeoutCmnEvent} to handle.
	 */
	void handleTurnTimeoutCmnEvent(final TurnTimeoutCmnEvent evt);

	/**
	 * Handle a {@link UnauthorizedActionCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link UnauthorizedActionCmnEvent} to handle.
	 */
	void handleUnauthorizedActionCmnEvent(final UnauthorizedActionCmnEvent evt);

	/**
	 * Handle a {@link CantActCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link CantActCmnEvent} to handle.
	 */
	void handleCanActCmnEvent(final CantActCmnEvent evt);

	/**
	 * Handle a {@link UnsupportedActionCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link UnsupportedActionCmnEvent} to handle.
	 */
	void handleUnsupportedActionCmnEvent(final UnsupportedActionCmnEvent evt);
}
