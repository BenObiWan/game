package game.communication.event.game;

import game.communication.event.AbstractCommonGameEvent;
import game.communication.event.CommonGameEventType;

/**
 * The event telling the client that it is it's time to act.
 * 
 * @author benobiwan
 * 
 */
public final class YourTurnCmnEvent extends AbstractCommonGameEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -6758996355557209082L;

	/**
	 * Creates a new YourTurnCmnEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            id of the player concerned by the event.
	 */
	public YourTurnCmnEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, CommonGameEventType.YOUR_TURN);
	}
}
