package game.communication.event.gamectrl;

import game.communication.event.AbstractGameCtrlEvent;
import game.communication.event.GameCtrlEventType;

/**
 * The event signaling that the player has left the game.
 * 
 * @author benobiwan
 * 
 */
public final class GameLeftCrEvent extends AbstractGameCtrlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611883655207914L;

	/**
	 * creates a new GameCreatedCrEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player leaving the game.
	 */
	public GameLeftCrEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, GameCtrlEventType.GAME_LEFT);
	}
}
