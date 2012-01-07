package game.gameclient;

import game.communication.event.IGameCreationEvent;
import game.communication.event.InconsistentEventTypeException;
import game.communication.event.game.CantActCmnEvent;
import game.communication.event.game.IGameEventHandler;
import game.communication.event.game.TurnTimeoutCmnEvent;
import game.communication.event.game.UnauthorizedActionCmnEvent;
import game.communication.event.game.UnsupportedActionCmnEvent;
import game.communication.event.game.YourTurnCmnEvent;
import game.communication.event.gamecreation.ConfigurationUpdateCrEvent;
import game.communication.event.gamecreation.GameCreatedCrEvent;
import game.communication.event.gamecreation.IGameCreationEventHandler;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Interface describing a change listener of client side event. Uses
 * {@link EventBus}.
 * 
 * @author benobiwan
 * 
 */
public interface IClientSidePlayerChangeListener extends IGameEventHandler,
		IGameCreationEventHandler
{
	@Override
	@Subscribe
	void handleYourTurnCmnEvent(final YourTurnCmnEvent evt);

	@Override
	@Subscribe
	void handleTurnTimeoutCmnEvent(TurnTimeoutCmnEvent evt);

	@Override
	@Subscribe
	void handleUnauthorizedActionCmnEvent(UnauthorizedActionCmnEvent evt);

	@Override
	@Subscribe
	void handleCanActCmnEvent(CantActCmnEvent evt);

	@Override
	@Subscribe
	void handleUnsupportedActionCmnEvent(UnsupportedActionCmnEvent evt);

	@Override
	@Subscribe
	void handleGameCreationEvent(final IGameCreationEvent evt)
			throws InconsistentEventTypeException;

	@Override
	@Subscribe
	void handleConfigurationUpdateCrEvent(final ConfigurationUpdateCrEvent evt);

	@Override
	@Subscribe
	void handleGameCreatedCrEvent(final GameCreatedCrEvent evt);
}
