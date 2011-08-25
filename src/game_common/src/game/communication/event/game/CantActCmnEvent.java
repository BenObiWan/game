package game.communication.event.game;

import game.communication.event.AbstractCommonGameEvent;
import game.communication.event.CommonGameEventType;

/**
 * Event telling the player that he cannot act right now.
 * 
 * @author benobiwan
 * 
 */
public final class CantActCmnEvent extends AbstractCommonGameEvent
{

	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611883659495207914L;

	/**
	 * creates a new CantActCmnEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            id of the player concerned by the event.
	 */
	public CantActCmnEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, CommonGameEventType.CANT_ACT);
	}
}
