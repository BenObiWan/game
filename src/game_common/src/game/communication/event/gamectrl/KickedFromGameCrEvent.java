package game.communication.event.gamectrl;

import game.communication.event.AbstractGameCtrlEvent;
import game.communication.event.GameCtrlEventType;

/**
 * The event signaling that the player has been kicked from the game.
 * 
 * @author benobiwan
 * 
 */
public final class KickedFromGameCrEvent extends AbstractGameCtrlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611855207914L;

	/**
	 * creates a new KickedFromGameCrEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player kicked from the game.
	 */
	public KickedFromGameCrEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, GameCtrlEventType.KICKED_FROM_GAME);
	}
}
