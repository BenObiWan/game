package game.gameclient;

import game.communication.event.game.CantActCmnEvent;
import game.communication.event.game.TurnTimeoutCmnEvent;
import game.communication.event.game.UnauthorizedActionCmnEvent;
import game.communication.event.game.UnsupportedActionCmnEvent;
import game.communication.event.game.YourTurnCmnEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Interface describing a change listener of client side event. Uses
 * {@link EventBus}.
 * 
 * @author benobiwan
 * 
 */
public interface IClientSidePlayerChangeListener
{
	/**
	 * Handle a {@link YourTurnCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link YourTurnCmnEvent} to handle.
	 */
	@Subscribe
	void handleYourTurnCmnEvent(final YourTurnCmnEvent evt);

	/**
	 * Handle a {@link TurnTimeoutCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link TurnTimeoutCmnEvent} to handle.
	 */
	@Subscribe
	void handleTurnTimeoutCmnEvent(TurnTimeoutCmnEvent evt);

	/**
	 * Handle a {@link UnauthorizedActionCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link UnauthorizedActionCmnEvent} to handle.
	 */
	@Subscribe
	void handleUnauthorizedActionCmnEvent(UnauthorizedActionCmnEvent evt);

	/**
	 * Handle a {@link CantActCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link CantActCmnEvent} to handle.
	 */
	@Subscribe
	void handleCanActCmnEvent(CantActCmnEvent evt);

	/**
	 * Handle a {@link UnsupportedActionCmnEvent}.
	 * 
	 * @param evt
	 *            the {@link UnsupportedActionCmnEvent} to handle.
	 */
	@Subscribe
	void handleUnsupportedActionCmnEvent(UnsupportedActionCmnEvent evt);
}
