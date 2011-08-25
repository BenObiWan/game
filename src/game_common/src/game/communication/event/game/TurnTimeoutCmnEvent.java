package game.communication.event.game;

import game.communication.event.AbstractCommonGameEvent;
import game.communication.event.CommonGameEventType;

/**
 * The event telling the client that his turn has ended due to timeout.
 * 
 * @author benobiwan
 * 
 */
public final class TurnTimeoutCmnEvent extends AbstractCommonGameEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -5235014472545255397L;

	/**
	 * Creates a new TurnTimeoutCmnEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            id of the player concerned by the event.
	 */
	public TurnTimeoutCmnEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, CommonGameEventType.TURN_TIMEOUT);
	}
}
