package game.communication.event.gamectrl;

import game.communication.event.AbstractGameCtrlEvent;
import game.communication.event.GameCtrlEventType;

/**
 * The event signaling that a game has been destroyed.
 * 
 * @author benobiwan
 * 
 */
public final class GameDestroyedCrEvent extends AbstractGameCtrlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611883655207914L;

	/**
	 * creates a new GameDestroyedCrEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player concerned by the event.
	 */
	public GameDestroyedCrEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, GameCtrlEventType.GAME_DESTROYED);
	}
}
