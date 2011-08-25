package game.communication.event;

/**
 * Implementation of the {@link ICommonGameEvent} interface describing all event
 * taking place during the course of a game and common to many games.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractCommonGameEvent extends AbstractGameEvent
		implements ICommonGameEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3276431935266073866L;
	/**
	 * Type of this common game event.
	 */
	protected final CommonGameEventType _type;

	/**
	 * Creates a new AbstractCommonGameEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            id of the player concerned by the event.
	 * @param type
	 *            type of this common game event.
	 */
	protected AbstractCommonGameEvent(final int iGameId, final int iPlayerId,
			final CommonGameEventType type)
	{
		super(iGameId, iPlayerId);
		_type = type;
	}

	@Override
	public final CommonGameEventType getType()
	{
		return _type;
	}
}
