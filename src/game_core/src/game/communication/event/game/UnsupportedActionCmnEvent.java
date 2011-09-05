package game.communication.event.game;

import game.communication.event.AbstractCommonGameEvent;
import game.communication.event.CommonGameEventType;

/**
 * Event telling the player that he has tried to execute an action not supported
 * by this game.
 * 
 * @author benobiwan
 * 
 */
public class UnsupportedActionCmnEvent extends AbstractCommonGameEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -1794257887136962195L;

	/**
	 * Type of the incriminated action.
	 */
	private final Enum<?> _incriminatedActionType;

	/**
	 * Creates a new UnsupportedActionCmnEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            id of the player concerned by the event.
	 * @param incriminatedActionType
	 *            the type of the incriminated action.
	 */
	protected UnsupportedActionCmnEvent(final int iGameId, final int iPlayerId,
			final Enum<?> incriminatedActionType)
	{
		super(iGameId, iPlayerId, CommonGameEventType.UNSUPPORTED_ACTION);
		_incriminatedActionType = incriminatedActionType;
	}

	/**
	 * Get the type of the incriminated action.
	 * 
	 * @return the type of the incriminated action.
	 */
	public Enum<?> getIncriminatedActionType()
	{
		return _incriminatedActionType;
	}

}
