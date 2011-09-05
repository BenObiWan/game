package game.communication.event.gamecreation;

import game.communication.event.AbstractGameCreationEvent;
import game.communication.event.GameCreationEventType;

/**
 * The event signaling that the game is created.
 * 
 * @author benobiwan
 * 
 */
public final class GameCreatedCrEvent extends AbstractGameCreationEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611883659495207914L;

	/**
	 * creates a new GameCreatedCrEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player which created the game.
	 */
	public GameCreatedCrEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, GameCreationEventType.GAME_CREATED);
	}
}
