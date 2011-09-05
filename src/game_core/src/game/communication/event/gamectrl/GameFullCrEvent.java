package game.communication.event.gamectrl;

import game.communication.event.AbstractGameCtrlEvent;
import game.communication.event.GameCtrlEventType;

/**
 * The event signaling the game is full.
 * 
 * @author benobiwan
 * 
 */
public final class GameFullCrEvent extends AbstractGameCtrlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611883659495207914L;

	/**
	 * creates a new GameFullCrEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player concerned by the event.
	 */
	public GameFullCrEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, GameCtrlEventType.GAME_FULL);
	}
}
